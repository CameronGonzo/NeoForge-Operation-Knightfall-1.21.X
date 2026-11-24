package net.uhhitscam.knightfall.event;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.component.AmmoData;
import net.uhhitscam.knightfall.component.AmmoTypeData;
import net.uhhitscam.knightfall.component.FireCoolDownData;
import net.uhhitscam.knightfall.component.FiringModeData;
import net.uhhitscam.knightfall.component.ModDataComponentTypes;
import net.uhhitscam.knightfall.component.OverheatData;
import net.uhhitscam.knightfall.component.ReloadNSwitchCoolDownData;
import net.uhhitscam.knightfall.item.custom.projectile.DebugBlasterItem;
import net.uhhitscam.knightfall.item.custom.projectile.DebugBlasterSettings;
import net.uhhitscam.knightfall.network.CSDebugBlasterEditorPacket;
import net.uhhitscam.knightfall.network.PayloadRegister;
import net.uhhitscam.knightfall.network.SSDebugBlasterEditorPacket;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@EventBusSubscriber(modid = OperationKnightfall.MODID)
public final class DebugBlasterEditing {
    private record Session(int id, boolean mainHand, int slot, ItemStack stack) {}
    private static final Map<UUID, Session> SESSIONS = new HashMap<>();
    private static int nextSessionId;

    private DebugBlasterEditing() {}

    public static void handle(ServerPlayer player, SSDebugBlasterEditorPacket packet) {
        if (!player.isCreative() || !player.isAlive() || player.isSpectator()) {
            Session previous = SESSIONS.remove(player.getUUID());
            if (previous != null) {
                reply(player, previous, "The editor requires Creative mode.", true);
                player.sendSystemMessage(Component.literal("The Debug Blaster editor requires Creative mode."), true);
            }
            return;
        }
        if (packet.action() == SSDebugBlasterEditorPacket.OPEN) {
            ItemStack stack = packet.mainHand() ? player.getMainHandItem() : player.getOffhandItem();
            if (!(stack.getItem() instanceof DebugBlasterItem)) return;
            ProjectileWeaponServerEvents.cancelForEditing(player);
            Session session = new Session(++nextSessionId, packet.mainHand(), player.getInventory().getSelectedSlot(), stack);
            SESSIONS.put(player.getUUID(), session);
            reply(player, session, "", false);
            return;
        }
        Session session = SESSIONS.get(player.getUUID());
        if (session == null || session.id() != packet.sessionId() || session.mainHand() != packet.mainHand()) return;
        ItemStack held = session.mainHand() ? player.getMainHandItem() : player.getOffhandItem();
        if (held != session.stack() || session.mainHand() && player.getInventory().getSelectedSlot() != session.slot()) {
            reply(player, session, "Held blaster changed. Close and reopen the editor.", false);
            SESSIONS.remove(player.getUUID());
            return;
        }
        if (packet.action() == SSDebugBlasterEditorPacket.APPLY) {
            ProjectileWeaponServerEvents.cancelForEditing(player);
            DebugBlasterSettings settings = packet.settings();
            var previous = DebugBlasterItem.settings(held).definition();
            var mode = ((DebugBlasterItem) held.getItem()).getFiringMode(held);
            if (previous.defaultFiringMode() != settings.definition().defaultFiringMode()
                    || !settings.definition().firingModes().contains(mode)) {
                mode = settings.definition().defaultFiringMode();
            }
            held.set(ModDataComponentTypes.DEBUG_BLASTER_SETTINGS, settings);
            // Applying starts a fresh trial: no old heat, ammo or cooldown interferes with the new settings.
            held.set(ModDataComponentTypes.FIRING_MODE, new FiringModeData(mode.name()));
            refill(held);
            player.inventoryMenu.broadcastChanges();
            reply(player, session, "Debug blaster settings applied.", true);
            player.sendSystemMessage(Component.literal("Debug blaster settings applied."), true);
            SESSIONS.remove(player.getUUID());
        } else if (packet.action() == SSDebugBlasterEditorPacket.REFILL) {
            ProjectileWeaponServerEvents.cancelForEditing(player);
            refill(held);
            player.inventoryMenu.broadcastChanges();
            reply(player, session, "Refilled and cooled. Unsaved edits are still in the editor.", false);
        }
    }

    private static void refill(ItemStack stack) {
        var definition = DebugBlasterItem.settings(stack).definition();
        stack.set(ModDataComponentTypes.AMMO, new AmmoData(definition.maxAmmo()));
        stack.set(ModDataComponentTypes.AMMO_TYPE, new AmmoTypeData(definition.ammoType().name()));
        stack.set(ModDataComponentTypes.OVERHEAT, new OverheatData(0, 0));
        stack.set(ModDataComponentTypes.FIRE_COOLDOWN, new FireCoolDownData(0));
        stack.set(ModDataComponentTypes.RELOAD_N_SWITCH_COOLDOWN, new ReloadNSwitchCoolDownData(0));
    }

    private static void reply(ServerPlayer player, Session session, String message, boolean close) {
        PayloadRegister.sendToPlayer(player, new CSDebugBlasterEditorPacket(session.id(), session.mainHand(),
                DebugBlasterItem.settings(session.stack()), message, close));
    }

    @SubscribeEvent
    public static void onLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        SESSIONS.remove(event.getEntity().getUUID());
    }
}
