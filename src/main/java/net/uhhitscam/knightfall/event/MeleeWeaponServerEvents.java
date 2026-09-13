package net.uhhitscam.knightfall.event;

import net.uhhitscam.knightfall.item.custom.melee.MeleeAttack;
import net.uhhitscam.knightfall.item.custom.melee.MeleeAttackTrait;
import net.uhhitscam.knightfall.item.custom.melee.MeleeHitContext;
import net.uhhitscam.knightfall.item.custom.melee.MeleeInput;
import net.uhhitscam.knightfall.item.custom.melee.MeleeInputRules;
import net.uhhitscam.knightfall.item.custom.melee.MeleeLightsaber;
import net.uhhitscam.knightfall.item.custom.melee.MeleeProperty;
import net.uhhitscam.knightfall.item.custom.melee.MeleeTuning;
import net.uhhitscam.knightfall.item.custom.melee.MeleeWeaponDefinition;
import net.uhhitscam.knightfall.item.custom.melee.MeleeWeaponForm;
import net.uhhitscam.knightfall.item.custom.melee.MeleeWeaponItem;
import net.uhhitscam.knightfall.item.custom.projectile.ProjectileItem;


import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.component.ModDataComponentTypes;
import net.uhhitscam.knightfall.effect.custom.StunEffect;
import net.uhhitscam.knightfall.entity.custom.MeleeProjectileEntity;
import net.uhhitscam.knightfall.network.CSMeleeRecoveryPacket;
import net.uhhitscam.knightfall.network.PayloadRegister;
import net.uhhitscam.knightfall.network.SSMeleeInputPacket;
import net.uhhitscam.knightfall.util.MeleeTargeting;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static net.uhhitscam.knightfall.item.custom.melee.MeleeAttackTrait.*;
import static net.uhhitscam.knightfall.item.custom.melee.MeleeInput.*;

