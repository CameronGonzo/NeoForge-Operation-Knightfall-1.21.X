package net.uhhitscam.knightfall.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.common.NeoForge;
import net.uhhitscam.knightfall.component.AmmoTypeData;
import net.uhhitscam.knightfall.component.ModDataComponentTypes;
import net.uhhitscam.knightfall.component.ReloadNSwitchCoolDownData;
import net.uhhitscam.knightfall.event.ModClientEvents;
import net.uhhitscam.knightfall.item.custom.AmmoType;
import net.uhhitscam.knightfall.item.custom.FlechetteCanisterItem;
import net.uhhitscam.knightfall.item.custom.ProjectileItem;
import net.uhhitscam.knightfall.item.custom.SlugItem;
import net.uhhitscam.knightfall.item.custom.WeaponClassification;
import net.uhhitscam.knightfall.item.custom.WeaponName;
import net.uhhitscam.knightfall.network.PayloadRegister;
import net.uhhitscam.knightfall.network.SSConsumeItemPacket;
import net.uhhitscam.knightfall.network.SSCooldownPacket;
import org.lwjgl.glfw.GLFW;

import java.util.Objects;

public class KeyBinding {
    private static final String CATEGORY = "key.categories.knightfall";

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

    private static final int RELOAD_KEY_HOLD_THRESHOLD = 40;

    private static boolean reloadKeyHeld = false;
    private static boolean unloadTriggered = false;
    private static int reloadKeyHoldTicks = 0;

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

        if (player == null) {
            resetReloadKeyState();
            return;
        }

        if (minecraft.screen != null) {
            resetReloadKeyState();
            return;
        }

        handleReloadKey(player);

        if (SWITCH_FIRING_MODE_KEY.consumeClick()) {
            handleSwitchFiringMode(player);
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
                handleUnload(player);
                unloadTriggered = true;
            }

