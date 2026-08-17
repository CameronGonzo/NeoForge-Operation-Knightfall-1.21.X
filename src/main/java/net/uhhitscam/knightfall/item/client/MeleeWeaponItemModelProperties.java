package net.uhhitscam.knightfall.item.client;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.uhhitscam.knightfall.OperationKnightfall;

public final class MeleeWeaponItemModelProperties {
    public static final ResourceLocation HELD = ResourceLocation.fromNamespaceAndPath(
            OperationKnightfall.MODID,
            "held"
    );

    private MeleeWeaponItemModelProperties() {
    }

    public static void register(Item item) {
        ItemProperties.register(item, HELD, (stack, level, entity, seed) -> isHeldStack(stack, entity) ? 1.0F : 0.0F);
    }

    private static boolean isHeldStack(ItemStack renderedStack, LivingEntity entity) {
        return entity != null
                && (entity.getMainHandItem() == renderedStack || entity.getOffhandItem() == renderedStack);
    }
}
