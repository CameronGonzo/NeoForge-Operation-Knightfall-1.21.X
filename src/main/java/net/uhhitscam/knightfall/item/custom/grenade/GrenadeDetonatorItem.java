package net.uhhitscam.knightfall.item.custom.grenade;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.uhhitscam.knightfall.component.GrenadeRemoteLink;
import net.uhhitscam.knightfall.component.ModDataComponentTypes;
import net.uhhitscam.knightfall.component.RemoteDetonatorState;

import java.util.Objects;

public class GrenadeDetonatorItem extends Item {
    private static final int REMOVAL_DELAY_TICKS = 20;

    private final GrenadeSound activationSound;

    public GrenadeDetonatorItem(Properties properties, GrenadeSound activationSound) {
        super(properties);
        this.activationSound = Objects.requireNonNull(activationSound, "Detonator activation sound cannot be null.");
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        GrenadeRemoteLink link = stack.get(ModDataComponentTypes.GRENADE_REMOTE_LINK.get());
        if (link == null || stack.has(ModDataComponentTypes.REMOTE_DETONATOR_STATE.get())) {
            return InteractionResult.FAIL;
        }

        if (level instanceof ServerLevel serverLevel) {
            if (GrenadeRemoteDetonations.get(serverLevel.getServer()).isActivated(link)) {
                stack.setCount(0);
                return InteractionResult.CONSUME;
            }

            stack.set(
                    ModDataComponentTypes.REMOTE_DETONATOR_STATE.get(),
                    new RemoteDetonatorState(level.getGameTime() + REMOVAL_DELAY_TICKS)
            );
            activationSound.play(level, player.position());
            GrenadeRemoteDetonations.activateAndDetonate(serverLevel.getServer(), link);
            player.awardStat(Stats.ITEM_USED.get(this));
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public void inventoryTick(ItemStack stack, net.minecraft.server.level.ServerLevel level, Entity entity, net.minecraft.world.entity.EquipmentSlot slot) {
        super.inventoryTick(stack, level, entity, slot);
        {
            ServerLevel serverLevel = level;
            GrenadeRemoteLink link = stack.get(ModDataComponentTypes.GRENADE_REMOTE_LINK.get());
            if ((link != null
                    && !stack.has(ModDataComponentTypes.REMOTE_DETONATOR_STATE.get())
                    && GrenadeRemoteDetonations.get(serverLevel.getServer()).isActivated(link))
                    || shouldRemove(stack, level.getGameTime())) {
                stack.setCount(0);
            }
        }
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        if (entity.level() instanceof ServerLevel serverLevel) {
            GrenadeRemoteLink link = stack.get(ModDataComponentTypes.GRENADE_REMOTE_LINK.get());
            if ((link != null
                    && !stack.has(ModDataComponentTypes.REMOTE_DETONATOR_STATE.get())
                    && GrenadeRemoteDetonations.get(serverLevel.getServer()).isActivated(link))
                    || shouldRemove(stack, entity.level().getGameTime())) {
                stack.setCount(0);
                entity.discard();
                return true;
            }
        }
        return false;
    }

    private static boolean shouldRemove(ItemStack stack, long gameTime) {
        RemoteDetonatorState state = stack.get(ModDataComponentTypes.REMOTE_DETONATOR_STATE.get());
        return state != null && gameTime >= state.removalGameTime();
    }
}
