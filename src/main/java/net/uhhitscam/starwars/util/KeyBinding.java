package net.uhhitscam.starwars.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.common.NeoForge;
import net.uhhitscam.starwars.item.custom.BlasterItem;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {

    private static final String CATEGORY = "key.categories.starwars";
    public static final KeyMapping RELOAD_KEY = new KeyMapping(
            "key.starwars.reload", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_R), CATEGORY);
    public static final KeyMapping SWITCH_FIRING_MODE_KEY = new KeyMapping(
            "key.starwars.switch", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_B), CATEGORY);
//    public static final KeyMapping MAIN_HAND_FIRING = new KeyMapping(
//            "key.starwars.mainfiring", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_RIGHT), CATEGORY);

    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(EventPriority.HIGH, KeyBinding::onRegisterKeyMappings);
        NeoForge.EVENT_BUS.addListener(EventPriority.HIGH, KeyBinding::onClientTick);
    }

    private static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(RELOAD_KEY);
        event.register(SWITCH_FIRING_MODE_KEY);
//        event.register(MAIN_HAND_FIRING);
    }

    private static void onClientTick(ClientTickEvent.Post event) {
        if (RELOAD_KEY.consumeClick()) {
            handleReload();
        }
        if (SWITCH_FIRING_MODE_KEY.consumeClick()) {
            handleSwitchFiringMode();
        }
//        if (MAIN_HAND_FIRING.consumeClick()) {
//            handleMainHandFiring();
//        }
    }

    private static void handleReload() {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player != null) {
            ItemStack heldItem = player.getMainHandItem();

            // Check if the held item is a Blasteritem before reloading
            if (heldItem.getItem() instanceof BlasterItem blaster) {
                // Now it's safe to reload the Blasteritem
                blaster.reload(player, heldItem);
            }
        }
    }

    private static void handleSwitchFiringMode() {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player != null) {
            ItemStack blaster = player.getMainHandItem();

            // Check if the held item is a Blasteritem before reloading
            if (blaster.getItem() instanceof BlasterItem stack) {
                // Now it's safe to switch the firing mode of the Blasteritem
                stack.switchFiringMode(blaster);
            }
        }
    }

//    private static void handleMainHandFiring() {
//        Minecraft minecraft = Minecraft.getInstance();
//        LocalPlayer player = minecraft.player;
//        if (player != null) {
//            ItemStack heldItem = player.getMainHandItem();
//            if (heldItem.getItem() instanceof BlasterItem blaster) {
//                blaster.mainHandFiring(player, heldItem); // New method in BlasterItem for firing
//            }
//        }
//    }
}
