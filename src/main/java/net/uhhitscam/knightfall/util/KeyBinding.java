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
import net.uhhitscam.knightfall.item.custom.*;
import net.uhhitscam.knightfall.network.PayloadRegister;
import net.uhhitscam.knightfall.network.SSConsumeItemPacket;
import net.uhhitscam.knightfall.network.SSCooldownPacket;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {

    private static final String CATEGORY = "key.categories.knightfall";
    public static final KeyMapping RELOAD_KEY = new KeyMapping(
            "key.knightfall.reload", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_R), CATEGORY);
    public static final KeyMapping SWITCH_FIRING_MODE_KEY = new KeyMapping(
            "key.knightfall.switch", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_B), CATEGORY);

    private static final int reloadKeyPressThreshold = 40;
    private static boolean isReloadKeyHeld = false;
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
        if (minecraft.screen != null) return;

        if (RELOAD_KEY.isDown()) {
            if (reloadKeyHoldTicks >= reloadKeyPressThreshold) {
                handleUnload();
            }

            if (!isReloadKeyHeld) {
                isReloadKeyHeld = true;
            } else {
                reloadKeyHoldTicks++;
            }
        } else {
            if (isReloadKeyHeld && (reloadKeyHoldTicks < reloadKeyPressThreshold)) {
                handleReload();
            }
            isReloadKeyHeld = false;
            reloadKeyHoldTicks = 0;
        }

        if (SWITCH_FIRING_MODE_KEY.consumeClick()) {
            handleSwitchFiringMode();
        }
    }

    private static void handleReload() {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        int mainIndex = -1;
        int offIndex = -1;
        int ammoToConsumeMain = 0;
        int ammoToConsumeOff = 0;

        if (player != null) {
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offHandItem = player.getOffhandItem();

            if (mainHandItem.getItem() instanceof ProjectileItem mainProjectileItem) {
                ReloadNSwitchCoolDownData ReloadNSwitchCoolDownData = mainHandItem.get(ModDataComponentTypes.RELOAD_N_SWITCH_COOLDOWN);

                if (ReloadNSwitchCoolDownData == null) {
                    ReloadNSwitchCoolDownData = new ReloadNSwitchCoolDownData(0);
                    mainHandItem.set(ModDataComponentTypes.RELOAD_N_SWITCH_COOLDOWN, ReloadNSwitchCoolDownData);
                }

                if (ReloadNSwitchCoolDownData.isOnCooldown(player.level()) || mainProjectileItem.getProjectileWeaponName().equals(WeaponName.DC15S_SIDEARM)) {
                    return;
                }

                PayloadRegister.sendToServer(new SSCooldownPacket(true, true));
                ammoToConsumeMain = mainProjectileItem.reload(player, mainHandItem, true, 0);
                mainIndex = getIndex(mainProjectileItem, ammoToConsumeMain, mainHandItem);
            }

            if (offHandItem.getItem() instanceof ProjectileItem offProjectileItem) {
                ReloadNSwitchCoolDownData ReloadNSwitchCoolDownData = offHandItem.get(ModDataComponentTypes.RELOAD_N_SWITCH_COOLDOWN);

                if (ReloadNSwitchCoolDownData == null) {
                    ReloadNSwitchCoolDownData = new ReloadNSwitchCoolDownData(0);
                    offHandItem.set(ModDataComponentTypes.RELOAD_N_SWITCH_COOLDOWN, ReloadNSwitchCoolDownData);
                }

                if (ReloadNSwitchCoolDownData.isOnCooldown(player.level()) || offProjectileItem.getProjectileWeaponName().equals(WeaponName.DC15S_SIDEARM)) {
                    return;
                }

                PayloadRegister.sendToServer(new SSCooldownPacket(false, true));
                if (mainHandItem.getItem() instanceof ProjectileItem mainProjectileItem && mainProjectileItem.getClassification().equals(offProjectileItem.getClassification())) {
                    ammoToConsumeOff = offProjectileItem.reload(player, offHandItem, false, ammoToConsumeMain);
                } else {
                    ammoToConsumeOff = offProjectileItem.reload(player, offHandItem, false, 0);
                }
                offIndex = getIndex(offProjectileItem, ammoToConsumeOff, offHandItem);
            }

            if (!player.isCreative()) {
                if (mainIndex > -1 && offIndex > -1 && mainHandItem.getItem() instanceof ProjectileItem mainProjectileItem && offHandItem.getItem() instanceof ProjectileItem offProjectileItem) {
                    AmmoTypeData mainProjectileWeaponAmmoTypeData = mainHandItem.get(ModDataComponentTypes.AMMO_TYPE.get());
                    AmmoType mainCurrentAmmoType = (mainProjectileWeaponAmmoTypeData != null) ? AmmoType.valueOf(mainProjectileWeaponAmmoTypeData.ammoType()) : null;
                    AmmoTypeData offProjectileWeaponAmmoTypeData = offHandItem.get(ModDataComponentTypes.AMMO_TYPE.get());
                    AmmoType offCurrentAmmoType = (offProjectileWeaponAmmoTypeData != null) ? AmmoType.valueOf(offProjectileWeaponAmmoTypeData.ammoType()) : null;
                    if (mainProjectileItem.getClassification().equals(offProjectileItem.getClassification()) && mainCurrentAmmoType.equals(offCurrentAmmoType)) {
                        ItemStack ammoStack = player.getInventory().items.get(mainIndex);
                        PayloadRegister.sendToServer(new SSConsumeItemPacket(ammoStack, ammoToConsumeMain + ammoToConsumeOff, mainIndex));
                    } else if (!mainProjectileItem.getClassification().equals(offProjectileItem.getClassification()) || !mainCurrentAmmoType.equals(offCurrentAmmoType)) {
                        ItemStack mainAmmoStack = player.getInventory().items.get(mainIndex);
                        PayloadRegister.sendToServer(new SSConsumeItemPacket(mainAmmoStack, ammoToConsumeMain, mainIndex));
                        ItemStack offAmmoStack = player.getInventory().items.get(offIndex);
                        PayloadRegister.sendToServer(new SSConsumeItemPacket(offAmmoStack, ammoToConsumeOff, offIndex));
                    }
                } else if (mainIndex > -1 && offIndex <= -1 && mainHandItem.getItem() instanceof ProjectileItem) {
                    ItemStack mainAmmoStack = player.getInventory().items.get(mainIndex);
                    PayloadRegister.sendToServer(new SSConsumeItemPacket(mainAmmoStack, ammoToConsumeMain, mainIndex));
                } else if (mainIndex <= -1 && offIndex > -1 && offHandItem.getItem() instanceof ProjectileItem) {
                    ItemStack offAmmoStack = player.getInventory().items.get(offIndex);
                    PayloadRegister.sendToServer(new SSConsumeItemPacket(offAmmoStack, ammoToConsumeOff, offIndex));
                }
            }
        }
    }

    private static int getIndex(ProjectileItem projectileItem, int ammoToConsume, ItemStack stack) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        AmmoTypeData mainProjectileWeaponAmmoTypeData = stack.get(ModDataComponentTypes.AMMO_TYPE.get());
        AmmoType CurrentAmmoType = (mainProjectileWeaponAmmoTypeData != null) ? AmmoType.valueOf(mainProjectileWeaponAmmoTypeData.ammoType()) : null;

        if (player.isCreative()) {
            return -1;
        }

        for (int i = 0; i < player.getInventory().items.size(); i++) {
            ItemStack ammoStack = player.getInventory().items.get(i);
            Item item = ammoStack.getItem();
            player.inventoryMenu.broadcastChanges();
            if (item instanceof SlugItem && projectileItem.getClassification().equals(WeaponClassification.SLUGTHROWER)) {
                assert CurrentAmmoType != null;
                if ((CurrentAmmoType.equals(((SlugItem) item).getAmmoType())) && ammoToConsume > 0) {
                    return i;
                }
            } else {
                if (item instanceof FlechetteCanisterItem && projectileItem.getClassification().equals(WeaponClassification.FLECHETTE)) {
                    assert CurrentAmmoType != null;
                    if (CurrentAmmoType.equals(((FlechetteCanisterItem) item).getAmmoType()) && ammoToConsume > 0) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }

    private static void handleUnload() {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;

        if (player != null) {
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offHandItem = player.getOffhandItem();

            if (mainHandItem.getItem() instanceof ProjectileItem mainProjectileItem) {
                mainProjectileItem.unload(player, mainHandItem, true);
            }

            if (offHandItem.getItem() instanceof ProjectileItem offProjectileItem) {
                offProjectileItem.unload(player, offHandItem, false);
            }
        }
    }

    private static void handleSwitchFiringMode() {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;

        if (player != null) {
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offHandItem = player.getOffhandItem();

            if (mainHandItem.getItem() instanceof ProjectileItem mainProjectileItem) {
                ReloadNSwitchCoolDownData ReloadNSwitchCoolDownData = offHandItem.get(ModDataComponentTypes.RELOAD_N_SWITCH_COOLDOWN);

                if (ReloadNSwitchCoolDownData == null) {
                    ReloadNSwitchCoolDownData = new ReloadNSwitchCoolDownData(0);
                    mainHandItem.set(ModDataComponentTypes.RELOAD_N_SWITCH_COOLDOWN, ReloadNSwitchCoolDownData);
                }

                if (ReloadNSwitchCoolDownData.isOnCooldown(player.level()) || mainProjectileItem.getFiringModes().size() <= 1) {
                    return;
                }

                PayloadRegister.sendToServer(new SSCooldownPacket(true, false));
                mainProjectileItem.switchFiringMode(player, mainHandItem, true);
                ModClientEvents.mainFiring = false;
            }

            if (offHandItem.getItem() instanceof ProjectileItem offProjectileItem) {
                ReloadNSwitchCoolDownData ReloadNSwitchCoolDownData = offHandItem.get(ModDataComponentTypes.RELOAD_N_SWITCH_COOLDOWN);

                if (ReloadNSwitchCoolDownData == null) {
                    ReloadNSwitchCoolDownData = new ReloadNSwitchCoolDownData(0);
                    offHandItem.set(ModDataComponentTypes.RELOAD_N_SWITCH_COOLDOWN, ReloadNSwitchCoolDownData);
                }

                if (ReloadNSwitchCoolDownData.isOnCooldown(player.level()) || offProjectileItem.getFiringModes().size() <= 1) {

                    return;
                }

                PayloadRegister.sendToServer(new SSCooldownPacket(false, false));
                offProjectileItem.switchFiringMode(player, offHandItem, false);
                ModClientEvents.offFiring = false;
            }
        }
    }
}
