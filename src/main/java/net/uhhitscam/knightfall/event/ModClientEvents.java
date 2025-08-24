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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
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
import net.uhhitscam.knightfall.item.custom.ProjectileItem;
import net.uhhitscam.knightfall.item.custom.FiringMode;
import net.uhhitscam.knightfall.item.custom.WeaponName;
import net.uhhitscam.knightfall.network.PayloadRegister;
import net.uhhitscam.knightfall.network.SSFireProjectileWeaponPacket;
import net.uhhitscam.knightfall.sound.ChargingSoundInstance;
import net.uhhitscam.knightfall.sound.FullyChargedSoundInstance;
import net.uhhitscam.knightfall.util.ModTags;
import net.uhhitscam.knightfall.util.WeaponSoundsUtil;
import net.uhhitscam.knightfall.util.WeaponTimingUtil;
import org.lwjgl.glfw.GLFW;

import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

@EventBusSubscriber(modid = OperationKnightfall.MODID, value = Dist.CLIENT)
public class ModClientEvents {
    public static boolean mainFiring = false;
    public static boolean offFiring = false;
    public static boolean mainCharging = false;
    public static boolean offCharging = false;
    public static boolean mainChargeFired = false;
    public static boolean offChargeFired = false;
    public static boolean mainFullyCharged = false;
    public static boolean offFullyCharged = false;
    public static int mainCharge = 0;
    public static int offCharge = 0;
    private static Timer mainFullAutoTimer = new Timer();
    private static Timer offFullAutoTimer = new Timer();
    private static Timer mainChargeNShootTimer = new Timer();
    private static Timer offChargeNShootTimer = new Timer();
    private static Timer mainChargeNShootOnReleaseTimer = new Timer();
    private static Timer offChargeNShootOnReleaseTimer = new Timer();
    public static ChargingSoundInstance mainChargingSoundInstance;
    public static ChargingSoundInstance offChargingSoundInstance;
    public static FullyChargedSoundInstance mainFullyChargedSoundInstance;
    public static FullyChargedSoundInstance offFullyChargedSoundInstance;

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onRenderGui(RenderGuiEvent.Post event) {
        HudClient.onRenderHUD(event.getGuiGraphics());
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        HudClient.onClientTick();
    }

    @SubscribeEvent
    public static void onMouseInput(InputEvent.MouseButton.Pre event) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;

        if (player == null) return;

        if (minecraft.screen != null) {
            return;
        }

        if (event.getButton() == GLFW.GLFW_MOUSE_BUTTON_RIGHT && event.getAction() == GLFW.GLFW_PRESS) {
            if (minecraft.screen != null || player.hasEffect(ModEffects.STUN_EFFECT)) {
                return;
            }

            boolean interacting = false;

            HitResult hitResult = minecraft.hitResult;
            if (hitResult.getType() == HitResult.Type.BLOCK && !player.isShiftKeyDown()) {
                BlockHitResult blockHitResult = (BlockHitResult) hitResult;
                BlockPos blockPos = blockHitResult.getBlockPos();
                BlockState blockState = player.level().getBlockState(blockPos);
                if (!player.isShiftKeyDown() && blockState.is(ModTags.Blocks.INTERACTABLE_BLOCKS)) {
                    interacting = true;
                }
            }

            if (hitResult.getType() == HitResult.Type.ENTITY && !player.isShiftKeyDown()) {
                EntityHitResult entityHitResult = (EntityHitResult) hitResult;
                Entity entity = entityHitResult.getEntity();
                if (entity instanceof Villager || entity instanceof WanderingTrader || entity instanceof ItemFrame || entity instanceof GlowItemFrame || entity instanceof ArmorStand) {
                    return;
                }
            }

            ItemStack mainHandItem = player.getMainHandItem();
            mainFiring = true;

            if (!interacting && mainHandItem.getItem() instanceof ProjectileItem projectileWeapon) {
                if (FiringMode.FULL_AUTO.equals(projectileWeapon.getFiringMode(mainHandItem))) {
                    scheduleMainFullAutoFiring(player, mainHandItem, projectileWeapon);
                } else if (FiringMode.CHARGENSHOOT.equals(projectileWeapon.getFiringMode(mainHandItem))) {
                    scheduleMainChargeNShoot(player, mainHandItem, projectileWeapon);
                    ChargingSoundInstance.stopAudio = false;
                    mainChargingSoundInstance = new ChargingSoundInstance(WeaponSoundsUtil.getWeaponCharge(projectileWeapon.getProjectileWeaponName()), player);
                    Minecraft.getInstance().getSoundManager().play(mainChargingSoundInstance);
                } else if (FiringMode.CHARGENSHOOTONRELEASE.equals(projectileWeapon.getFiringMode(mainHandItem))) {
                    scheduleMainChargeNShootOnRelease(player, mainHandItem, projectileWeapon);
                    ChargingSoundInstance.stopAudio = false;
                    mainChargingSoundInstance = new ChargingSoundInstance(WeaponSoundsUtil.getWeaponCharge(projectileWeapon.getProjectileWeaponName()), player);
                    Minecraft.getInstance().getSoundManager().play(mainChargingSoundInstance);
                } else {
                    PayloadRegister.sendToServer(new SSFireProjectileWeaponPacket(true));

                }
                event.setCanceled(true);
            }
        }

