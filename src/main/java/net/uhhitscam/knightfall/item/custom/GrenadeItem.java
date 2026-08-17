package net.uhhitscam.knightfall.item.custom;

import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.uhhitscam.knightfall.entity.ModEntities;
import net.uhhitscam.knightfall.entity.custom.GrenadeEntity;
import net.uhhitscam.knightfall.component.GrenadeRemoteLink;
import net.uhhitscam.knightfall.component.ModDataComponentTypes;

public class GrenadeItem extends Item implements net.minecraft.world.item.ProjectileItem {
    private static final int IMPACT_ONLY_USE_DURATION = 72000;

    private final GrenadeDefinition definition;

    public GrenadeItem(Properties properties, GrenadeDefinition definition) {
        super(properties);
        this.definition = definition;
    }

    public GrenadeDefinition getDefinition() {
        return definition;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (definition.deployment() == GrenadeDeployment.PLACE) {
            return InteractionResultHolder.pass(stack);
        }

        if (player.getCooldowns().isOnCooldown(this)) {
            return InteractionResultHolder.fail(stack);
        }

        player.startUsingItem(hand);
        GrenadeRemoteProfile remoteProfile = definition.remoteProfile();
        if (!level.isClientSide && (remoteProfile == null || !remoteProfile.activationSoundOnStick())) {
            definition.audio().activationSound().play(level, player.position());
        }
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public void onUseTick(Level level, LivingEntity user, ItemStack stack, int remainingUseDuration) {
        if (level.isClientSide || !definition.trigger().fuseRunsWhileHeld()) {
            return;
        }

        if (definition.audio().shouldPlayBeep(remainingUseDuration, definition.fuseTicks())) {
            definition.audio().playBeep(level, user.position(), remainingUseDuration, definition.fuseTicks());
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (definition.deployment() != GrenadeDeployment.PLACE) {
            return super.useOn(context);
        }

        Player player = context.getPlayer();
        ItemStack stack = context.getItemInHand();
        if (player == null || stack.isEmpty() || player.getCooldowns().isOnCooldown(this)) {
            return InteractionResult.FAIL;
        }

        Level level = context.getLevel();
        if (level instanceof ServerLevel serverLevel) {
            GrenadeRemoteLink remoteLink = prepareRemoteLink(serverLevel, stack);
            GrenadeEntity grenade = new GrenadeEntity(ModEntities.GRENADE.get(), serverLevel, player);
            grenade.setItem(stack.copyWithCount(1));
            grenade.setFuseTicks(definition.fuseTicks());
            grenade.placeOnSurface(context.getClickLocation(), context.getClickedFace());
            serverLevel.addFreshEntity(grenade);

            definition.audio().activationSound().play(serverLevel, grenade.position());
            definition.audio().bounceSound().play(serverLevel, grenade.position());
            if (remoteLink != null
                    && definition.remoteProfile().detonatorDelivery() == GrenadeDetonatorDelivery.REPLACE_USED_STACK) {
                player.setItemInHand(context.getHand(), createRemoteDetonator(remoteLink));
                completeUseWithoutConsumption(player);
            } else {
                completeUse(player, stack);
                if (remoteLink != null) {
                    grantRemoteDetonator(player, remoteLink);
                }
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity user, int timeLeft) {
        if (level.isClientSide) {
            return;
        }

        int remainingFuseTicks = definition.trigger().fuseRunsWhileHeld()
                ? Math.max(1, timeLeft)
                : definition.fuseTicks();
        int useTicks = Math.max(0, getUseDuration(stack, user) - timeLeft);
        throwGrenade(
                (ServerLevel) level,
                user,
                stack,
                remainingFuseTicks,
                definition.throwVelocity(useTicks)
        );
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity user) {
        if (level instanceof ServerLevel serverLevel) {
            if (definition.trigger().fuseRunsWhileHeld()) {
                detonateInHand(serverLevel, user, stack);
            } else {
                throwGrenade(
                        serverLevel,
                        user,
                        stack,
                        definition.fuseTicks(),
                        definition.throwVelocity()
                );
            }
        }

        return stack;
    }

    private void throwGrenade(
            ServerLevel level,
            LivingEntity user,
            ItemStack stack,
            int remainingFuseTicks,
            float throwVelocity
    ) {
        if (stack.isEmpty()) {
            return;
        }

        GrenadeRemoteLink remoteLink = prepareRemoteLink(level, stack);
        boolean shouldGrantDetonator = remoteLink != null
                && remoteLink.equals(stack.get(ModDataComponentTypes.GRENADE_REMOTE_LINK.get()));

        GrenadeEntity grenade = new GrenadeEntity(ModEntities.GRENADE.get(), level, user);
        grenade.setItem(stack.copyWithCount(1));
        grenade.setFuseTicks(remainingFuseTicks);
        grenade.setFuseRunning(shouldStartFuseWhenThrown());
        grenade.shootFromRotation(
                user,
                user.getXRot(),
                user.getYRot(),
                0.0F,
                throwVelocity,
                definition.throwInaccuracy()
        );
        level.addFreshEntity(grenade);

        definition.audio().throwSound().play(level, user.position());
        completeUse(user, stack);

        if (shouldGrantDetonator && user instanceof Player player) {
            grantRemoteDetonator(player, remoteLink);
        }
    }

    private GrenadeRemoteLink prepareRemoteLink(ServerLevel level, ItemStack stack) {
        GrenadeRemoteProfile remoteProfile = definition.remoteProfile();
        if (remoteProfile == null) {
            return null;
        }

        GrenadeRemoteLink existingLink = stack.get(ModDataComponentTypes.GRENADE_REMOTE_LINK.get());
        if (existingLink != null && !GrenadeRemoteDetonations.get(level.getServer()).isActivated(existingLink)) {
            return null;
        }

        GrenadeRemoteLink newLink = GrenadeRemoteLink.create();
        stack.set(ModDataComponentTypes.GRENADE_REMOTE_LINK.get(), newLink);
        return newLink;
    }

    private boolean shouldStartFuseWhenThrown() {
        GrenadeRemoteProfile remoteProfile = definition.remoteProfile();
        return remoteProfile != null
                ? remoteProfile.beepsBeforeSticking()
                : !definition.trigger().sticksToBlocks();
    }

    private void grantRemoteDetonator(Player player, GrenadeRemoteLink link) {
        ItemStack detonator = createRemoteDetonator(link);
        if (detonator.isEmpty()) {
            return;
        }

        if (!player.getInventory().add(detonator)) {
            player.drop(detonator, false);
        }
    }

    private ItemStack createRemoteDetonator(GrenadeRemoteLink link) {
        GrenadeRemoteProfile remoteProfile = definition.remoteProfile();
        if (remoteProfile == null) {
            return ItemStack.EMPTY;
        }

        ItemStack detonator = new ItemStack(remoteProfile.detonatorItem().get());
        detonator.set(ModDataComponentTypes.GRENADE_REMOTE_LINK.get(), link);
        return detonator;
    }

    private void detonateInHand(ServerLevel level, LivingEntity user, ItemStack stack) {
        if (stack.isEmpty()) {
            return;
        }

        ItemStack detonatedStack = stack.copyWithCount(1);
        completeUse(user, stack);

        GrenadeEntity grenade = new GrenadeEntity(ModEntities.GRENADE.get(), level, user);
        grenade.setItem(detonatedStack);
        grenade.setFuseTicks(0);
        grenade.setPos(user.getX(), user.getY(0.5D), user.getZ());
        grenade.detonate();
    }

    private void completeUse(LivingEntity user, ItemStack stack) {
        stack.consume(1, user);

        completeUseWithoutConsumption(user);
    }

    private void completeUseWithoutConsumption(LivingEntity user) {
        if (user instanceof Player player) {
            player.awardStat(Stats.ITEM_USED.get(this));
            if (definition.cooldownTicks() > 0) {
                player.getCooldowns().addCooldown(this, definition.cooldownTicks());
            }
        }
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return definition.trigger().fuseRunsWhileHeld()
                ? definition.fuseTicks()
                : IMPACT_ONLY_USE_DURATION;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.SPEAR;
    }

    @Override
    public Projectile asProjectile(Level level, Position position, ItemStack stack, Direction direction) {
        GrenadeEntity grenade = new GrenadeEntity(ModEntities.GRENADE.get(), level);
        grenade.setPos(position.x(), position.y(), position.z());
        grenade.setItem(stack.copyWithCount(1));
        grenade.setFuseTicks(definition.fuseTicks());
        grenade.setFuseRunning(shouldStartFuseWhenThrown());
        return grenade;
    }

    @Override
    public DispenseConfig createDispenseConfig() {
        return DispenseConfig.builder()
                .power(definition.throwVelocity())
                .uncertainty(definition.throwInaccuracy())
                .build();
    }
}