            return;
        }

        if (reloadKeyHeld && reloadKeyHoldTicks < RELOAD_KEY_HOLD_THRESHOLD && !unloadTriggered) {
            handleReload(player);
        }

        resetReloadKeyState();
    }

    private static void resetReloadKeyState() {
        reloadKeyHeld = false;
        unloadTriggered = false;
        reloadKeyHoldTicks = 0;
    }

    private static void handleReload(LocalPlayer player) {
        ItemStack mainHandItem = player.getMainHandItem();
        ItemStack offHandItem = player.getOffhandItem();

        ReloadAttempt mainReload = tryReloadWeapon(player, mainHandItem, true, 0);

        int additionalAmmoToReserveForOffhand = shouldReserveMainAmmoForOffhand(mainHandItem, offHandItem)
                ? mainReload.ammoToConsume()
                : 0;

        ReloadAttempt offReload = tryReloadWeapon(player, offHandItem, false, additionalAmmoToReserveForOffhand);

        if (!player.isCreative()) {
            consumeReloadedAmmo(player, mainReload, offReload);
        }
    }

    private static ReloadAttempt tryReloadWeapon(
            LocalPlayer player,
            ItemStack weaponStack,
            boolean mainHand,
            int additionalAmmoToConsume
    ) {
        if (!(weaponStack.getItem() instanceof ProjectileItem projectileItem)) {
            return ReloadAttempt.EMPTY;
        }

        ReloadNSwitchCoolDownData cooldownData = getOrCreateReloadNSwitchCooldownData(weaponStack);

        if (cooldownData.isOnCooldown(player.level())) {
            return ReloadAttempt.EMPTY;
        }

        if (projectileItem.getProjectileWeaponName() == WeaponName.DC15S_SIDEARM) {
            return ReloadAttempt.EMPTY;
        }

        PayloadRegister.sendToServer(new SSCooldownPacket(mainHand, true));

        int ammoToConsume = projectileItem.reload(player, weaponStack, mainHand, additionalAmmoToConsume);
        int ammoStackIndex = getAmmoStackIndex(player, projectileItem, ammoToConsume, weaponStack);
        AmmoType ammoType = ProjectileItem.getAmmoType(weaponStack);

        return new ReloadAttempt(projectileItem, ammoToConsume, ammoStackIndex, ammoType);
    }

    private static boolean shouldReserveMainAmmoForOffhand(ItemStack mainHandItem, ItemStack offHandItem) {
        if (!(mainHandItem.getItem() instanceof ProjectileItem mainProjectileItem)) {
            return false;
        }

        if (!(offHandItem.getItem() instanceof ProjectileItem offProjectileItem)) {
            return false;
        }

        return mainProjectileItem.getClassification() == offProjectileItem.getClassification();
    }

    private static void consumeReloadedAmmo(LocalPlayer player, ReloadAttempt mainReload, ReloadAttempt offReload) {
        boolean mainHasAmmoToConsume = mainReload.hasAmmoToConsume();
        boolean offHasAmmoToConsume = offReload.hasAmmoToConsume();

        if (mainHasAmmoToConsume && offHasAmmoToConsume) {
            if (mainReload.ammoStackIndex() == offReload.ammoStackIndex()) {
                ItemStack ammoStack = player.getInventory().items.get(mainReload.ammoStackIndex());
                PayloadRegister.sendToServer(new SSConsumeItemPacket(
                        ammoStack,
                        mainReload.ammoToConsume() + offReload.ammoToConsume(),
                        mainReload.ammoStackIndex()
                ));
            } else {
                consumeAmmo(player, mainReload);
                consumeAmmo(player, offReload);
            }

            return;
        }

        if (mainHasAmmoToConsume) {
            consumeAmmo(player, mainReload);
        }

        if (offHasAmmoToConsume) {
            consumeAmmo(player, offReload);
        }
    }

    private static void consumeAmmo(LocalPlayer player, ReloadAttempt reloadAttempt) {
        if (!reloadAttempt.hasAmmoToConsume()) {
            return;
        }

        ItemStack ammoStack = player.getInventory().items.get(reloadAttempt.ammoStackIndex());
        PayloadRegister.sendToServer(new SSConsumeItemPacket(
                ammoStack,
                reloadAttempt.ammoToConsume(),
                reloadAttempt.ammoStackIndex()
        ));
    }

    private static int getAmmoStackIndex(
            LocalPlayer player,
            ProjectileItem projectileItem,
            int ammoToConsume,
            ItemStack weaponStack
    ) {
        if (player.isCreative() || ammoToConsume <= 0) {
            return -1;
        }

        AmmoType currentAmmoType = getAmmoTypeFromStack(weaponStack);

        if (currentAmmoType == null || currentAmmoType == AmmoType.NONE) {
            return -1;
        }

        for (int i = 0; i < player.getInventory().items.size(); i++) {
            ItemStack ammoStack = player.getInventory().items.get(i);
            Item item = ammoStack.getItem();

            if (projectileItem.getClassification() == WeaponClassification.SLUGTHROWER
                    && item instanceof SlugItem slugItem
                    && currentAmmoType == slugItem.getAmmoType()) {
                return i;
            }

            if (projectileItem.getClassification() == WeaponClassification.FLECHETTE
                    && item instanceof FlechetteCanisterItem flechetteCanisterItem
                    && currentAmmoType == flechetteCanisterItem.getAmmoType()) {
                return i;
            }
        }

        return -1;
    }

    private static AmmoType getAmmoTypeFromStack(ItemStack stack) {
        AmmoTypeData ammoTypeData = stack.get(ModDataComponentTypes.AMMO_TYPE.get());

        if (ammoTypeData == null || ammoTypeData.ammoType() == null || ammoTypeData.ammoType().isBlank()) {
            return null;
        }

        try {
            return AmmoType.valueOf(ammoTypeData.ammoType());
        } catch (IllegalArgumentException ignored) {
            return null;
        }
    }

    private static void handleUnload(LocalPlayer player) {
        ItemStack mainHandItem = player.getMainHandItem();
        ItemStack offHandItem = player.getOffhandItem();

        if (mainHandItem.getItem() instanceof ProjectileItem mainProjectileItem) {
            ModClientEvents.stopFiring(true);
            mainProjectileItem.unload(player, mainHandItem, true);
        }

        if (offHandItem.getItem() instanceof ProjectileItem offProjectileItem) {
            ModClientEvents.stopFiring(false);
            offProjectileItem.unload(player, offHandItem, false);
        }
    }

    private static void handleSwitchFiringMode(LocalPlayer player) {
        trySwitchFiringMode(player, player.getMainHandItem(), true);
        trySwitchFiringMode(player, player.getOffhandItem(), false);
    }

    private static void trySwitchFiringMode(LocalPlayer player, ItemStack weaponStack, boolean mainHand) {
        if (!(weaponStack.getItem() instanceof ProjectileItem projectileItem)) {
            return;
        }

        ReloadNSwitchCoolDownData cooldownData = getOrCreateReloadNSwitchCooldownData(weaponStack);

        if (cooldownData.isOnCooldown(player.level())) {
            return;
        }

        if (projectileItem.getFiringModes().size() <= 1) {
            return;
        }

        PayloadRegister.sendToServer(new SSCooldownPacket(mainHand, false));
        projectileItem.switchFiringMode(player, weaponStack, mainHand);
        ModClientEvents.stopFiring(mainHand);
    }

    private static ReloadNSwitchCoolDownData getOrCreateReloadNSwitchCooldownData(ItemStack stack) {
        ReloadNSwitchCoolDownData cooldownData = stack.get(ModDataComponentTypes.RELOAD_N_SWITCH_COOLDOWN);

        if (cooldownData == null) {
            cooldownData = new ReloadNSwitchCoolDownData(0);
            stack.set(ModDataComponentTypes.RELOAD_N_SWITCH_COOLDOWN, cooldownData);
        }

        return cooldownData;
    }

    private record ReloadAttempt(
            ProjectileItem projectileItem,
            int ammoToConsume,
            int ammoStackIndex,
            AmmoType ammoType
    ) {
        private static final ReloadAttempt EMPTY = new ReloadAttempt(null, 0, -1, AmmoType.NONE);

        private boolean hasAmmoToConsume() {
            return projectileItem != null && ammoToConsume > 0 && ammoStackIndex >= 0;
        }

        private boolean matchesAmmoPool(ReloadAttempt other) {
            if (projectileItem == null || other.projectileItem == null) {
                return false;
            }

            return projectileItem.getClassification() == other.projectileItem.getClassification()
                    && Objects.equals(ammoType, other.ammoType);
        }
    }
}