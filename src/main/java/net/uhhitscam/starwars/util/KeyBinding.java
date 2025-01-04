package net.uhhitscam.starwars.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.common.NeoForge;
import net.uhhitscam.starwars.item.custom.Blasteritem;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;

public class KeyBinding {

    private static final String CATEGORY = "key.categories.starwars";
    public static final KeyMapping RELOAD_KEY = new KeyMapping(
            "key.starwars.reload", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_R), CATEGORY
    );

    private static final Map<String, Runnable> ACTIONS = new HashMap<>();

    public static void register(IEventBus modEventBus) {
        // Register key mappings on the mod-specific bus
        modEventBus.addListener(EventPriority.HIGH, KeyBinding::onRegisterKeyMappings);

        // Register client tick on the global event bus
        NeoForge.EVENT_BUS.addListener(EventPriority.HIGH, KeyBinding::onClientTick);
    }



    private static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(RELOAD_KEY);
    }

    private static void onClientTick(ClientTickEvent.Post event) {
        if (RELOAD_KEY.consumeClick()) {
            handleReload();
        }
    }

    private static void handleReload() {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player != null) {
            ItemStack heldItem = player.getMainHandItem();

            // Check if the held item is a Blasteritem before reloading
            if (heldItem.getItem() instanceof Blasteritem blaster) {
                // Now it's safe to reload the Blasteritem
                blaster.reload(player, heldItem);
            }
        }
    }
}
