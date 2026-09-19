package net.uhhitscam.knightfall.event;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.item.custom.projectile.ProjectileItem;

@EventBusSubscriber(modid = OperationKnightfall.MODID, value = Dist.CLIENT)
public final class ProjectileWeaponTooltipEvents {
    private static final Component SHIFT_HINT = Component.translatable("tooltip.knightfall.blaster.shift");

    private ProjectileWeaponTooltipEvents() {}

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        if (event.getItemStack().getOrDefault(net.minecraft.core.component.DataComponents.TOOLTIP_DISPLAY,
                net.minecraft.world.item.component.TooltipDisplay.DEFAULT).hideTooltip()) return;
        var base = new java.util.ArrayList<Component>();
        if (event.getItemStack().getItem() instanceof ProjectileItem weapon) {
            weapon.appendBaseTooltip(event.getItemStack(), base::add);
        } else if (event.getItemStack().getItem() instanceof net.uhhitscam.knightfall.item.custom.projectile.GasItem gas) {
            gas.appendBaseTooltip(event.getItemStack(), base::add);
        }
        event.getToolTip().addAll(Math.min(1, event.getToolTip().size()), base);
        if (!net.minecraft.client.Minecraft.getInstance().hasShiftDown()
                || !(event.getItemStack().getItem() instanceof ProjectileItem weapon)) {
            return;
        }

        event.getToolTip().removeIf(component -> component.getString().equals(SHIFT_HINT.getString()));
        weapon.appendAmmoTypeTooltip(event.getItemStack(), event.getToolTip());
    }
}
