package net.uhhitscam.knightfall.event;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.item.custom.ProjectileItem;

@EventBusSubscriber(modid = OperationKnightfall.MODID, value = Dist.CLIENT)
public final class ProjectileWeaponTooltipEvents {
    private static final Component SHIFT_HINT = Component.translatable("tooltip.knightfall.blaster.shift");

    private ProjectileWeaponTooltipEvents() {}

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        if (!Screen.hasShiftDown()
                || !(event.getItemStack().getItem() instanceof ProjectileItem weapon)) {
            return;
        }

        event.getToolTip().removeIf(component -> component.getString().equals(SHIFT_HINT.getString()));
        weapon.appendAmmoTypeTooltip(event.getItemStack(), event.getToolTip());
    }
}
