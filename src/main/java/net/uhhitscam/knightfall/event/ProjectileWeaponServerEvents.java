package net.uhhitscam.knightfall.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.effect.custom.StunEffect;
import net.uhhitscam.knightfall.item.custom.FiringMode;
import net.uhhitscam.knightfall.item.custom.ProjectileItem;
import net.uhhitscam.knightfall.item.custom.WeaponAction;
import net.uhhitscam.knightfall.item.custom.WeaponCooldownAction;
import net.uhhitscam.knightfall.item.custom.WeaponName;
import net.uhhitscam.knightfall.util.BeamLogic;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@EventBusSubscriber(modid = OperationKnightfall.MODID)
public final class ProjectileWeaponServerEvents {
    private static final int BURST_SHOT_COUNT = 3;
    private static final int BURST_FOLLOWUP_DELAY_TICKS = 2;
    private static final ResourceLocation HELD_WEAPON_SPEED_MODIFIER_ID =
            ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "held_projectile_weapon_speed");
    private static final Map<UUID, PlayerWeaponState> PLAYER_STATES = new HashMap<>();

    private ProjectileWeaponServerEvents() {}

    public static void handleInput(ServerPlayer player, boolean mainHand, boolean active) {
        PlayerWeaponState playerState = PLAYER_STATES.computeIfAbsent(player.getUUID(), ignored -> new PlayerWeaponState());
        detectHeldWeaponChange(player, playerState, mainHand);
        HandState handState = playerState.hand(mainHand);

        if (!active) {
            stopInput(player, handState, mainHand, true);
            return;
        }

        if (handState.inputDown || !canAct(player)) {
            return;
        }

        ItemStack stack = getHeldStack(player, mainHand);
        if (!(stack.getItem() instanceof ProjectileItem weapon)
                || weapon.isActionOnCooldown(player, stack)) {
            return;
        }

        FiringMode firingMode = weapon.getFiringMode(stack);
        handState.start(stack, mainHand ? player.getInventory().selected : -1, firingMode, player.level().getGameTime());

        switch (firingMode) {
            case FULL_AUTO -> weapon.fireServer(player, stack, mainHand, false);
            case BURST -> startBurst(player, handState, weapon, mainHand);
            case BEAM -> startBeam(player, handState, weapon, mainHand);
            case CHARGENSHOOT, CHARGENSHOOTONRELEASE -> {
            }
            default -> weapon.fireServer(player, stack, mainHand, false);
        }
    }

    public static void handleAction(ServerPlayer player, boolean mainHand, WeaponAction action) {
        if (!canAct(player)) {
            return;
        }

        PlayerWeaponState playerState = PLAYER_STATES.computeIfAbsent(player.getUUID(), ignored -> new PlayerWeaponState());
        detectHeldWeaponChange(player, playerState, mainHand);
        stopInput(player, playerState.hand(mainHand), mainHand, false);

        ItemStack stack = getHeldStack(player, mainHand);
        if (!(stack.getItem() instanceof ProjectileItem weapon)) {
            return;
        }

        switch (action) {
            case RELOAD -> weapon.reload(player, stack);
            case UNLOAD -> weapon.unload(player, stack, mainHand);
            case SWITCH_MODE -> weapon.switchFiringMode(player, stack);
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        PlayerWeaponState state = PLAYER_STATES.computeIfAbsent(player.getUUID(), ignored -> new PlayerWeaponState());
        detectHeldWeaponChange(player, state, true);
        detectHeldWeaponChange(player, state, false);
        tickHand(player, state.mainHand, true);
        tickHand(player, state.offHand, false);
        updateHeldMovementSpeed(player);
    }

    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            clearPlayer(player);
        }
    }

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            clearPlayer(player);
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            clearPlayer(player);
        }
    }

    private static void startBurst(ServerPlayer player, HandState state, ProjectileItem weapon, boolean mainHand) {
        if (weapon.fireServer(player, state.stack, mainHand, false)) {
            state.burstShotsFired = 1;
            state.nextBurstTick = player.level().getGameTime() + weapon.getBurstRate();
        }
    }

    private static void startBeam(ServerPlayer player, HandState state, ProjectileItem weapon, boolean mainHand) {
        if (weapon.getAmmo(state.stack) <= 0) {
            weapon.fireServer(player, state.stack, mainHand, false);
            return;
        }

        if (weapon.isOverheated(state.stack, player.level())) {
            return;
        }

        BeamLogic.startOrUpdateBeam(player, weapon, state.stack, mainHand);
        state.beamActive = true;
    }

    private static void tickHand(ServerPlayer player, HandState state, boolean mainHand) {
        if (!state.inputDown) {
            return;
        }

        ProjectileItem weapon = validateActiveInput(player, state, mainHand);
        if (weapon == null) {
            stopInput(player, state, mainHand, false);
            return;
        }

        long gameTime = player.level().getGameTime();
        switch (state.firingMode) {
            case FULL_AUTO -> weapon.fireServer(player, state.stack, mainHand, false);
            case BURST -> tickBurst(player, state, weapon, mainHand, gameTime);
            case CHARGENSHOOT -> tickChargeAndShoot(player, state, weapon, mainHand, gameTime);
            case BEAM -> tickBeam(player, state, weapon, mainHand);
            default -> {
            }
        }
    }

    private static void tickBurst(ServerPlayer player, HandState state, ProjectileItem weapon, boolean mainHand, long gameTime) {
        if (state.burstShotsFired <= 0 || state.burstShotsFired >= BURST_SHOT_COUNT || gameTime < state.nextBurstTick) {
            return;
        }

        if (!weapon.fireServer(player, state.stack, mainHand, true)) {
            state.burstShotsFired = BURST_SHOT_COUNT;
            return;
        }

        state.burstShotsFired++;
        state.nextBurstTick = gameTime + BURST_FOLLOWUP_DELAY_TICKS;
    }

    private static void tickChargeAndShoot(ServerPlayer player, HandState state, ProjectileItem weapon, boolean mainHand, long gameTime) {
        if (gameTime - state.pressTime < weapon.getChargeThreshold()) {
            return;
        }

        if (weapon.getProjectileWeaponName() == WeaponName.Z6_ROTARY) {
            state.chargeTriggered = true;
            weapon.fireServer(player, state.stack, mainHand, false);
            return;
        }

        if (state.chargeTriggered) {
            return;
        }

        state.chargeTriggered = true;
        weapon.fireServer(player, state.stack, mainHand, false);
    }

    private static void tickBeam(ServerPlayer player, HandState state, ProjectileItem weapon, boolean mainHand) {
        if (weapon.getAmmo(state.stack) <= 0) {
            stopInput(player, state, mainHand, false);
            return;
        }

        if (weapon.isOverheated(state.stack, player.level())) {
            if (state.beamActive) {
                BeamLogic.stopBeam(player, mainHand);
                state.beamActive = false;
            }
            return;
        }

        BeamLogic.startOrUpdateBeam(player, weapon, state.stack, mainHand);
        state.beamActive = true;
    }

    private static ProjectileItem validateActiveInput(ServerPlayer player, HandState state, boolean mainHand) {
        if (!canAct(player)) {
            return null;
        }

        ItemStack currentStack = getHeldStack(player, mainHand);
        if (currentStack != state.stack
                || mainHand && player.getInventory().selected != state.selectedSlot
                || !(currentStack.getItem() instanceof ProjectileItem weapon)
                || weapon.getFiringMode(currentStack) != state.firingMode
                || weapon.isActionOnCooldown(player, currentStack)
                || state.firingMode != FiringMode.REPULSE && weapon.getAmmo(currentStack) <= 0) {
            return null;
        }

        return weapon;
    }

    private static void stopInput(ServerPlayer player, HandState state, boolean mainHand, boolean fireOnRelease) {
        if (!state.inputDown) {
            return;
        }

        if (fireOnRelease && state.firingMode == FiringMode.CHARGENSHOOTONRELEASE) {
            ProjectileItem weapon = validateActiveInput(player, state, mainHand);
            if (weapon != null && player.level().getGameTime() - state.pressTime >= weapon.getChargeThreshold()) {
                weapon.fireServer(player, state.stack, mainHand, false);
            }
        }

        if (state.beamActive) {
            BeamLogic.stopBeam(player, mainHand);
        }

        state.reset();
    }

    private static void detectHeldWeaponChange(ServerPlayer player, PlayerWeaponState state, boolean mainHand) {
        ItemStack currentStack = getHeldStack(player, mainHand);
        int currentSlot = mainHand ? player.getInventory().selected : -1;
        HeldWeaponSnapshot snapshot = mainHand ? state.mainSnapshot : state.offSnapshot;

        if (!snapshot.initialized) {
            snapshot.update(currentStack, currentSlot);
            return;
        }

        if (snapshot.stack == currentStack && snapshot.selectedSlot == currentSlot) {
            return;
        }

        stopInput(player, state.hand(mainHand), mainHand, false);
        if (currentStack.getItem() instanceof ProjectileItem weapon) {
            weapon.startCooldown(player, currentStack, WeaponCooldownAction.EQUIP);
        }
        snapshot.update(currentStack, currentSlot);
    }

    private static boolean canAct(ServerPlayer player) {
        return player.isAlive() && !player.isSpectator() && !StunEffect.isStunned(player);
    }

    private static void updateHeldMovementSpeed(ServerPlayer player) {
        double speedMultiplier = Math.min(
                getHeldMovementSpeedMultiplier(player.getMainHandItem()),
                getHeldMovementSpeedMultiplier(player.getOffhandItem())
        );
        double modifierAmount = speedMultiplier - 1.0;
        AttributeInstance movementSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (movementSpeed == null) {
            return;
        }

        AttributeModifier currentModifier = movementSpeed.getModifier(HELD_WEAPON_SPEED_MODIFIER_ID);
        if (modifierAmount == 0.0) {
            if (currentModifier != null) {
                movementSpeed.removeModifier(HELD_WEAPON_SPEED_MODIFIER_ID);
            }
            return;
        }

        if (currentModifier != null && Double.compare(currentModifier.amount(), modifierAmount) == 0) {
            return;
        }

        movementSpeed.removeModifier(HELD_WEAPON_SPEED_MODIFIER_ID);
        movementSpeed.addTransientModifier(new AttributeModifier(
                HELD_WEAPON_SPEED_MODIFIER_ID,
                modifierAmount,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        ));
    }

    private static double getHeldMovementSpeedMultiplier(ItemStack stack) {
        return stack.getItem() instanceof ProjectileItem weapon
                ? weapon.getHeldMovementSpeedMultiplier()
                : 1.0;
    }

    private static ItemStack getHeldStack(ServerPlayer player, boolean mainHand) {
        return mainHand ? player.getMainHandItem() : player.getOffhandItem();
    }

    private static void clearPlayer(ServerPlayer player) {
        PlayerWeaponState state = PLAYER_STATES.remove(player.getUUID());
        if (state == null) {
            return;
        }

        stopInput(player, state.mainHand, true, false);
        stopInput(player, state.offHand, false, false);
        BeamLogic.stopBeam(player, true);
        BeamLogic.stopBeam(player, false);
    }

    private static final class PlayerWeaponState {
        private final HandState mainHand = new HandState();
        private final HandState offHand = new HandState();
        private final HeldWeaponSnapshot mainSnapshot = new HeldWeaponSnapshot();
        private final HeldWeaponSnapshot offSnapshot = new HeldWeaponSnapshot();

        private HandState hand(boolean main) {
            return main ? mainHand : offHand;
        }
    }

    private static final class HandState {
        private ItemStack stack = ItemStack.EMPTY;
        private int selectedSlot = -1;
        private FiringMode firingMode;
        private long pressTime;
        private long nextBurstTick;
        private int burstShotsFired;
        private boolean inputDown;
        private boolean chargeTriggered;
        private boolean beamActive;

        private void start(ItemStack stack, int selectedSlot, FiringMode firingMode, long pressTime) {
            this.stack = stack;
            this.selectedSlot = selectedSlot;
            this.firingMode = firingMode;
            this.pressTime = pressTime;
            this.inputDown = true;
        }

        private void reset() {
            stack = ItemStack.EMPTY;
            selectedSlot = -1;
            firingMode = null;
            pressTime = 0;
            nextBurstTick = 0;
            burstShotsFired = 0;
            inputDown = false;
            chargeTriggered = false;
            beamActive = false;
        }
    }

    private static final class HeldWeaponSnapshot {
        private ItemStack stack = ItemStack.EMPTY;
        private int selectedSlot = -1;
        private boolean initialized;

        private void update(ItemStack stack, int selectedSlot) {
            this.stack = stack;
            this.selectedSlot = selectedSlot;
            initialized = true;
        }
    }
}
