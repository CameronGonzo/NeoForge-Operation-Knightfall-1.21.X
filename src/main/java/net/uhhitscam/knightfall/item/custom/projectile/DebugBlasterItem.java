package net.uhhitscam.knightfall.item.custom.projectile;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.uhhitscam.knightfall.component.ModDataComponentTypes;

public final class DebugBlasterItem extends ProjectileItem {
    public DebugBlasterItem(Properties properties) {
        super(properties, DebugBlasterSettings.DEFAULT.definition());
    }

    public static DebugBlasterSettings settings(ItemStack stack) {
        return stack.getOrDefault(ModDataComponentTypes.DEBUG_BLASTER_SETTINGS, DebugBlasterSettings.DEFAULT);
    }

    @Override
    public ProjectileWeaponDefinition getDefinition(ItemStack stack) {
        return settings(stack).definition();
    }

    @Override
    public void appendBaseTooltip(ItemStack stack, java.util.function.Consumer<Component> tooltip) {
        super.appendBaseTooltip(stack, tooltip);
        tooltip.accept(Component.translatable("tooltip.knightfall.debug_blaster",
                Component.keybind("key.knightfall.debug_blaster_editor")).withStyle(ChatFormatting.AQUA));
    }
}
