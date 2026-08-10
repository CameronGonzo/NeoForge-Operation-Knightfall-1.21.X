package net.uhhitscam.knightfall.item.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.item.custom.GrenadeDefinition;
import net.uhhitscam.knightfall.item.custom.GrenadeItem;
import net.uhhitscam.knightfall.item.custom.GrenadeVisualState;

public final class GrenadeItemModelProperties {
    public static final ResourceLocation GRENADE_STATE = ResourceLocation.fromNamespaceAndPath(
            OperationKnightfall.MODID,
            "grenade_state"
    );

    private GrenadeItemModelProperties() {
    }

    public static void register(Item item) {
        ItemProperties.register(item, GRENADE_STATE, GrenadeItemModelProperties::getGrenadeState);
    }

    private static float getGrenadeState(
            ItemStack stack,
            ClientLevel level,
            LivingEntity entity,
            int seed
    ) {
        if (entity == null
                || !entity.isUsingItem()
                || entity.getUseItem() != stack
                || !(stack.getItem() instanceof GrenadeItem grenadeItem)) {
            return GrenadeVisualState.INACTIVE.modelPredicateValue();
        }

        GrenadeDefinition definition = grenadeItem.getDefinition();
        int remainingUseTicks = entity.getUseItemRemainingTicks();
        int useTicks = Math.max(0, stack.getUseDuration(entity) - remainingUseTicks);
        return GrenadeVisualState.forHeldGrenade(definition, useTicks, remainingUseTicks)
                .modelPredicateValue();
    }
}
