package net.uhhitscam.knightfall.util;

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
import net.uhhitscam.knightfall.event.ModClientEvents;
import net.uhhitscam.knightfall.item.custom.ProjectileItem;
import net.uhhitscam.knightfall.item.custom.WeaponAction;
import net.uhhitscam.knightfall.network.PayloadRegister;
import net.uhhitscam.knightfall.network.SSProjectileWeaponActionPacket;
import org.lwjgl.glfw.GLFW;

public final class KeyBinding {
    private static final String CATEGORY = "key.categories.knightfall";
    private static final int RELOAD_KEY_HOLD_THRESHOLD = 40;

    public static final KeyMapping RELOAD_KEY = new KeyMapping(
            "key.knightfall.reload",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_R),
            CATEGORY
    );

    public static final KeyMapping SWITCH_FIRING_MODE_KEY = new KeyMapping(
            "key.knightfall.switch",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_B),
            CATEGORY
    );

    private static boolean reloadKeyHeld;
    private static boolean unloadTriggered;
    private static int reloadKeyHoldTicks;

    private KeyBinding() {}

    public static void init() {
    }

    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(EventPriority.HIGH, KeyBinding::onRegisterKeyMappings);
        NeoForge.EVENT_BUS.addListener(EventPriority.HIGH, KeyBinding::onClientTick);
    }

    private static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(RELOAD_KEY);
        event.register(SWITCH_FIRING_MODE_KEY);
    }

    private static void onClientTick(ClientTickEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player == null || minecraft.screen != null) {
            resetReloadKeyState();
            return;
        }

        handleReloadKey(player);
        if (SWITCH_FIRING_MODE_KEY.consumeClick()) {
            sendActionForHeldWeapons(player, WeaponAction.SWITCH_MODE);
        }
    }

    private static void handleReloadKey(LocalPlayer player) {
        if (RELOAD_KEY.isDown()) {
            if (!reloadKeyHeld) {
                reloadKeyHeld = true;
                reloadKeyHoldTicks = 0;
                unloadTriggered = false;
                return;
            }

            reloadKeyHoldTicks++;
            if (reloadKeyHoldTicks >= RELOAD_KEY_HOLD_THRESHOLD && !unloadTriggered) {
                sendActionForHeldWeapons(player, WeaponAction.UNLOAD);
                unloadTriggered = true;
            }
            return;
        }

        if (reloadKeyHeld && reloadKeyHoldTicks < RELOAD_KEY_HOLD_THRESHOLD && !unloadTriggered) {
            sendActionForHeldWeapons(player, WeaponAction.RELOAD);
        }
        resetReloadKeyState();
    }

    private static void sendActionForHeldWeapons(LocalPlayer player, WeaponAction action) {
        sendAction(player.getMainHandItem(), true, action);
        sendAction(player.getOffhandItem(), false, action);
    }

    private static void sendAction(ItemStack stack, boolean mainHand, WeaponAction action) {
        if (!(stack.getItem() instanceof ProjectileItem weapon)) {
            return;
        }
        if (action == WeaponAction.SWITCH_MODE && weapon.getFiringModes().size() <= 1) {
            return;
        }

        ModClientEvents.stopFiring(mainHand);
        PayloadRegister.sendToServer(new SSProjectileWeaponActionPacket(mainHand, action));
    }

    private static void resetReloadKeyState() {
        reloadKeyHeld = false;
        unloadTriggered = false;
        reloadKeyHoldTicks = 0;
    }
}