@EventBusSubscriber(modid = OperationKnightfall.MODID)
public final class MeleeWeaponServerEvents {
    private static final ResourceLocation FLURRY = ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "melee_flurry");
    private static final Map<UUID, State> STATES = new HashMap<>();
    private static final ThreadLocal<Hit> CURRENT_HIT = new ThreadLocal<>();
    private static final String STAGGER_UNTIL = "knightfall:melee_stagger_until";

    private MeleeWeaponServerEvents() {}

    public static boolean isExecutingAttack(Player player) {
        return CURRENT_HIT.get() != null && CURRENT_HIT.get().player == player;
    }

    public static InteractionHand useHand(Player player) {
        for (InteractionHand hand : InteractionHand.values()) {
            ItemStack stack = player.getItemInHand(hand);
            if (stack.getItem() instanceof MeleeWeaponItem weapon && weapon.getForm(stack) != null && weapon.getForm(stack).hasUse()) return hand;
            // Preserve a main-hand blaster, food, or other usable item control of right click
            if (hand == InteractionHand.MAIN_HAND && !(stack.getItem() instanceof MeleeWeaponItem)
                    && (stack.getItem() instanceof ProjectileItem || stack.getUseDuration(player) > 0
                    || stack.getItem() instanceof net.minecraft.world.item.BlockItem)) return null;
        }
        return null;
    }

    private static boolean canAct(ServerPlayer player) {
        return player.isAlive() && !player.isSpectator() && !StunEffect.isStunned(player)
                && player.containerMenu == player.inventoryMenu;
    }

    public static void handleInput(ServerPlayer player, int input) {
        State state = STATES.get(player.getUUID());
        if (input == SSMeleeInputPacket.CANCEL || input == SSMeleeInputPacket.RELEASE) {
            if (state == null || !state.down) return;
            if (input == SSMeleeInputPacket.RELEASE && valid(player, state)) release(player, state);
            stopUse(state);
            return;
        }
        if (!canAct(player) || player.isUsingItem()) return;
        InteractionHand hand = input == SSMeleeInputPacket.ATTACK ? InteractionHand.MAIN_HAND : useHand(player);
        if (hand == null) return;
        ItemStack stack = player.getItemInHand(hand);
        if (!(stack.getItem() instanceof MeleeWeaponItem weapon) || weapon.getForm(stack) == null
                || player.getCooldowns().isOnCooldown(stack.getItem())) return;
        state = STATES.computeIfAbsent(player.getUUID(), id -> new State());
        long now = player.level().getGameTime();
        if (state.down || state.dashTicks > 0 || now < state.readyAt) return;
        if (input == SSMeleeInputPacket.ATTACK) {
            if (now == state.lastLeftTick) return;
            state.lastLeftTick = now;
            MeleeAttack attack = weapon.getForm(stack).attack(LEFT_CLICK);
            if (attack == null) return;
            state.stack = stack;
            state.hand = hand;
            state.form = weapon.getForm(stack);
            state.definition = weapon.getDefinition();
            execute(player, state, attack, true);
            return;
        }
        state.stack = stack;
        state.form = weapon.getForm(stack);
        state.definition = weapon.getDefinition();
        state.hand = hand;
        state.slot = player.getInventory().selected;
        state.dimension = player.level().dimension().location();
        state.down = true;
        state.pressAt = now;
        state.consumed = false;
        state.continuousGesture = false;
        state.attack = null;
        state.input = null;
        state.previous = player.position();
        MeleeAttack tap = state.form.attack(RIGHT_CLICK);
        if (tap != null && tap.trait().whip()) {
            if (state.tether == null || state.tether.isRemoved()) {
                state.tether = MeleeProjectileEntity.launch(player, stack, tap, true, false);
                net.neoforged.neoforge.common.NeoForge.EVENT_BUS.post(new MeleeActionEvent(player, hand, stack, tap, false));
                state.consumed = true;
                state.readyAt = now + tap.cooldownTicks();
            }
            return;
        }
        // A tap-only weapon responds immediately and a mixed gesture waits for release/threshold
        if (tap != null && !state.form.hasHold()) {
            state.consumed = true;
            execute(player, state, tap, false);
        } else if (tap == null && state.form.attack(HOLD_RIGHT_CLICK) != null
                && (state.form.attack(HOLD_RIGHT_CLICK).trait() == BLOCK || state.form.attack(HOLD_RIGHT_CLICK).trait() == PARRY)) {
            activateHold(player, state, HOLD_RIGHT_CLICK);
        }
    }

    private static boolean valid(ServerPlayer player, State state) {
        return canAct(player) && !player.isUsingItem() && state.stack == player.getItemInHand(state.hand)
                && !state.stack.isEmpty() && state.slot == player.getInventory().selected
                && state.dimension.equals(player.level().dimension().location())
                && state.stack.getItem() instanceof MeleeWeaponItem weapon && weapon.getForm(state.stack) == state.form
                && !player.getCooldowns().isOnCooldown(state.stack.getItem());
    }

    private static void release(ServerPlayer player, State state) {
        long elapsed = player.level().getGameTime() - state.pressAt;
        MeleeWeaponDefinition definition = definition(state);
        MeleeAttack tap = state.form.attack(RIGHT_CLICK);
        if (tap != null && tap.trait().whip()) {
            if (MeleeInputRules.isTap(elapsed, definition.tuning().holdThresholdTicks(), state.consumed)) detach(state);
        } else if (state.attack != null && state.attack.trait() == THROW
                && elapsed >= Math.max(definition.tuning().holdThresholdTicks(), state.attack.chargeTicks())) {
            execute(player, state, state.attack, false);
        } else if (tap != null && MeleeInputRules.isTap(elapsed, definition.tuning().holdThresholdTicks(), state.consumed)) {
            execute(player, state, tap, false);
        }
    }

    @SubscribeEvent
    public static void tick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        State state = STATES.get(player.getUUID());
        if (state == null) return;
        long now = player.level().getGameTime();
        if (state.parryUntil != 0 && now >= state.parryUntil) { state.parryUntil = 0; setAction(state, null); }
        if (state.flurryStack != player.getMainHandItem()) clearFlurry(player, state);
        if (!canAct(player)) { stopUse(state); detach(state); state.dashTicks = 0; clearFlurry(player, state); return; }
        if (state.tether != null && (state.tether.isRemoved() || state.stack != player.getItemInHand(state.hand))) detach(state);
        if (state.dashTicks > 0) tickDash(player, state);
        if (!state.down) return;
        if (!valid(player, state)) { stopUse(state); detach(state); return; }
        MeleeWeaponDefinition definition = definition(state);
        long elapsed = now - state.pressAt;
        MeleeAttack tap = state.form.attack(RIGHT_CLICK);
        if (tap != null && tap.trait().whip()) {
            if (elapsed >= definition.tuning().holdThresholdTicks() && state.tether != null) {
                state.consumed = true;
                LivingEntity target = state.tether.attachedTarget();
                if (target != null && now >= state.nextPulse) {
                    state.nextPulse = now + tap.intervalTicks();
                    if (tap.trait() == WHIP_PULL) push(target, player.position().subtract(target.position()).normalize().scale(tap.knockback()));
                    else definition.electricEffect().apply(context(player, target, state.stack));
                }
            }
            return;
        }
        if (state.input == FALL_HOLD_RIGHT_CLICK) {
            if (player.onGround()) {
                execute(player, state, state.attack, false);
                state.attack = null;
                state.input = null;
            }
            return;
        }
        boolean running = running(player, state.previous);
        state.previous = player.position();
        if (elapsed >= definition.tuning().holdThresholdTicks()) {
            MeleeInput chosen = MeleeInputRules.hold(state.form, falling(player), running);
            if (state.attack == null && (!state.consumed || state.continuousGesture) && chosen != null) activateHold(player, state, chosen);
            else if (state.attack != null && state.attack.trait().continuous() && chosen != state.input) {
                setAction(state, null);
                state.attack = null;
                state.input = null;
                state.levitated = null;
                if (chosen != null) activateHold(player, state, chosen);
            }
        }
        if (state.attack == null || state.input == FALL_HOLD_RIGHT_CLICK) return;
        MeleeAttack attack = state.attack;
        if (state.input == RUN_HOLD_RIGHT_CLICK && !running) return;
        if (attack.trait().continuous()) {
            if (attack.trait() == BLOCK) return;
            if (attack.trait() == LEVITATE) { levitate(player, state); return; }
            if (now >= state.nextPulse) {
                state.nextPulse = now + attack.intervalTicks();
                boolean success = false;
                var targets = attack.trait() == CHARGE ? MeleeTargeting.path(player, state.contactPrevious)
                        : attack.trait() == STEADY ? MeleeTargeting.contact(player, attack.radius())
                        : MeleeTargeting.area(player, attack.radius(), true);
                for (LivingEntity target : targets) {
                    success |= hit(player, state.stack, state.hand, target, attack, false);
                }
                finishRecovery(player, state, success);
            }
            if (now + attack.intervalTicks() == state.nextPulse) state.contactPrevious = player.position();
        } else if (attack.trait() != THROW && attack.trait() != PARRY
                && elapsed >= Math.max(definition.tuning().holdThresholdTicks(), attack.chargeTicks())) {
            execute(player, state, attack, false);
            state.attack = null;
        }
    }

    private static boolean running(ServerPlayer player, Vec3 previous) {
        return player.isSprinting() && player.onGround() && player.position().subtract(previous).horizontalDistanceSqr() > 0.0025;
    }

    private static boolean falling(ServerPlayer player) {
        return !player.onGround() && !player.getAbilities().flying && !player.isFallFlying()
                && !player.isInWaterOrBubble() && !player.onClimbable() && player.getDeltaMovement().y < -0.08;
    }

    private static void activateHold(ServerPlayer player, State state, MeleeInput input) {
        state.input = input;
        state.attack = state.form.attack(input);
        state.consumed = true;
        state.continuousGesture = state.attack.trait().continuous();
        state.nextPulse = Math.max(state.nextPulse, player.level().getGameTime());
        state.contactPrevious = player.position();
        setAction(state, state.attack.trait());
        net.neoforged.neoforge.common.NeoForge.EVENT_BUS.post(new MeleeActionEvent(player, state.hand, state.stack, state.attack, true));
        if (state.attack.trait() == PARRY) armParry(player, state, state.attack);
    }

    private static void execute(ServerPlayer player, State state, MeleeAttack attack, boolean left) {
        long now = player.level().getGameTime();
        if (!left && now < state.readyAt) return;
        net.neoforged.neoforge.common.NeoForge.EVENT_BUS.post(new MeleeActionEvent(player, state.hand, state.stack, attack, false));
        if (!left || attack.trait() == BLASTER_SHOT) state.readyAt = now + attack.cooldownTicks();
        boolean success = false;
        switch (attack.trait()) {
            case SIMPLE_HIT, THRUST, UPPERCUT, BREAK_BLOCK, KNOCKBACK -> {
                LivingEntity target = MeleeTargeting.ray(player, player.entityInteractionRange() + (attack.trait() == THRUST ? 1 : 0));
                if (target != null) success = hit(player, state.stack, state.hand, target, attack, state.hand == InteractionHand.MAIN_HAND);
            }
            case SLASH, SWEEP, SLAM_GROUND -> {
                if (attack.trait() == SLAM_GROUND && !player.onGround()) break;
                for (LivingEntity target : MeleeTargeting.area(player, attack.radius(), attack.trait() == SLASH)) {
                    success |= hit(player, state.stack, state.hand, target, attack, false);
                }
                player.serverLevel().sendParticles(ParticleTypes.SWEEP_ATTACK, player.getX(), player.getY() + 0.4,
                        player.getZ(), 5, attack.radius() / 2, 0, attack.radius() / 2, 0);
            }
            case BLASTER_SHOT -> definition(state).blasterShot().fire(player, state.hand, state.stack);
            case THROW, QUICK_THROW -> {
                boolean recoverable = attack.trait() == THROW && !player.hasInfiniteMaterials();
                MeleeProjectileEntity projectile = MeleeProjectileEntity.launch(player, state.stack, attack, false, recoverable);
                if (projectile != null && recoverable) state.stack.shrink(1);
            }
            case DASH -> {
                state.dashAttack = attack;
                state.dashTicks = attack.durationTicks();
                state.dashStack = state.stack;
                state.dashPrevious = player.position();
                state.dashHits.clear();
                state.dashSuccess = false;
                push(player, player.getLookAngle().multiply(1, 0, 1).normalize().scale(attack.speed()));
            }
            case PARRY -> armParry(player, state, attack);
            case SWITCH -> {
                ((MeleeWeaponItem) state.stack.getItem()).switchForm(state.stack);
                clearFlurry(player, state);
            }
            default -> { }
        }
        player.swing(state.hand, true);
        if (attack.trait() != PARRY && attack.trait() != SWITCH && attack.trait() != DASH) finishRecovery(player, state, success);
    }

    private static void tickDash(ServerPlayer player, State state) {
        if (state.dashStack != player.getItemInHand(state.hand)) { state.dashTicks = 0; return; }
        for (LivingEntity target : MeleeTargeting.path(player, state.dashPrevious)) {
            if (state.dashHits.add(target.getUUID())) {
                state.dashSuccess |= hit(player, state.dashStack, state.hand, target, state.dashAttack, false);
            }
        }
        state.dashPrevious = player.position();
        if (player.horizontalCollision) state.dashTicks = 1;
        if (--state.dashTicks == 0) { finishRecovery(player, state, state.dashSuccess); state.dashSuccess = false; }
    }

    private static void levitate(ServerPlayer player, State state) {
        if (state.levitated == null) state.levitated = MeleeTargeting.ray(player, definition(state).tuning().tetherRange());
        LivingEntity target = state.levitated;
        if (target == null) return;
        if (!MeleeTargeting.canHit(player, target) || !player.hasLineOfSight(target)
                || target.distanceTo(player) > definition(state).tuning().tetherRange()) { state.levitated = null; return; }
        Vec3 desired = MeleeTargeting.clipped(player, player.getEyePosition(), player.getEyePosition().add(player.getLookAngle()
                .scale(Math.min(state.attack.radius(), definition(state).tuning().tetherRange()))));
        Vec3 delta = desired.subtract(target.getBoundingBox().getCenter()).scale(0.3);
        if (delta.length() > 0.6) delta = delta.normalize().scale(0.6);
        // Movement uses normal entity collision; never teleport the target through a wall.
        target.setDeltaMovement(delta);
        syncMotion(target);
    }

    private static void armParry(ServerPlayer player, State state, MeleeAttack attack) {
        state.parryUntil = player.level().getGameTime() + definition(state).tuning().parryWindowTicks();
        state.parryAttack = attack;
        state.parryStack = state.stack;
        state.readyAt = player.level().getGameTime() + attack.cooldownTicks();
        setAction(state, PARRY);
    }

    private static boolean hit(ServerPlayer player, ItemStack stack, InteractionHand hand, LivingEntity target,
                               MeleeAttack attack, boolean vanilla) {
        if (stack.isEmpty() || !MeleeTargeting.canHit(player, target)) return false;
        if (attack.trait() == BREAK_BLOCK && faces(target, player.damageSources().playerAttack(player))) {
            disableBlock(target, definition(stack).tuning().blockDisableTicks());
        }
        Hit previous = CURRENT_HIT.get();
        Hit current = new Hit(player, target, stack, attack);
        CURRENT_HIT.set(current);
        try {
            if (attack.damageMultiplier() == 0) {
                // A shield shove can move a target without inventing damage or on-hit effects.
                var source = player.damageSources().playerAttack(player);
                State defense = STATES.get(target.getUUID());
                boolean blocked = target.isDamageSourceBlocked(source) || defense != null && defense.down
                        && defense.attack != null && defense.attack.trait() == BLOCK && faces(target, source);
                current.success = !target.isInvulnerableTo(source) && !blocked;
            } else if (vanilla) player.attack(target);
            else {
                DamageSource source = player.damageSources().playerAttack(player);
                float strength = player.getAttackStrengthScale(0.5F);
                float base = (float) (hand == InteractionHand.MAIN_HAND ? player.getAttributeValue(Attributes.ATTACK_DAMAGE)
                        : ((MeleeWeaponItem) stack.getItem()).getForm(stack).damage());
                float damage = base * (0.2F + strength * strength * 0.8F);
                damage = EnchantmentHelper.modifyDamage(player.serverLevel(), stack, target, source, damage);
                target.hurt(source, damage);
                if (current.success) EnchantmentHelper.doPostAttackEffectsWithItemSource(player.serverLevel(), target, source, stack);
            }
        } finally { if (previous == null) CURRENT_HIT.remove(); else CURRENT_HIT.set(previous); }
        if (!current.success) return false;
        if (attack.damageMultiplier() > 0) applyProperties(player, target, stack);
        if (attack.trait() == UPPERCUT || attack.trait() == SLAM_GROUND) {
            push(target, target.position().subtract(player.position()).multiply(1, 0, 1).normalize()
                    .scale(attack.knockback()).add(0, attack.lift(), 0));
        } else if (attack.trait() == KNOCKBACK || attack.trait() == CHARGE) {
            push(target, player.getLookAngle().multiply(1, 0, 1).normalize().scale(attack.knockback()).add(0, 0.1, 0));
        }
        stack.hurtAndBreak(1, player, hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
        return true;
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void damage(LivingIncomingDamageEvent event) {
        Hit hit = CURRENT_HIT.get();
        if (hit != null && hit.target == event.getEntity() && event.getSource().getEntity() == hit.player) {
            event.setAmount(event.getAmount() * hit.attack.damageMultiplier());
        }
    }

    @SubscribeEvent
    public static void damaged(LivingDamageEvent.Post event) {
        Hit hit = CURRENT_HIT.get();
        if (hit != null && hit.target == event.getEntity() && event.getSource().getEntity() == hit.player
                && (event.getNewDamage() > 0 || event.getReduction(
                net.neoforged.neoforge.common.damagesource.DamageContainer.Reduction.ABSORPTION) > 0)) hit.success = true;
    }

    public static void applyProperties(ServerPlayer player, LivingEntity target, ItemStack stack) {
        if (!(stack.getItem() instanceof MeleeWeaponItem weapon) || weapon.getForm(stack) == null) return;
        MeleeWeaponForm form = weapon.getForm(stack);
        MeleeWeaponDefinition definition = weapon.getDefinition();
        if (form.has(MeleeProperty.FIRE_DAMAGE)) target.igniteForSeconds(definition.tuning().fireSeconds());
        if (form.has(MeleeProperty.ELECTRIC_SHOCK)) definition.electricEffect().apply(context(player, target, stack));
        if (form.has(MeleeProperty.STAGGER)) {
            if (target instanceof Player victim) {
                victim.resetAttackStrengthTicker();
                if (victim instanceof ServerPlayer server) PayloadRegister.sendToPlayer(server, new CSMeleeRecoveryPacket());
            } else target.getPersistentData().putLong(STAGGER_UNTIL, target.level().getGameTime() + 20);
        }
        if (definition.hitEffect().apply(context(player, target, stack)) && definition.consumesOnSuccessfulHit()) stack.consume(1, player);
    }

    public static boolean isStaggered(LivingEntity entity) {
        return entity.getPersistentData().getLong(STAGGER_UNTIL) > entity.level().getGameTime();
    }

    private static void finishRecovery(ServerPlayer player, State state, boolean success) {
        player.resetAttackStrengthTicker();
        PayloadRegister.sendToPlayer(player, new CSMeleeRecoveryPacket());
        updateFlurry(player, state, success);
    }

    public static void projectileHit(ServerPlayer player, ItemStack source) {
        State state = STATES.get(player.getUUID());
        if (state != null && state.stack == player.getMainHandItem() && ItemStack.isSameItem(state.stack, source)
                && state.stack.getItem() instanceof MeleeWeaponItem weapon
                && weapon.getForm(state.stack) == state.form && weapon.getForm(source) == state.form) {
            updateFlurry(player, state, true);
        }
    }

    private static void updateFlurry(ServerPlayer player, State state, boolean success) {
        if (state.hand != InteractionHand.MAIN_HAND || !state.form.has(MeleeProperty.FLURRY)) return;
        var attribute = player.getAttribute(Attributes.ATTACK_SPEED);
        if (attribute == null) return;
        MeleeTuning tuning = definition(state).tuning();
        attribute.removeModifier(FLURRY);
        attribute.addTransientModifier(new AttributeModifier(FLURRY, (success ? tuning.flurryHitSpeed() : tuning.flurryMissSpeed()) - 1,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        state.flurryStack = state.stack;
    }

    private static void clearFlurry(ServerPlayer player, State state) {
        var attribute = player.getAttribute(Attributes.ATTACK_SPEED);
        if (attribute != null) attribute.removeModifier(FLURRY);
        state.flurryStack = ItemStack.EMPTY;
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void block(LivingShieldBlockEvent event) {
        if (event.getEntity().level().isClientSide()) return;
        LivingEntity defender = event.getEntity();
        DamageSource source = event.getDamageSource();
        State state = STATES.get(defender.getUUID());
        boolean customBlock = defender instanceof ServerPlayer player && state != null && state.down && valid(player, state)
                && state.attack != null && state.attack.trait() == BLOCK;
        boolean parry = state != null && state.parryUntil > defender.level().getGameTime()
                && defender.getItemInHand(state.hand) == state.parryStack && defender instanceof ServerPlayer player && canAct(player)
                && !player.getCooldowns().isOnCooldown(state.parryStack.getItem());
        if ((customBlock || parry) && faces(defender, source)) {
            event.setBlocked(true);
            event.setShieldDamage(0);
            if (source.getDirectEntity() instanceof LivingEntity attacker && parry && defender instanceof ServerPlayer player
                    && MeleeTargeting.canHit(player, attacker) && player.hasLineOfSight(attacker)
                    && player.distanceTo(attacker) <= player.entityInteractionRange()) {
                state.parryUntil = 0; // Consume before counterattacking; two parries cannot recurse.
                hit(player, state.parryStack, state.hand, attacker, state.parryAttack, false);
            }
            if (customBlock) {
                state.stack.hurtAndBreak(Math.max(1, (int) event.getOriginalBlockedDamage()), defender,
                        state.hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
                if (source.getDirectEntity() instanceof LivingEntity attacker && attacker.canDisableShield()) {
                    disableBlock(defender, definition(state).tuning().blockDisableTicks());
                }
            }
        }
        ItemStack defense = customBlock || parry ? state.stack : defender.getUseItem();
        if (source.getDirectEntity() instanceof LivingEntity attacker && event.getBlocked()) {
            ItemStack offense = CURRENT_HIT.get() != null && CURRENT_HIT.get().player == attacker
                    ? CURRENT_HIT.get().stack : attacker.getMainHandItem();
            boolean incomingSaber = activeSaber(offense, attacker);
            if (incomingSaber && defense.getItem() instanceof MeleeWeaponItem weapon && weapon.getForm(defense) != null) {
                var form = weapon.getForm(defense);
                if (!form.has(MeleeProperty.LIGHTSABER_RESISTANT) && !form.has(MeleeProperty.DISABLE_LIGHTSABERS)) event.setBlocked(false);
                cortosis(defense, offense, attacker);
            }
            if (activeSaber(defense, defender)) cortosis(offense, defense, defender);
        }
    }

    private static boolean activeSaber(ItemStack stack, LivingEntity holder) {
        return stack.getItem() instanceof MeleeLightsaber saber && saber.isBladeActive(stack, holder);
    }

    private static void cortosis(ItemStack material, ItemStack saberStack, LivingEntity saberHolder) {
        if (material.getItem() instanceof MeleeWeaponItem weapon && weapon.getForm(material) != null
                && weapon.getForm(material).has(MeleeProperty.DISABLE_LIGHTSABERS)
                && saberStack.getItem() instanceof MeleeLightsaber saber
                && saberHolder.getRandom().nextFloat() < weapon.getDefinition().tuning().cortosisChance()) {
            saber.disableBlade(saberStack, saberHolder, weapon.getDefinition().tuning().lightsaberDisableTicks());
        }
    }

    private static boolean faces(LivingEntity defender, DamageSource source) {
        if (source.is(DamageTypeTags.BYPASSES_SHIELD) || source.getSourcePosition() == null
                || source.getDirectEntity() instanceof AbstractArrow arrow && arrow.getPierceLevel() > 0) return false;
        return source.getSourcePosition().subtract(defender.position()).multiply(1, 0, 1).normalize()
                .dot(defender.getLookAngle().multiply(1, 0, 1).normalize()) > 0;
    }

    public static void disableBlock(LivingEntity target, int ticks) {
        State state = STATES.get(target.getUUID());
        ItemStack blocking = target.getUseItem();
        if (state != null && state.down && state.attack != null && state.attack.trait() == BLOCK) blocking = state.stack;
        if (blocking.isEmpty()) return;
        if (target instanceof Player player) player.getCooldowns().addCooldown(blocking.getItem(), ticks);
        target.stopUsingItem();
        if (state != null) { state.parryUntil = 0; stopUse(state); }
        target.level().broadcastEntityEvent(target, (byte) 30);
    }

    public static void push(LivingEntity target, Vec3 motion) {
        double resistance = 1 - target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
        target.push(motion.x * resistance, motion.y * resistance, motion.z * resistance);
        syncMotion(target);
    }

    private static void syncMotion(LivingEntity target) {
        target.hurtMarked = true;
        if (target instanceof ServerPlayer server) server.connection.send(new ClientboundSetEntityMotionPacket(target));
    }

    private static MeleeWeaponDefinition definition(State state) { return state.definition; }
    private static MeleeWeaponDefinition definition(ItemStack stack) { return ((MeleeWeaponItem) stack.getItem()).getDefinition(); }
    private static MeleeHitContext context(ServerPlayer player, LivingEntity target, ItemStack stack) {
        return new MeleeHitContext(player.serverLevel(), player, target, stack, definition(stack));
    }

    private static void setAction(State state, MeleeAttackTrait trait) {
        if (!state.stack.isEmpty()) state.stack.set(ModDataComponentTypes.MELEE_ACTION.get(), trait == null ? 0 : trait.ordinal() + 1);
    }
    private static void stopUse(State state) {
        setAction(state, null);
        state.down = false;
        state.attack = null;
        state.input = null;
        state.levitated = null;
    }
    private static void detach(State state) { if (state.tether != null) state.tether.discard(); state.tether = null; }
    private static void clear(Player player) {
        State state = STATES.remove(player.getUUID());
        if (state == null) return;
        stopUse(state);
        detach(state);
        if (player instanceof ServerPlayer server) clearFlurry(server, state);
    }
    @SubscribeEvent public static void logout(PlayerEvent.PlayerLoggedOutEvent event) { clear(event.getEntity()); }
    @SubscribeEvent public static void dimension(PlayerEvent.PlayerChangedDimensionEvent event) { clear(event.getEntity()); }
    @SubscribeEvent public static void respawn(PlayerEvent.PlayerRespawnEvent event) { clear(event.getEntity()); }
    @SubscribeEvent public static void stopped(ServerStoppedEvent event) { STATES.clear(); CURRENT_HIT.remove(); }

    private static final class Hit {
        final ServerPlayer player;
        final LivingEntity target;
        final ItemStack stack;
        final MeleeAttack attack;
        boolean success;
        Hit(ServerPlayer player, LivingEntity target, ItemStack stack, MeleeAttack attack) {
            this.player = player; this.target = target; this.stack = stack; this.attack = attack;
        }
    }

    private static final class State {
        ItemStack stack = ItemStack.EMPTY, flurryStack = ItemStack.EMPTY, dashStack = ItemStack.EMPTY, parryStack = ItemStack.EMPTY;
        MeleeWeaponForm form;
        MeleeWeaponDefinition definition;
        InteractionHand hand = InteractionHand.MAIN_HAND;
        MeleeInput input;
        MeleeAttack attack, dashAttack, parryAttack;
        ResourceLocation dimension;
        int slot, dashTicks;
        boolean down, consumed, dashSuccess, continuousGesture;
        long pressAt, readyAt, nextPulse, parryUntil, lastLeftTick = Long.MIN_VALUE;
        Vec3 previous = Vec3.ZERO, contactPrevious = Vec3.ZERO, dashPrevious = Vec3.ZERO;
        LivingEntity levitated;
        MeleeProjectileEntity tether;
        final Set<UUID> dashHits = new HashSet<>();
    }
}
