package net.uhhitscam.knightfall.item.client;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.component.ModDataComponentTypes;

public final class RemoteDetonatorItemModelProperties {
    public static final ResourceLocation ACTIVATED = ResourceLocation.fromNamespaceAndPath(
            OperationKnightfall.MODID,
            "activated"
    );

    private RemoteDetonatorItemModelProperties() {
    }

    public static void register(Item item) {
        ItemProperties.register(item, ACTIVATED, (stack, level, entity, seed) ->
                stack.has(ModDataComponentTypes.REMOTE_DETONATOR_STATE.get()) ? 1.0F : 0.0F);
    }
}
