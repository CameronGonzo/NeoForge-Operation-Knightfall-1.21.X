package net.uhhitscam.knightfall.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.decoration.GlowItemFrame;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.entity.vehicle.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.effect.ModEffects;
import net.uhhitscam.knightfall.gui.HudClient;
import net.uhhitscam.knightfall.item.custom.FiringMode;
import net.uhhitscam.knightfall.item.custom.ProjectileItem;
import net.uhhitscam.knightfall.item.custom.WeaponCooldownAction;
import net.uhhitscam.knightfall.item.custom.WeaponName;
import net.uhhitscam.knightfall.network.PayloadRegister;
import net.uhhitscam.knightfall.network.SSBeamPacket;
import net.uhhitscam.knightfall.network.SSCooldownPacket;
import net.uhhitscam.knightfall.network.SSFireProjectileWeaponPacket;
import net.uhhitscam.knightfall.sound.BeamSoundInstance;
import net.uhhitscam.knightfall.sound.ChargingSoundInstance;
import net.uhhitscam.knightfall.sound.FullyChargedSoundInstance;
import net.uhhitscam.knightfall.util.ModTags;
import net.uhhitscam.knightfall.util.WeaponSoundsUtil;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = OperationKnightfall.MODID, value = Dist.CLIENT)
public class ModClientEvents {

    private static final WeaponInputState MAIN_STATE = new WeaponInputState(WeaponInputSide.MAIN);
    private static final WeaponInputState OFF_STATE = new WeaponInputState(WeaponInputSide.OFF);

    private static HeldWeaponSnapshot previousMainHeldWeapon = HeldWeaponSnapshot.EMPTY;
    private static HeldWeaponSnapshot previousOffHeldWeapon = HeldWeaponSnapshot.EMPTY;
    private static boolean heldWeaponSoundTrackerInitialized = false;

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onRenderGui(RenderGuiEvent.Post event) {
        HudClient.onRenderHUD(event.getGuiGraphics());
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        HudClient.onClientTick();

        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;

        if (player == null) {
            resetHeldWeaponSoundTracker();
            return;
        }

        tickHeldWeaponEquipSounds(player);

        tickWeaponState(player, MAIN_STATE);
        tickWeaponState(player, OFF_STATE);
    }

    @SubscribeEvent
    public static void onMouseInput(InputEvent.MouseButton.Pre event) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;

        if (player == null) {
            return;
        }

        int button = event.getButton();
        int action = event.getAction();

        if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
            if (action == GLFW.GLFW_PRESS) {
                if (minecraft.screen != null || player.hasEffect(ModEffects.STUN_EFFECT)) {
                    return;
                }

                if (tryBeginFiring(player, MAIN_STATE)) {
                    event.setCanceled(true);
                }
            } else if (action == GLFW.GLFW_RELEASE) {
                endFiring(player, MAIN_STATE);
            }