        if (event.getButton() == GLFW.GLFW_MOUSE_BUTTON_RIGHT && event.getAction() == GLFW.GLFW_RELEASE) {
            ItemStack mainHandItem = player.getMainHandItem();

            if (mainHandItem.getItem() instanceof ProjectileItem projectileWeapon) {
                if (mainCharge >= WeaponTimingUtil.getProjectileWeaponChargeThreshold(projectileWeapon.getProjectileWeaponName())) {
                    PayloadRegister.sendToServer(new SSFireProjectileWeaponPacket(true));
                    FullyChargedSoundInstance.stopAudio = true;
                    Minecraft.getInstance().getSoundManager().stop(mainFullyChargedSoundInstance);
                } else {
                    ChargingSoundInstance.stopAudio = true;
                    Minecraft.getInstance().getSoundManager().stop(mainChargingSoundInstance);

                    if (projectileWeapon.getProjectileWeaponName().equals(WeaponName.Z6_ROTARY)) {
                        ChargingSoundInstance.stopAudio = false;
                        mainChargingSoundInstance = new ChargingSoundInstance(WeaponSoundsUtil.getWeaponUncharge(projectileWeapon.getProjectileWeaponName()), player);
                        Minecraft.getInstance().getSoundManager().play(mainChargingSoundInstance);
                    }

                    if (FiringMode.CHARGENSHOOTONRELEASE.equals(projectileWeapon.getFiringMode(mainHandItem))) {
                        ChargingSoundInstance.stopAudio = false;
                        mainChargingSoundInstance = new ChargingSoundInstance(WeaponSoundsUtil.getWeaponUncharge(projectileWeapon.getProjectileWeaponName()), player);
                        Minecraft.getInstance().getSoundManager().play(mainChargingSoundInstance);
                    }
                }
            }
            mainFiring = false;
            mainFullyCharged = false;
            mainFullAutoTimer.cancel();
            mainFullAutoTimer = new Timer();
            mainChargeNShootTimer.cancel();
            mainChargeNShootTimer = new Timer();
            mainChargeNShootOnReleaseTimer.cancel();
            mainChargeNShootOnReleaseTimer = new Timer();
            mainCharge = 0;
            mainCharging = false;

            if (mainHandItem.getItem() instanceof ProjectileItem projectileWeapon && !mainChargeFired && !FiringMode.CHARGENSHOOTONRELEASE.equals(projectileWeapon.getFiringMode(mainHandItem))) {
                if (FiringMode.CHARGENSHOOT.equals(projectileWeapon.getFiringMode(mainHandItem))) {
                    ChargingSoundInstance.stopAudio = false;
                    mainChargingSoundInstance = new ChargingSoundInstance(WeaponSoundsUtil.getWeaponUncharge(projectileWeapon.getProjectileWeaponName()), player);
                    Minecraft.getInstance().getSoundManager().play(mainChargingSoundInstance);
                }
            } else if (mainChargeFired) {
                mainChargeFired = false;
            }

            if (mainHandItem.getItem() instanceof ProjectileItem projectileWeapon) {
                if (FiringMode.FULL_AUTO.equals(projectileWeapon.getFiringMode(mainHandItem)) || FiringMode.CHARGENSHOOT.equals(projectileWeapon.getFiringMode(mainHandItem)) || FiringMode.CHARGENSHOOTONRELEASE.equals(projectileWeapon.getFiringMode(mainHandItem))) {
                    mainFiring = false;
                    mainFullyCharged = false;
                    mainFullAutoTimer.cancel();
                    mainFullAutoTimer = new Timer();
                    mainChargeNShootTimer.cancel();
                    mainChargeNShootTimer = new Timer();
                    mainChargeNShootOnReleaseTimer.cancel();
                    mainChargeNShootOnReleaseTimer = new Timer();
                    mainCharge = 0;
                    mainCharging = false;
                }
            }
        }

