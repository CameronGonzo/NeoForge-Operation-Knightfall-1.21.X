package net.uhhitscam.knightfall.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.effect.custom.StunEffect;
import net.uhhitscam.knightfall.gui.HudClient;
import net.uhhitscam.knightfall.item.custom.FiringMode;
import net.uhhitscam.knightfall.item.custom.MeleeWeaponDefinition;
import net.uhhitscam.knightfall.item.custom.MeleeWeaponItem;
import net.uhhitscam.knightfall.item.custom.MeleeWeaponSound;
import net.uhhitscam.knightfall.item.custom.ProjectileItem;
import net.uhhitscam.knightfall.item.custom.WeaponName;
import net.uhhitscam.knightfall.network.PayloadRegister;
import net.uhhitscam.knightfall.network.SSProjectileWeaponInputPacket;
import net.uhhitscam.knightfall.sound.BeamSoundInstance;
import net.uhhitscam.knightfall.sound.ChargingSoundInstance;
import net.uhhitscam.knightfall.sound.FullyChargedSoundInstance;
import net.uhhitscam.knightfall.util.WeaponSoundsUtil;

@EventBusSubscriber(modid = OperationKnightfall.MODID, value = Dist.CLIENT)
public final class ModClientEvents {
    private static final WeaponInputState MAIN_STATE = new WeaponInputState(WeaponInputSide.MAIN);
    private static final WeaponInputState OFF_STATE = new WeaponInputState(WeaponInputSide.OFF);

    private static HeldWeaponSnapshot previousMainHeldWeapon = HeldWeaponSnapshot.EMPTY;
    private static HeldWeaponSnapshot previousOffHeldWeapon = HeldWeaponSnapshot.EMPTY;
    private static boolean heldWeaponSoundTrackerInitialized;
    private static long mainEquipReadyTime;
    private static long offEquipReadyTime;

