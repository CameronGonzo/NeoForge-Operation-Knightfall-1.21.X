package net.uhhitscam.knightfall.item.custom;

import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.uhhitscam.knightfall.entity.ModEntities;
import net.uhhitscam.knightfall.entity.custom.GrenadeEntity;

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
        if (player.getCooldowns().isOnCooldown(this)) {
            return InteractionResultHolder.fail(stack);
        }

        player.startUsingItem(hand);
        if (!level.isClientSide) {
            definition.audio().beepSound().play(level, player.position());
        }
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public void onUseTick(Level level, LivingEntity user, ItemStack stack, int remainingUseDuration) {
        if (level.isClientSide || !definition.trigger().detonatesOnFuse()) {
            return;
        }

        if (definition.audio().shouldPlayBeep(remainingUseDuration, definition.fuseTicks())) {
            definition.audio().beepSound().play(level, user.position());
        }
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity user, int timeLeft) {
        if (level.isClientSide) {
            return;
        }

        int remainingFuseTicks = definition.trigger().detonatesOnFuse()
                ? Math.max(1, timeLeft)
                : definition.fuseTicks();
        throwGrenade((ServerLevel) level, user, stack, remainingFuseTicks);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity user) {
        if (level instanceof ServerLevel serverLevel) {
            if (definition.trigger().detonatesOnFuse()) {
                detonateInHand(serverLevel, user, stack);
            } else {
                throwGrenade(serverLevel, user, stack, definition.fuseTicks());
            }
        }

        return stack;
    }

    private void throwGrenade(ServerLevel level, LivingEntity user, ItemStack stack, int remainingFuseTicks) {
        if (stack.isEmpty()) {
            return;
        }

        GrenadeEntity grenade = new GrenadeEntity(ModEntities.GRENADE.get(), level, user);
        grenade.setItem(stack.copyWithCount(1));
        grenade.setFuseTicks(remainingFuseTicks);
        grenade.shootFromRotation(
                user,
                user.getXRot(),
                user.getYRot(),
                0.0F,
                definition.throwVelocity(),
                definition.throwInaccuracy()
        );
        level.addFreshEntity(grenade);

        definition.audio().throwSound().play(level, user.position());
        completeUse(user, stack);
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

        if (user instanceof Player player) {
            player.awardStat(Stats.ITEM_USED.get(this));
            if (definition.cooldownTicks() > 0) {
                player.getCooldowns().addCooldown(this, definition.cooldownTicks());
            }
        }
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return definition.trigger().detonatesOnFuse()
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