        if (event.getButton() == GLFW.GLFW_MOUSE_BUTTON_LEFT && event.getAction() == GLFW.GLFW_PRESS) {

            if (minecraft.screen != null || player.hasEffect(ModEffects.STUN_EFFECT)) {
                return;
            }

            boolean punching = false;
            ItemStack offHandItem = player.getOffhandItem();

            if (offHandItem.getItem() instanceof ProjectileItem) {
                offFiring = true;
                HitResult hitResult = minecraft.hitResult;
                if (hitResult instanceof EntityHitResult entityHitResult) {
                    Entity target = entityHitResult.getEntity();
                    if (player.distanceTo(target) <= 3 && !player.isShiftKeyDown()) {
                        punching = true;
                    }
                }
            }

            if (!punching && offHandItem.getItem() instanceof ProjectileItem projectileWeapon) {
                if (FiringMode.FULL_AUTO.equals(projectileWeapon.getFiringMode(offHandItem))) {
                    scheduleOffFullAutoFiring(player, offHandItem, projectileWeapon);
                } else if (FiringMode.CHARGENSHOOT.equals(projectileWeapon.getFiringMode(offHandItem))) {
                    scheduleOffChargeNShoot(player, offHandItem, projectileWeapon);
                    ChargingSoundInstance.stopAudio = false;
                    offChargingSoundInstance = new ChargingSoundInstance(WeaponSoundsUtil.getWeaponCharge(projectileWeapon.getProjectileWeaponName()), player);
                    Minecraft.getInstance().getSoundManager().play(offChargingSoundInstance);
                } else if (FiringMode.CHARGENSHOOTONRELEASE.equals(projectileWeapon.getFiringMode(offHandItem))) {
                    scheduleOffChargeNShootOnRelease(player, offHandItem, projectileWeapon);
                    ChargingSoundInstance.stopAudio = false;
                    offChargingSoundInstance = new ChargingSoundInstance(WeaponSoundsUtil.getWeaponCharge(projectileWeapon.getProjectileWeaponName()), player);
                    Minecraft.getInstance().getSoundManager().play(offChargingSoundInstance);
                } else {
                    PayloadRegister.sendToServer(new SSFireProjectileWeaponPacket(false));
                }
                event.setCanceled(true);
            }
        }