    private ModClientEvents() {}

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
            resetState(MAIN_STATE, true);
            resetState(OFF_STATE, true);
            resetHeldWeaponSoundTracker();
            return;
        }

        tickHeldWeaponEquipSounds(player);
        releaseStoppedInputs(minecraft, player);
        tickWeaponState(player, MAIN_STATE);
        tickWeaponState(player, OFF_STATE);
    }

    @SubscribeEvent
    public static void onProjectileWeaponUse(ProjectileWeaponUseEvent event) {
        if (event.getPlayer() instanceof LocalPlayer player) {
            tryBeginFiring(player, MAIN_STATE);
        }
    }

    @SubscribeEvent
    public static void onInteractionKeyMapping(InputEvent.InteractionKeyMappingTriggered event) {
        if (event.isUseItem() && isInputClaimed(MAIN_STATE)) {
            event.setSwingHand(false);
            event.setCanceled(true);
            return;
        }

        if (!event.isAttack()) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player == null) {
            return;
        }

        if (isInputClaimed(OFF_STATE)) {
            event.setSwingHand(false);
            event.setCanceled(true);
            return;
        }

        if (!(player.getOffhandItem().getItem() instanceof ProjectileItem)) {
            return;
        }

        if (canStartInput(player) && shouldAllowVanillaMelee(player)) {
            return;
        }

        if (tryBeginFiring(player, OFF_STATE)) {
            event.setSwingHand(false);
            event.setCanceled(true);
        }
    }

    public static void stopFiring(boolean mainHand) {
        LocalPlayer player = Minecraft.getInstance().player;
        WeaponInputState state = mainHand ? MAIN_STATE : OFF_STATE;
        if (player == null) {
            resetState(state, true);
        } else {
            boolean keyStillDown = mainHand
                    ? Minecraft.getInstance().options.keyUse.isDown()
                    : Minecraft.getInstance().options.keyAttack.isDown();
            if (keyStillDown) {
                cancelFiringUntilRelease(player, state);
            } else {
                endFiring(player, state);
            }
        }
    }

    private static boolean canStartInput(LocalPlayer player) {
        return Minecraft.getInstance().screen == null && !StunEffect.isStunned(player);
    }

    private static void releaseStoppedInputs(Minecraft minecraft, LocalPlayer player) {
        if (isInputClaimed(MAIN_STATE) && !minecraft.options.keyUse.isDown()) {
            endFiring(player, MAIN_STATE);
        }

        if (isInputClaimed(OFF_STATE) && !minecraft.options.keyAttack.isDown()) {
            endFiring(player, OFF_STATE);
        }
    }

    private static boolean tryBeginFiring(LocalPlayer player, WeaponInputState state) {
        if (isInputClaimed(state)) {
            return true;
        }

        tickHeldWeaponEquipSounds(player);

        ItemStack stack = state.side.getStack(player);
        if (!(stack.getItem() instanceof ProjectileItem weapon)) {
            return false;
        }

        if (!canStartInput(player) || isLocalInputOnCooldown(player, state, stack, weapon)) {
            state.suppressUntilRelease = true;
            return true;
        }

        beginFiring(player, state, stack, weapon);
        return true;
    }

    private static boolean isLocalInputOnCooldown(
            LocalPlayer player,
            WeaponInputState state,
            ItemStack stack,
            ProjectileItem weapon
    ) {
        long equipReadyTime = state.side.isMainHand() ? mainEquipReadyTime : offEquipReadyTime;
        return player.level().getGameTime() < equipReadyTime
                || weapon.isActionOnCooldown(player.level(), stack);
    }

    private static boolean isInputClaimed(WeaponInputState state) {
        return state.inputDown || state.suppressUntilRelease;
    }

    private static void beginFiring(LocalPlayer player, WeaponInputState state, ItemStack stack, ProjectileItem weapon) {
        resetState(state, true);
        state.inputDown = true;
        state.heldStack = stack;
        state.selectedSlot = state.side.isMainHand() ? player.getInventory().selected : -1;
        state.activeMode = weapon.getFiringMode(stack);

        PayloadRegister.sendToServer(new SSProjectileWeaponInputPacket(state.side.isMainHand(), true));

        boolean hasRequiredAmmo = weapon.getAmmo(stack) > 0 || state.activeMode == FiringMode.REPULSE;
        switch (state.activeMode) {
            case CHARGENSHOOT, CHARGENSHOOTONRELEASE -> {
                if (hasRequiredAmmo) {
                    state.charging = true;
                    playChargeSound(player, state, weapon);
                }
            }
            case BEAM -> {
                if (weapon.getAmmo(stack) > 0 && !weapon.isOverheated(stack, player.level())) {
                    startBeamSound(player, state, weapon);
                }
            }
            default -> {
            }
        }
    }

    private static void tickWeaponState(LocalPlayer player, WeaponInputState state) {
        if (!state.inputDown) {
            return;
        }

        if (!canStartInput(player)) {
            cancelFiringUntilRelease(player, state);
            return;
        }

        ItemStack currentStack = state.side.getStack(player);
        if (state.side.isMainHand() && player.getInventory().selected != state.selectedSlot
                || currentStack.getItem() != state.heldStack.getItem()
                || !(currentStack.getItem() instanceof ProjectileItem weapon)
                || weapon.getFiringMode(currentStack) != state.activeMode) {
            cancelFiringUntilRelease(player, state);
            return;
        }

        if (state.activeMode == FiringMode.CHARGENSHOOT || state.activeMode == FiringMode.CHARGENSHOOTONRELEASE) {
            tickChargePresentation(player, state, weapon);
        } else if (state.activeMode == FiringMode.BEAM) {
            if (weapon.getAmmo(currentStack) <= 0 || weapon.isOverheated(currentStack, player.level())) {
                stopBeamSound(state);
            } else if (state.beamSound == null) {
                startBeamSound(player, state, weapon);
            }
        }
    }

    private static void tickChargePresentation(LocalPlayer player, WeaponInputState state, ProjectileItem weapon) {
        if (!state.charging) {
            return;
        }

        state.chargeTicks++;
        if (state.chargeTicks < weapon.getChargeThreshold()) {
            return;
        }

        if (state.activeMode == FiringMode.CHARGENSHOOTONRELEASE) {
            if (!state.fullyCharged) {
                playFullyChargedSound(player, state, weapon);
                state.fullyCharged = true;
            }
            return;
        }

        state.charging = false;
        state.chargeFired = true;
        stopChargingSound(state);
        stopFullyChargedSound(state);
    }

    private static void endFiring(LocalPlayer player, WeaponInputState state) {
        if (!state.inputDown) {
            resetState(state, true);
            return;
        }

        PayloadRegister.sendToServer(new SSProjectileWeaponInputPacket(state.side.isMainHand(), false));
        ProjectileItem weapon = getTrackedWeapon(state);
        if (weapon != null && shouldPlayUnchargeOnStop(state, weapon)) {
            playUnchargeSound(player, state, weapon);
        }

        resetState(state, true);
    }

    private static void cancelFiringUntilRelease(LocalPlayer player, WeaponInputState state) {
        if (state.inputDown) {
            PayloadRegister.sendToServer(new SSProjectileWeaponInputPacket(state.side.isMainHand(), false));
            ProjectileItem weapon = getTrackedWeapon(state);
            if (weapon != null && shouldPlayUnchargeOnStop(state, weapon)) {
                playUnchargeSound(player, state, weapon);
            }
        }

        resetState(state, true);
        state.suppressUntilRelease = true;
    }

    private static boolean shouldPlayUnchargeOnStop(WeaponInputState state, ProjectileItem weapon) {
        if (state.activeMode == FiringMode.CHARGENSHOOTONRELEASE) {
            return state.charging && state.chargeTicks < weapon.getChargeThreshold();
        }
        return state.activeMode == FiringMode.CHARGENSHOOT && state.charging && !state.chargeFired;
    }

    private static ProjectileItem getTrackedWeapon(WeaponInputState state) {
        return state.heldStack.getItem() instanceof ProjectileItem weapon ? weapon : null;
    }

    private static boolean shouldAllowVanillaMelee(LocalPlayer player) {
        if (!(Minecraft.getInstance().hitResult instanceof EntityHitResult entityHitResult)) {
            return false;
        }

        Entity target = entityHitResult.getEntity();
        return !player.isShiftKeyDown() && player.canInteractWithEntity(target, 0.0);
    }

    private static void tickHeldWeaponEquipSounds(LocalPlayer player) {
        HeldWeaponSnapshot currentMain = HeldWeaponSnapshot.fromMainHand(player);
        HeldWeaponSnapshot currentOff = HeldWeaponSnapshot.fromOffHand(player);

        if (!heldWeaponSoundTrackerInitialized) {
            previousMainHeldWeapon = currentMain;
            previousOffHeldWeapon = currentOff;
            heldWeaponSoundTrackerInitialized = true;
            return;
        }

        handleHeldWeaponTransition(player, previousMainHeldWeapon, currentMain, true);
        handleHeldWeaponTransition(player, previousOffHeldWeapon, currentOff, false);
        previousMainHeldWeapon = currentMain;
        previousOffHeldWeapon = currentOff;
    }

    private static void handleHeldWeaponTransition(
            LocalPlayer player,
            HeldWeaponSnapshot previous,
            HeldWeaponSnapshot current,
            boolean mainHand
    ) {
        if (previous.equals(current)) {
            return;
        }
        if (previous.projectileWeapon()) {
            player.playSound(WeaponSoundsUtil.getWeaponUnequip(previous.weaponName()), 0.5F, 1.0F);
        } else if (previous.meleeWeapon() != null) {
            playLocalMeleeSound(player, previous.meleeWeapon().audio().unequipSound());
        }
        if (current.projectileWeapon()) {
            player.playSound(WeaponSoundsUtil.getWeaponEquip(current.weaponName()), 0.5F, 1.0F);
            ItemStack stack = mainHand ? player.getMainHandItem() : player.getOffhandItem();
            if (stack.getItem() instanceof ProjectileItem weapon) {
                long equipReadyTime = player.level().getGameTime() + weapon.getEquipTime();
                if (mainHand) {
                    mainEquipReadyTime = equipReadyTime;
                } else {
                    offEquipReadyTime = equipReadyTime;
                }
            }
        } else if (current.meleeWeapon() != null) {
            playLocalMeleeSound(player, current.meleeWeapon().audio().equipSound());
            if (mainHand) {
                mainEquipReadyTime = 0;
            } else {
                offEquipReadyTime = 0;
            }
        } else if (mainHand) {
            mainEquipReadyTime = 0;
        } else {
            offEquipReadyTime = 0;
        }
    }

    private static void playLocalMeleeSound(LocalPlayer player, MeleeWeaponSound sound) {
        player.playSound(sound.sound().get(), sound.volume(), sound.pitch());
    }

    private static void resetHeldWeaponSoundTracker() {
        previousMainHeldWeapon = HeldWeaponSnapshot.EMPTY;
        previousOffHeldWeapon = HeldWeaponSnapshot.EMPTY;
        heldWeaponSoundTrackerInitialized = false;
        mainEquipReadyTime = 0;
        offEquipReadyTime = 0;
    }

    private static void playChargeSound(LocalPlayer player, WeaponInputState state, ProjectileItem weapon) {
        state.chargingSound = new ChargingSoundInstance(
                WeaponSoundsUtil.getWeaponCharge(weapon.getProjectileWeaponName()), player
        );
        Minecraft.getInstance().getSoundManager().play(state.chargingSound);
    }

    private static void playUnchargeSound(LocalPlayer player, WeaponInputState state, ProjectileItem weapon) {
        ChargingSoundInstance unchargeSound = new ChargingSoundInstance(
                WeaponSoundsUtil.getWeaponUncharge(weapon.getProjectileWeaponName()), player
        );
        Minecraft.getInstance().getSoundManager().play(unchargeSound);
    }

    private static void playFullyChargedSound(LocalPlayer player, WeaponInputState state, ProjectileItem weapon) {
        WeaponName weaponName = weapon.getProjectileWeaponName();
        if (weaponName == WeaponName.MW20_BRYAR_PISTOL
                || weaponName == WeaponName.RELBY_V10
                || weaponName == WeaponName.C10) {
            return;
        }

        state.fullyChargedSound = new FullyChargedSoundInstance(
                WeaponSoundsUtil.getWeaponChargeLoop(weaponName), player
        );
        Minecraft.getInstance().getSoundManager().play(state.fullyChargedSound);
    }

    private static void startBeamSound(LocalPlayer player, WeaponInputState state, ProjectileItem weapon) {
        state.beamSound = new BeamSoundInstance(
                WeaponSoundsUtil.getWeaponBeam(weapon.getProjectileWeaponName()), player
        );
        Minecraft.getInstance().getSoundManager().play(state.beamSound);
    }

    private static void stopChargingSound(WeaponInputState state) {
        if (state.chargingSound != null) {
            Minecraft.getInstance().getSoundManager().stop(state.chargingSound);
            state.chargingSound = null;
        }
    }

    private static void stopFullyChargedSound(WeaponInputState state) {
        if (state.fullyChargedSound != null) {
            Minecraft.getInstance().getSoundManager().stop(state.fullyChargedSound);
            state.fullyChargedSound = null;
        }
    }

    private static void stopBeamSound(WeaponInputState state) {
        if (state.beamSound != null) {
            Minecraft.getInstance().getSoundManager().stop(state.beamSound);
            state.beamSound = null;
        }
    }

    private static void resetState(WeaponInputState state, boolean stopSounds) {
        if (stopSounds) {
            stopChargingSound(state);
            stopFullyChargedSound(state);
            stopBeamSound(state);
        }

        state.inputDown = false;
        state.suppressUntilRelease = false;
        state.charging = false;
        state.chargeFired = false;
        state.fullyCharged = false;
        state.chargeTicks = 0;
        state.selectedSlot = -1;
        state.heldStack = ItemStack.EMPTY;
        state.activeMode = null;
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
            MeleeWeaponDefinition meleeWeapon,
            int slotIndex
    ) {
        private static final HeldWeaponSnapshot EMPTY = new HeldWeaponSnapshot(false, null, null, -1);

        private static HeldWeaponSnapshot fromMainHand(LocalPlayer player) {
            ItemStack stack = player.getMainHandItem();
            if (stack.getItem() instanceof ProjectileItem weapon) {
                return new HeldWeaponSnapshot(true, weapon.getProjectileWeaponName(), null, player.getInventory().selected);
            }
            if (stack.getItem() instanceof MeleeWeaponItem weapon) {
                return new HeldWeaponSnapshot(false, null, weapon.getDefinition(), player.getInventory().selected);
            }
            return EMPTY;
        }

        private static HeldWeaponSnapshot fromOffHand(LocalPlayer player) {
            ItemStack stack = player.getOffhandItem();
            if (stack.getItem() instanceof ProjectileItem weapon) {
                return new HeldWeaponSnapshot(true, weapon.getProjectileWeaponName(), null, -2);
            }
            if (stack.getItem() instanceof MeleeWeaponItem weapon) {
                return new HeldWeaponSnapshot(false, null, weapon.getDefinition(), -2);
            }
            return EMPTY;
        }
    }

    private static final class WeaponInputState {
        private final WeaponInputSide side;
        private boolean inputDown;
        private boolean suppressUntilRelease;
        private boolean charging;
        private boolean chargeFired;
        private boolean fullyCharged;
        private int chargeTicks;
        private int selectedSlot = -1;
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
