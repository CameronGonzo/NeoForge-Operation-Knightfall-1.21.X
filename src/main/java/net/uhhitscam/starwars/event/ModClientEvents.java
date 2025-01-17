package net.uhhitscam.starwars.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.uhhitscam.starwars.OperationKnightfall;
import net.uhhitscam.starwars.item.custom.BlasterItem;
import net.uhhitscam.starwars.network.PayloadRegister;
import net.uhhitscam.starwars.network.SSFireBlasterPacket;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = OperationKnightfall.MODID, value = Dist.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void onMouseInput(InputEvent.MouseButton.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;

        // Check if the right mouse button is clicked
        if (event.getButton() == GLFW.GLFW_MOUSE_BUTTON_RIGHT && event.getAction() == GLFW.GLFW_PRESS && player != null) {
            ItemStack heldItem = player.getMainHandItem();

            // Check if the item in the main hand is a BlasterItem
            if (heldItem.getItem() instanceof BlasterItem) {
                // Send the packet to the server to fire the blaster
                PayloadRegister.sendToServer(new SSFireBlasterPacket(heldItem, "blasterGasType"));
            }
        }
    }
}
