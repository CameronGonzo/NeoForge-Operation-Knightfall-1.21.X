package net.uhhitscam.starwars.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class GeneralUtil {
    public static ItemStack replaceItem(ItemStack usedStack, Player player, ItemStack newStack, int slotIndex) {
        //Always remove the used item from the inventory
        usedStack.shrink(1);
        usedStack.consume(1, player);

        //If the used item is now empty, return the new item in its place
        if (usedStack.isEmpty()) {
            //Set the item at the specified slot to the new stack (gas cartridge)
            player.getInventory().setItem(slotIndex, newStack);  // Replace the item in the same slot
            return newStack; // Return the new item (gas cartridge)
        } else {
            //If not empty, we just return the used item
            return usedStack;
        }
    }
}
