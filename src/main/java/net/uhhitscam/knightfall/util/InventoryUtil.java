package net.uhhitscam.knightfall.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.uhhitscam.knightfall.network.PayloadRegister;
import net.uhhitscam.knightfall.network.SSDropItemPacket;

public class InventoryUtil {
    public static boolean hasPlayerStackInInventory(Player player, Item item) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack currentStack = player.getInventory().getItem(i);
            if (!currentStack.isEmpty() && currentStack.is(item)) {
                return true;
            }
        }
        return false;
    }

    public static int getFirstInventoryIndex(Player player, Item item) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack currentStack = player.getInventory().getItem(i);
            if (!currentStack.isEmpty() && currentStack.is(item)) {
                return i;
            }
        }
        return -1;
    }

    public static ItemStack replaceItem(ItemStack usedStack, Player player, ItemStack newStack, int slotIndex) {
        usedStack.consume(1, player);

        if (usedStack.isEmpty()) {
            player.getInventory().setItem(slotIndex, newStack);
            return newStack;
        } else {
            return usedStack;
        }
    }

    public static void giveItem(Player player, ItemStack stack) {
        ItemStack stack2 = stack.copy();
        boolean addedCompletely = player.getInventory().add(stack2);
        player.inventoryMenu.broadcastChanges();

        if (!addedCompletely && !stack2.isEmpty()) {
            PayloadRegister.sendToServer(new SSDropItemPacket(stack2));
        }
    }
}