package net.uhhitscam.knightfall.event;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.player.PlayerContainerEvent;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.item.custom.GrenadeRemoteDetonations;

@EventBusSubscriber(modid = OperationKnightfall.MODID)
public final class GrenadeRemoteDetonatorEvents {
    private GrenadeRemoteDetonatorEvents() {
    }

    @SubscribeEvent
    public static void onContainerOpen(PlayerContainerEvent.Open event) {
        if (!(event.getEntity().level() instanceof ServerLevel serverLevel)) {
            return;
        }

        AbstractContainerMenu menu = event.getContainer();
        boolean changed = false;
        for (Slot slot : menu.slots) {
            if (GrenadeRemoteDetonations.isUnusedDetonator(serverLevel.getServer(), slot.getItem())) {
                slot.set(ItemStack.EMPTY);
                slot.setChanged();
                changed = true;
            }
        }
        if (changed) {
            menu.broadcastChanges();
        }
    }

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if (!(event.getLevel() instanceof ServerLevel serverLevel)) {
            return;
        }

        if (event.getEntity() instanceof ItemEntity itemEntity
                && GrenadeRemoteDetonations.isUnusedDetonator(serverLevel.getServer(), itemEntity.getItem())) {
            itemEntity.discard();
        } else if (event.getEntity() instanceof ItemFrame itemFrame
                && GrenadeRemoteDetonations.isUnusedDetonator(serverLevel.getServer(), itemFrame.getItem())) {
            itemFrame.setItem(ItemStack.EMPTY);
        }
    }
}