            return;
        }

        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            if (action == GLFW.GLFW_PRESS) {
                if (minecraft.screen != null || player.hasEffect(ModEffects.STUN_EFFECT)) {
                    return;
                }

                if (tryBeginFiring(player, OFF_STATE)) {
                    event.setCanceled(true);
                }
            } else if (action == GLFW.GLFW_RELEASE) {
                endFiring(player, OFF_STATE);
            }
        }
    }


    public static void stopFiring(boolean mainHand) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        WeaponInputState state = mainHand ? MAIN_STATE : OFF_STATE;

        if (player != null) {
            endFiring(player, state);
        } else {
            resetState(state, true);
        }
    }

    private static void tickHeldWeaponEquipSounds(LocalPlayer player) {
        HeldWeaponSnapshot currentMainHeldWeapon = HeldWeaponSnapshot.fromMainHand(player);
        HeldWeaponSnapshot currentOffHeldWeapon = HeldWeaponSnapshot.fromOffHand(player);

        if (!heldWeaponSoundTrackerInitialized) {
            previousMainHeldWeapon = currentMainHeldWeapon;
            previousOffHeldWeapon = currentOffHeldWeapon;
            heldWeaponSoundTrackerInitialized = true;
            return;
        }

        handleHeldWeaponTransition(player, previousMainHeldWeapon, currentMainHeldWeapon, true);
        handleHeldWeaponTransition(player, previousOffHeldWeapon, currentOffHeldWeapon, false);

        previousMainHeldWeapon = currentMainHeldWeapon;
        previousOffHeldWeapon = currentOffHeldWeapon;
    }

    private static void handleHeldWeaponTransition(
            LocalPlayer player,
            HeldWeaponSnapshot previousWeapon,
            HeldWeaponSnapshot currentWeapon,
            boolean mainHand
    ) {
        if (previousWeapon.equals(currentWeapon)) {
            return;
        }

        if (previousWeapon.projectileWeapon()) {
            playUnequipSound(player, previousWeapon.weaponName());
        }

        if (currentWeapon.projectileWeapon()) {
            playEquipSound(player, currentWeapon.weaponName());
            startLocalEquipCooldown(player, mainHand);
            PayloadRegister.sendToServer(new SSCooldownPacket(mainHand, WeaponCooldownAction.EQUIP));
        }
    }

    private static void startLocalEquipCooldown(LocalPlayer player, boolean mainHand) {
        ItemStack stack = mainHand ? player.getMainHandItem() : player.getOffhandItem();

        if (stack.getItem() instanceof ProjectileItem weapon) {
            weapon.startCooldown(player, stack, WeaponCooldownAction.EQUIP);
        }
    }

    private static void playEquipSound(LocalPlayer player, WeaponName weaponName) {
        player.playSound(WeaponSoundsUtil.getWeaponEquip(weaponName), 0.5F, 1.0F);
    }

    private static void playUnequipSound(LocalPlayer player, WeaponName weaponName) {
        player.playSound(WeaponSoundsUtil.getWeaponUnequip(weaponName), 0.5F, 1.0F);
    }

    private static void resetHeldWeaponSoundTracker() {
        previousMainHeldWeapon = HeldWeaponSnapshot.EMPTY;
        previousOffHeldWeapon = HeldWeaponSnapshot.EMPTY;
        heldWeaponSoundTrackerInitialized = false;
    }

    private static boolean tryBeginFiring(LocalPlayer player, WeaponInputState state) {
        ItemStack stack = state.side.getStack(player);

        if (!(stack.getItem() instanceof ProjectileItem weapon)) {
            return false;
        }

        if (state.side == WeaponInputSide.MAIN && shouldRespectRightClickInteraction(player)) {
            return false;
        }

        if (state.side == WeaponInputSide.OFF && shouldAllowVanillaLeftClick(player)) {
            return false;
        }

        if (weapon.getReloadNSwitchCooldownData(stack).isOnCooldown(player.level())) {
            return true;
        }

        beginFiring(player, state, stack, weapon);
        return true;
    }

    private static void beginFiring(LocalPlayer player, WeaponInputState state, ItemStack stack, ProjectileItem weapon) {
        if (state.beamActive) {
            stopBeam(state);
        }

        resetState(state, true);

        FiringMode firingMode = weapon.getFiringMode(stack);

        if (shouldSendSingleNoAmmoAttempt(firingMode) && weapon.getAmmo(stack) <= 0) {
            PayloadRegister.sendToServer(new SSFireProjectileWeaponPacket(state.side.isMainHand()));
            resetState(state, true);
            return;
        }

        state.firing = true;
        state.heldStack = stack.copy();
        state.activeMode = firingMode;
        state.autoAfterCharge = false;

        switch (firingMode) {
            case FULL_AUTO -> {
                // Full-auto fires from the client tick loop while the button is held.
            }

            case CHARGENSHOOT, CHARGENSHOOTONRELEASE -> {
                state.charging = true;
                state.chargeTicks = 0;
                playChargeSound(player, state, weapon);
            }

            case BEAM -> {
                if (weapon.getAmmo(stack) <= 0) {
                    resetState(state, true);
                    return;
                }

                startBeam(player, state, weapon);
            }

            default -> {
                PayloadRegister.sendToServer(new SSFireProjectileWeaponPacket(state.side.isMainHand()));
            }
        }
    }

    private static boolean shouldSendSingleNoAmmoAttempt(FiringMode firingMode) {
        return firingMode == FiringMode.FULL_AUTO
                || firingMode == FiringMode.CHARGENSHOOT
                || firingMode == FiringMode.CHARGENSHOOTONRELEASE;
    }

    private static void tickWeaponState(LocalPlayer player, WeaponInputState state) {

        if (!state.firing) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.screen != null || player.hasEffect(ModEffects.STUN_EFFECT)) {
            endFiring(player, state);
            return;
        }

        ItemStack currentStack = state.side.getStack(player);
        if (!(currentStack.getItem() instanceof ProjectileItem weapon) || !ItemStack.isSameItem(currentStack, state.heldStack)) {
            cancelFiringAfterStackChanged(player, state);
            return;
        }

        FiringMode firingMode = weapon.getFiringMode(currentStack);

        switch (firingMode) {
            case FULL_AUTO -> tickFullAuto(state, currentStack, weapon);
            case CHARGENSHOOT -> tickChargeAndShoot(state, currentStack, weapon);
            case CHARGENSHOOTONRELEASE -> tickChargeUntilRelease(player, state, currentStack, weapon);
            case BEAM -> tickBeam(state, currentStack, weapon);
            default -> {
                // Semi-auto, burst, scatter, sniper, launcher, stun, and repulse fire once on press.
            }
        }
    }

    private static void tickFullAuto(WeaponInputState state, ItemStack stack, ProjectileItem weapon) {
        int currentAmmo = weapon.getAmmo(stack);

        if (currentAmmo <= 0) {
            PayloadRegister.sendToServer(new SSFireProjectileWeaponPacket(state.side.isMainHand()));
            resetState(state, true);
            return;
        }

        PayloadRegister.sendToServer(new SSFireProjectileWeaponPacket(state.side.isMainHand()));
    }

    private static void tickChargeAndShoot(WeaponInputState state, ItemStack stack, ProjectileItem weapon) {
        if (weapon.getAmmo(stack) <= 0) {
            sendSingleNoAmmoAttemptAndStop(state);
            return;
        }

        if (state.autoAfterCharge) {
            tickFullAuto(state, stack, weapon);
            return;
        }

        WeaponName weaponName = weapon.getProjectileWeaponName();
        int threshold = weapon.getChargeThreshold();

        if (state.chargeTicks >= threshold) {
            state.chargeFired = true;
            state.charging = false;
            state.chargeTicks = 0;

            stopChargingSound(state);
            stopFullyChargedSound(state);

            if (weaponName == WeaponName.Z6_ROTARY) {
                state.autoAfterCharge = true;
                return;
            }

            PayloadRegister.sendToServer(new SSFireProjectileWeaponPacket(state.side.isMainHand()));

            state.firing = false;
            return;
        }

        state.chargeTicks++;
    }

    private static void tickChargeUntilRelease(LocalPlayer player, WeaponInputState state, ItemStack stack, ProjectileItem weapon) {
        if (weapon.getAmmo(stack) <= 0) {
            sendSingleNoAmmoAttemptAndStop(state);
            return;
        }

        WeaponName weaponName = weapon.getProjectileWeaponName();
        int threshold = weapon.getChargeThreshold();

        if (state.chargeTicks >= threshold) {
            if (!state.fullyCharged) {
                playFullyChargedSound(player, state, weapon);
                state.fullyCharged = true;
            }

            return;
        }

        state.chargeTicks++;
    }

    private static void sendSingleNoAmmoAttemptAndStop(WeaponInputState state) {
        PayloadRegister.sendToServer(new SSFireProjectileWeaponPacket(state.side.isMainHand()));
        resetState(state, true);
    }

    private static void tickBeam(WeaponInputState state, ItemStack stack, ProjectileItem weapon) {
        if (weapon.getAmmo(stack) <= 0) {
            stopBeam(state);
            resetState(state, true);
            return;
        }

        if (!state.beamActive) {
            PayloadRegister.sendToServer(new SSBeamPacket(state.side.isMainHand(), true));
            state.beamActive = true;
        }
    }

    private static void endFiring(LocalPlayer player, WeaponInputState state) {
        if (!state.firing
                && !state.charging
                && !state.beamActive
                && !state.fullyCharged
                && !state.chargeFired
                && !state.autoAfterCharge) {
            return;
        }

        ItemStack stack = state.side.getStack(player);
        if (!(stack.getItem() instanceof ProjectileItem weapon)) {
            if (state.beamActive) {
                stopBeam(state);
            }

            stopChargingSound(state);
            stopFullyChargedSound(state);
            resetState(state, false);
            return;
        }

        FiringMode firingMode = weapon.getFiringMode(stack);

        if (firingMode == FiringMode.CHARGENSHOOTONRELEASE) {
            finishChargeOnRelease(player, state, weapon);
        } else if (firingMode == FiringMode.CHARGENSHOOT) {
            stopChargingSound(state);
            stopFullyChargedSound(state);

            if (!state.chargeFired) {
                playUnchargeSound(player, state, weapon);
            }
        }

        if (firingMode == FiringMode.BEAM && state.beamActive) {
            stopBeam(state);
        }

        resetState(state, false);
    }

    private static void cancelFiringAfterStackChanged(LocalPlayer player, WeaponInputState state) {
        ProjectileItem oldWeapon = getTrackedWeapon(state);
        FiringMode oldMode = state.activeMode;

        if (state.beamActive) {
            stopBeam(state);
        }

        stopChargingSound(state);
        stopFullyChargedSound(state);
        stopBeamSound(state);

        if (oldWeapon != null && shouldPlayUnchargeOnCancel(oldMode, state)) {
            playUnchargeSound(player, state, oldWeapon);
        }

        resetState(state, false);
    }

    private static ProjectileItem getTrackedWeapon(WeaponInputState state) {
        if (state.heldStack.getItem() instanceof ProjectileItem projectileItem) {
            return projectileItem;
        }

        return null;
    }

    private static boolean shouldPlayUnchargeOnCancel(FiringMode firingMode, WeaponInputState state) {
        if (firingMode == FiringMode.CHARGENSHOOT) {
            return !state.chargeFired && !state.autoAfterCharge;
        }

        if (firingMode == FiringMode.CHARGENSHOOTONRELEASE) {
            return !state.chargeFired;
        }

        return false;
    }

    private static void finishChargeOnRelease(LocalPlayer player, WeaponInputState state, ProjectileItem weapon) {
        int threshold = weapon.getChargeThreshold();

        stopChargingSound(state);
        stopFullyChargedSound(state);

        if (state.chargeTicks >= threshold) {
            PayloadRegister.sendToServer(new SSFireProjectileWeaponPacket(state.side.isMainHand()));
            state.chargeFired = true;
        } else {
            playUnchargeSound(player, state, weapon);
        }
    }

    private static boolean shouldRespectRightClickInteraction(LocalPlayer player) {
        Minecraft minecraft = Minecraft.getInstance();
        HitResult hitResult = minecraft.hitResult;

        if (hitResult == null || player.isShiftKeyDown()) {
            return false;
        }

        if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHitResult = (BlockHitResult) hitResult;
            BlockPos blockPos = blockHitResult.getBlockPos();
            BlockState blockState = player.level().getBlockState(blockPos);

            return blockState.is(ModTags.Blocks.INTERACTABLE_BLOCKS);
        }

        if (hitResult.getType() == HitResult.Type.ENTITY) {
            EntityHitResult entityHitResult = (EntityHitResult) hitResult;
            Entity entity = entityHitResult.getEntity();

            return entity instanceof Villager
                    || entity instanceof WanderingTrader
                    || entity instanceof ItemFrame
                    || entity instanceof GlowItemFrame
                    || entity instanceof ArmorStand
                    || entity instanceof ChestBoat
                    || entity instanceof MinecartChest
                    || entity instanceof MinecartFurnace
                    || entity instanceof MinecartHopper
                    || entity instanceof MinecartCommandBlock;
        }

        return false;
    }

    private static boolean shouldAllowVanillaLeftClick(LocalPlayer player) {
        Minecraft minecraft = Minecraft.getInstance();
        HitResult hitResult = minecraft.hitResult;

        if (hitResult instanceof EntityHitResult entityHitResult) {
            Entity target = entityHitResult.getEntity();
            return player.distanceTo(target) <= 3 && !player.isShiftKeyDown();
        }

        return false;
    }

    private static void playChargeSound(LocalPlayer player, WeaponInputState state, ProjectileItem weapon) {
        ChargingSoundInstance.stopAudio = false;
        state.chargingSound = new ChargingSoundInstance(
                WeaponSoundsUtil.getWeaponCharge(weapon.getProjectileWeaponName()),
                player
        );

        Minecraft.getInstance().getSoundManager().play(state.chargingSound);
    }

    private static void playUnchargeSound(LocalPlayer player, WeaponInputState state, ProjectileItem weapon) {
        ChargingSoundInstance.stopAudio = false;
        ChargingSoundInstance unchargeSound = new ChargingSoundInstance(
                WeaponSoundsUtil.getWeaponUncharge(weapon.getProjectileWeaponName()),
                player
        );

        Minecraft.getInstance().getSoundManager().play(unchargeSound);
    }

    private static void playFullyChargedSound(LocalPlayer player, WeaponInputState state, ProjectileItem weapon) {
        WeaponName weaponName = weapon.getProjectileWeaponName();

        if (!shouldPlayFullyChargedLoop(weaponName)) {
            return;
        }

        FullyChargedSoundInstance.stopAudio = false;
        state.fullyChargedSound = new FullyChargedSoundInstance(
                WeaponSoundsUtil.getWeaponChargeLoop(weaponName),
                player
        );

        Minecraft.getInstance().getSoundManager().play(state.fullyChargedSound);
    }

    private static boolean shouldPlayFullyChargedLoop(WeaponName weaponName) {
        return weaponName != WeaponName.MW20_BRYAR_PISTOL
                && weaponName != WeaponName.RELBY_V10
                && weaponName != WeaponName.C10;
    }

    private static void startBeam(LocalPlayer player, WeaponInputState state, ProjectileItem weapon) {
        state.beamActive = true;
        PayloadRegister.sendToServer(new SSBeamPacket(state.side.isMainHand(), true));

        BeamSoundInstance.stopAudio = false;
        state.beamSound = new BeamSoundInstance(
                WeaponSoundsUtil.getWeaponBeam(weapon.getProjectileWeaponName()),
                player
        );

        Minecraft.getInstance().getSoundManager().play(state.beamSound);
    }

    private static void stopChargingSound(WeaponInputState state) {
        if (state.chargingSound == null) {
            return;
        }

        ChargingSoundInstance.stopAudio = true;
        Minecraft.getInstance().getSoundManager().stop(state.chargingSound);
        state.chargingSound = null;
    }

    private static void stopFullyChargedSound(WeaponInputState state) {
        if (state.fullyChargedSound == null) {
            return;
        }

        FullyChargedSoundInstance.stopAudio = true;
        Minecraft.getInstance().getSoundManager().stop(state.fullyChargedSound);
        state.fullyChargedSound = null;
    }

    private static void stopBeamSound(WeaponInputState state) {
        if (state.beamSound == null) {
            return;
        }

        BeamSoundInstance.stopAudio = true;
        Minecraft.getInstance().getSoundManager().stop(state.beamSound);
        state.beamSound = null;
    }

    private static void stopBeam(WeaponInputState state) {
        PayloadRegister.sendToServer(new SSBeamPacket(state.side.isMainHand(), false));
        state.beamActive = false;
        stopBeamSound(state);
    }

    private static void resetState(WeaponInputState state, boolean stopSounds) {
        state.firing = false;
        state.charging = false;
        state.chargeFired = false;
        state.fullyCharged = false;
        state.beamActive = false;
        state.autoAfterCharge = false;
        state.chargeTicks = 0;
        state.activeMode = null;
        state.heldStack = ItemStack.EMPTY;

        if (stopSounds) {
            stopChargingSound(state);
            stopFullyChargedSound(state);
            stopBeamSound(state);
        }
    }

    private enum WeaponInputSide {
        MAIN(true),
        OFF(false);

        private final boolean mainHand;

        WeaponInputSide(boolean mainHand) {
            this.mainHand = mainHand;
        }

        private boolean isMainHand() {
            return mainHand;
        }

        private ItemStack getStack(LocalPlayer player) {
            return mainHand ? player.getMainHandItem() : player.getOffhandItem();
        }
    }

    private record HeldWeaponSnapshot(
            boolean projectileWeapon,
            WeaponName weaponName,
            int slotIndex
    ) {
        private static final HeldWeaponSnapshot EMPTY = new HeldWeaponSnapshot(false, null, -1);

        private static HeldWeaponSnapshot fromMainHand(LocalPlayer player) {
            ItemStack stack = player.getMainHandItem();

            if (stack.getItem() instanceof ProjectileItem weapon) {
                return new HeldWeaponSnapshot(
                        true,
                        weapon.getProjectileWeaponName(),
                        player.getInventory().selected
                );
            }

            return EMPTY;
        }

        private static HeldWeaponSnapshot fromOffHand(LocalPlayer player) {
            ItemStack stack = player.getOffhandItem();

            if (stack.getItem() instanceof ProjectileItem weapon) {
                return new HeldWeaponSnapshot(
                        true,
                        weapon.getProjectileWeaponName(),
                        -2
                );
            }

            return EMPTY;
        }
    }

    private static final class WeaponInputState {
        private final WeaponInputSide side;

        private boolean firing;
        private boolean charging;
        private boolean chargeFired;
        private boolean fullyCharged;
        private boolean beamActive;
        private boolean autoAfterCharge;

        private int chargeTicks;
        private ItemStack heldStack = ItemStack.EMPTY;
        private FiringMode activeMode;

        private ChargingSoundInstance chargingSound;
        private FullyChargedSoundInstance fullyChargedSound;
        private BeamSoundInstance beamSound;

        private WeaponInputState(WeaponInputSide side) {
            this.side = side;
        }
    }
}