        if (event.getButton() == GLFW.GLFW_MOUSE_BUTTON_LEFT && event.getAction() == GLFW.GLFW_RELEASE) {
            ItemStack offHandItem = player.getOffhandItem();

            if (offHandItem.getItem() instanceof ProjectileItem projectileWeapon) {
                if (offCharge >= WeaponTimingUtil.getProjectileWeaponChargeThreshold(projectileWeapon.getProjectileWeaponName())) {
                    PayloadRegister.sendToServer(new SSFireProjectileWeaponPacket(false));
                    FullyChargedSoundInstance.stopAudio = true;
                    Minecraft.getInstance().getSoundManager().stop(offFullyChargedSoundInstance);
                } else {
                    ChargingSoundInstance.stopAudio = true;
                    Minecraft.getInstance().getSoundManager().stop(offChargingSoundInstance);

                    if (projectileWeapon.getProjectileWeaponName().equals(WeaponName.Z6_ROTARY)) {
                        ChargingSoundInstance.stopAudio = false;
                        offChargingSoundInstance = new ChargingSoundInstance(WeaponSoundsUtil.getWeaponUncharge(projectileWeapon.getProjectileWeaponName()), player);
                        Minecraft.getInstance().getSoundManager().play(offChargingSoundInstance);
                    }

                    if (FiringMode.CHARGENSHOOTONRELEASE.equals(projectileWeapon.getFiringMode(offHandItem))) {
                        ChargingSoundInstance.stopAudio = false;
                        offChargingSoundInstance = new ChargingSoundInstance(WeaponSoundsUtil.getWeaponUncharge(projectileWeapon.getProjectileWeaponName()), player);
                        Minecraft.getInstance().getSoundManager().play(offChargingSoundInstance);
                    }
                }
            }
            offFiring = false;
            offFullyCharged = false;
            offFullAutoTimer.cancel();
            offFullAutoTimer = new Timer();
            offChargeNShootTimer.cancel();
            offChargeNShootTimer = new Timer();
            offChargeNShootOnReleaseTimer.cancel();
            offChargeNShootOnReleaseTimer = new Timer();
            offCharge = 0;
            offCharging = false;

            if (offHandItem.getItem() instanceof ProjectileItem projectileWeapon && !offChargeFired && !FiringMode.CHARGENSHOOTONRELEASE.equals(projectileWeapon.getFiringMode(offHandItem))) {
                if (FiringMode.CHARGENSHOOT.equals(projectileWeapon.getFiringMode(offHandItem))) {
                    ChargingSoundInstance.stopAudio = false;
                    offChargingSoundInstance = new ChargingSoundInstance(WeaponSoundsUtil.getWeaponUncharge(projectileWeapon.getProjectileWeaponName()), player);
                    Minecraft.getInstance().getSoundManager().play(offChargingSoundInstance);
                }
            } else if (offChargeFired) {
                offChargeFired = false;
            }

            if (offHandItem.getItem() instanceof ProjectileItem projectileWeapon) {
                if (FiringMode.FULL_AUTO.equals(projectileWeapon.getFiringMode(offHandItem)) || FiringMode.CHARGENSHOOT.equals(projectileWeapon.getFiringMode(offHandItem)) || FiringMode.CHARGENSHOOTONRELEASE.equals(projectileWeapon.getFiringMode(offHandItem))) {
                    offFiring = false;
                    offFullyCharged = false;
                    offFullAutoTimer.cancel();
                    offFullAutoTimer = new Timer();
                    offChargeNShootTimer.cancel();
                    offChargeNShootTimer = new Timer();
                    offChargeNShootOnReleaseTimer.cancel();
                    offChargeNShootOnReleaseTimer = new Timer();
                    offCharge = 0;
                    offCharging = false;
                }
            }
        }
    }

    private static void scheduleMainFullAutoFiring(LocalPlayer player, ItemStack heldItem, ProjectileItem projectileWeapon) {
        mainFullAutoTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!mainFiring) return;

                if (!ItemStack.isSameItem(player.getMainHandItem(), heldItem) || player.getMainHandItem().equals(ItemStack.EMPTY)) {
                    mainFiring = false;
                    mainFullAutoTimer.cancel();
                    mainFullAutoTimer = new Timer();

                    if (projectileWeapon.getProjectileWeaponName().equals(WeaponName.Z6_ROTARY)) {
                        ChargingSoundInstance.stopAudio = true;
                        Minecraft.getInstance().getSoundManager().stop(mainChargingSoundInstance);
                        ChargingSoundInstance.stopAudio = false;
                        mainChargingSoundInstance = new ChargingSoundInstance(WeaponSoundsUtil.getWeaponUncharge(projectileWeapon.getProjectileWeaponName()), player);
                        Minecraft.getInstance().getSoundManager().play(mainChargingSoundInstance);
                    }
                    return;
                }

                PayloadRegister.sendToServer(new SSFireProjectileWeaponPacket(true));

                scheduleMainFullAutoFiring(player, player.getMainHandItem(), projectileWeapon);
            }
        }, 50);
    }

    private static void scheduleOffFullAutoFiring(LocalPlayer player, ItemStack heldItem, ProjectileItem projectileWeapon) {
        offFullAutoTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!offFiring) return;

                if (!ItemStack.isSameItem(player.getOffhandItem(), heldItem) || player.getOffhandItem().equals(ItemStack.EMPTY)) {
                    offFiring = false;
                    offFullAutoTimer.cancel();
                    offFullAutoTimer = new Timer();

                    if (projectileWeapon.getProjectileWeaponName().equals(WeaponName.Z6_ROTARY)) {
                        ChargingSoundInstance.stopAudio = true;
                        Minecraft.getInstance().getSoundManager().stop(offChargingSoundInstance);
                        ChargingSoundInstance.stopAudio = false;
                        offChargingSoundInstance = new ChargingSoundInstance(WeaponSoundsUtil.getWeaponUncharge(projectileWeapon.getProjectileWeaponName()), player);
                        Minecraft.getInstance().getSoundManager().play(offChargingSoundInstance);
                    }
                    return;
                }

                PayloadRegister.sendToServer(new SSFireProjectileWeaponPacket(false));

                scheduleOffFullAutoFiring(player, player.getOffhandItem(), projectileWeapon);
            }
        }, 50);
    }

    private static void scheduleMainChargeNShoot(LocalPlayer player, ItemStack heldItem, ProjectileItem projectileWeapon) {
        mainChargeNShootTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!mainFiring) return;

                if (!ItemStack.isSameItem(player.getMainHandItem(), heldItem) || player.getMainHandItem().equals(ItemStack.EMPTY)) {
                    mainCharge = 0;
                    mainCharging = false;
                    mainFiring = false;
                    mainChargeNShootTimer.cancel();
                    mainChargeNShootTimer = new Timer();
                    ChargingSoundInstance.stopAudio = true;
                    Minecraft.getInstance().getSoundManager().stop(mainChargingSoundInstance);
                    ChargingSoundInstance.stopAudio = false;
                    mainChargingSoundInstance = new ChargingSoundInstance(WeaponSoundsUtil.getWeaponUncharge(projectileWeapon.getProjectileWeaponName()), player);
                    Minecraft.getInstance().getSoundManager().play(mainChargingSoundInstance);
                    return;
                }

                if (mainCharge >= WeaponTimingUtil.getProjectileWeaponChargeThreshold(projectileWeapon.getProjectileWeaponName())) {
                    if(projectileWeapon.getProjectileWeaponName().equals(WeaponName.Z6_ROTARY)) {
                        mainChargeFired = true;
                        mainFiring = true;
                        mainChargeNShootTimer.cancel();
                        mainChargeNShootTimer = new Timer();
                        mainCharge = 0;
                        mainCharging = false;
                        scheduleMainFullAutoFiring(player, heldItem, projectileWeapon);
                    } else {
                        PayloadRegister.sendToServer(new SSFireProjectileWeaponPacket(true));
                        mainChargeFired = true;
                        mainFiring = false;
                        mainChargeNShootTimer.cancel();
                        mainChargeNShootTimer = new Timer();
                        mainCharge = 0;
                        mainCharging = false;
                    }
                }

                if (!mainCharging) {
                    mainCharging = true;
                } else {
                    mainCharge++;
                }

                scheduleMainChargeNShoot(player, player.getMainHandItem(), projectileWeapon);
            }
        }, 50);
    }

    private static void scheduleOffChargeNShoot(LocalPlayer player, ItemStack heldItem, ProjectileItem projectileWeapon) {
        offChargeNShootTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!offFiring) return;

                if (!ItemStack.isSameItem(player.getOffhandItem(), heldItem) || player.getOffhandItem().equals(ItemStack.EMPTY)) {
                    offCharge = 0;
                    offCharging = false;
                    offFiring = false;
                    offChargeNShootTimer.cancel();
                    offChargeNShootTimer = new Timer();
                    ChargingSoundInstance.stopAudio = true;
                    Minecraft.getInstance().getSoundManager().stop(offChargingSoundInstance);
                    ChargingSoundInstance.stopAudio = false;
                    offChargingSoundInstance = new ChargingSoundInstance(WeaponSoundsUtil.getWeaponUncharge(projectileWeapon.getProjectileWeaponName()), player);
                    Minecraft.getInstance().getSoundManager().play(offChargingSoundInstance);
                    return;
                }

                if (offCharge >= WeaponTimingUtil.getProjectileWeaponChargeThreshold(projectileWeapon.getProjectileWeaponName())) {
                    if(projectileWeapon.getProjectileWeaponName().equals(WeaponName.Z6_ROTARY)) {
                        offChargeFired = true;
                        offFiring = true;
                        offChargeNShootTimer.cancel();
                        offChargeNShootTimer = new Timer();
                        offCharge = 0;
                        offCharging = false;
                        scheduleOffFullAutoFiring(player, heldItem, projectileWeapon);
                    } else {
                        PayloadRegister.sendToServer(new SSFireProjectileWeaponPacket(false));
                        offChargeFired = true;
                        offFiring = false;
                        offChargeNShootTimer.cancel();
                        offChargeNShootTimer = new Timer();
                        offCharge = 0;
                        offCharging = false;
                    }
                }

                if (!offCharging) {
                    offCharging = true;
                } else {
                    offCharge++;
                }

                scheduleOffChargeNShoot(player, player.getOffhandItem(), projectileWeapon);
            }
        }, 50);
    }

    private static void scheduleMainChargeNShootOnRelease(LocalPlayer player, ItemStack heldItem, ProjectileItem projectileWeapon) {
        mainChargeNShootOnReleaseTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!mainFiring) return;

                if (!ItemStack.isSameItem(player.getMainHandItem(), heldItem) || player.getMainHandItem().equals(ItemStack.EMPTY)) {
                    mainCharge = 0;
                    mainCharging = false;
                    mainFiring = false;
                    mainFullyCharged = false;
                    mainChargeNShootOnReleaseTimer.cancel();
                    mainChargeNShootOnReleaseTimer = new Timer();
                    ChargingSoundInstance.stopAudio = true;
                    Minecraft.getInstance().getSoundManager().stop(mainChargingSoundInstance);
                    ChargingSoundInstance.stopAudio = true;
                    Minecraft.getInstance().getSoundManager().stop(mainFullyChargedSoundInstance);
                    ChargingSoundInstance.stopAudio = false;
                    mainChargingSoundInstance = new ChargingSoundInstance(WeaponSoundsUtil.getWeaponUncharge(projectileWeapon.getProjectileWeaponName()), player);
                    Minecraft.getInstance().getSoundManager().play(mainChargingSoundInstance);
                    return;
                }

                if (mainCharge >= WeaponTimingUtil.getProjectileWeaponChargeThreshold(projectileWeapon.getProjectileWeaponName()) && !mainFullyCharged) {
                    if (!(projectileWeapon.getProjectileWeaponName().equals(WeaponName.MW20_BRYAR_PISTOL) || projectileWeapon.getProjectileWeaponName().equals(WeaponName.RELBY_V10))) {
                        FullyChargedSoundInstance.stopAudio = false;
                        mainFullyChargedSoundInstance = new FullyChargedSoundInstance(WeaponSoundsUtil.getWeaponChargeLoop(projectileWeapon.getProjectileWeaponName()), player);
                        Minecraft.getInstance().getSoundManager().play(mainFullyChargedSoundInstance);
                    }
                    mainFullyCharged = true;
                }

                if (!mainCharging) {
                    mainCharging = true;
                } else if (mainCharge < WeaponTimingUtil.getProjectileWeaponChargeThreshold(projectileWeapon.getProjectileWeaponName())){
                    mainCharge++;
                }

                scheduleMainChargeNShootOnRelease(player, player.getMainHandItem(), projectileWeapon);
            }
        }, 50);
    }

    private static void scheduleOffChargeNShootOnRelease(LocalPlayer player, ItemStack heldItem, ProjectileItem projectileWeapon) {
        offChargeNShootOnReleaseTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!offFiring) return;

                if (!ItemStack.isSameItem(player.getOffhandItem(), heldItem) || player.getOffhandItem().equals(ItemStack.EMPTY)) {
                    offCharge = 0;
                    offCharging = false;
                    offFiring = false;
                    offFullyCharged = false;
                    offChargeNShootOnReleaseTimer.cancel();
                    offChargeNShootOnReleaseTimer = new Timer();
                    ChargingSoundInstance.stopAudio = true;
                    Minecraft.getInstance().getSoundManager().stop(offChargingSoundInstance);
                    ChargingSoundInstance.stopAudio = true;
                    Minecraft.getInstance().getSoundManager().stop(offFullyChargedSoundInstance);
                    ChargingSoundInstance.stopAudio = false;
                    offChargingSoundInstance = new ChargingSoundInstance(WeaponSoundsUtil.getWeaponUncharge(projectileWeapon.getProjectileWeaponName()), player);
                    Minecraft.getInstance().getSoundManager().play(offChargingSoundInstance);
                    return;
                }

                if (offCharge >= WeaponTimingUtil.getProjectileWeaponChargeThreshold(projectileWeapon.getProjectileWeaponName()) && !offFullyCharged) {
                    if (!(projectileWeapon.getProjectileWeaponName().equals(WeaponName.MW20_BRYAR_PISTOL) || projectileWeapon.getProjectileWeaponName().equals(WeaponName.RELBY_V10))) {
                        FullyChargedSoundInstance.stopAudio = false;
                        mainFullyChargedSoundInstance = new FullyChargedSoundInstance(WeaponSoundsUtil.getWeaponChargeLoop(projectileWeapon.getProjectileWeaponName()), player);
                        Minecraft.getInstance().getSoundManager().play(mainFullyChargedSoundInstance);
                    }
                    offFullyCharged = true;
                }

                if (!offCharging) {
                    offCharging = true;
                } else if (offCharge < WeaponTimingUtil.getProjectileWeaponChargeThreshold(projectileWeapon.getProjectileWeaponName())){
                    offCharge++;
                }

                scheduleOffChargeNShootOnRelease(player, player.getOffhandItem(), projectileWeapon);
            }
        }, 50);
    }

    private static HitResult getPlayerHitResult(Player player) {
        // Maximum reach for players
        double reach = 20.0;

        // Start and end points for the ray trace
        Vec3 start = player.getEyePosition(1.0F);
        Vec3 look = player.getViewVector(1.0F);
        Vec3 end = start.add(look.scale(reach));

        // Perform ray trace for entities
        AABB searchBox = new AABB(start, end).inflate(1.0);
        List<Entity> entities = player.getCommandSenderWorld().getEntities(player, searchBox, entity -> !entity.isSpectator() && entity.isPickable());

        EntityHitResult closestEntityHitResult = null;
        double closestDistance = reach;

        for (Entity entity : entities) {
            AABB entityBox = entity.getBoundingBox().inflate(entity.getPickRadius());
            Optional<Vec3> optionalHit = entityBox.clip(start, end);

            if (optionalHit.isPresent()) {
                double distance = start.distanceTo(optionalHit.get());
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestEntityHitResult = new EntityHitResult(entity, optionalHit.get());
                }
            }
        }

        if (closestEntityHitResult != null) {
            return closestEntityHitResult;
        }

        // If no entities are hit, perform a block ray trace
        return player.getCommandSenderWorld().clip(new ClipContext(start, end, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
    }
}