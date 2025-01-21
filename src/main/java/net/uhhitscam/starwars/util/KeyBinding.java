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

    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(EventPriority.HIGH, KeyBinding::onRegisterKeyMappings);
        NeoForge.EVENT_BUS.addListener(EventPriority.HIGH, KeyBinding::onClientTick);
    }

    private static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(RELOAD_KEY);
        event.register(SWITCH_FIRING_MODE_KEY);
    }

    private static void onClientTick(ClientTickEvent.Post event) {
        if (RELOAD_KEY.consumeClick()) {
            handleReload();
        }
        if (SWITCH_FIRING_MODE_KEY.consumeClick()) {
            handleSwitchFiringMode();
        }
    }

    private static void handleReload() {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;

        if (player != null) {
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offHandItem = player.getOffhandItem();

            // Check if the main hand item is a BlasterItem and reload it
            if (mainHandItem.getItem() instanceof BlasterItem blasterMain) {
                blasterMain.reload(player, mainHandItem, true);
            }

            // Check if the offhand item is a BlasterItem and reload it
            if (offHandItem.getItem() instanceof BlasterItem blasterOff) {
                blasterOff.reload(player, offHandItem, false);
            }
        }
    }

    private static void handleSwitchFiringMode() {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;

        if (player != null) {
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offHandItem = player.getOffhandItem();

            // Check if the main hand item is a BlasterItem and reload it
            if (mainHandItem.getItem() instanceof BlasterItem blasterMain) {
                // Now it's safe to switch the firing mode of the Blasteritem
                blasterMain.switchFiringMode(mainHandItem, true);
            }

            // Check if the offhand item is a BlasterItem and reload it
            if (offHandItem.getItem() instanceof BlasterItem blasterOff) {
                // Now it's safe to switch the firing mode of the Blasteritem
                blasterOff.switchFiringMode(offHandItem, false);
            }
        }
    }
}
