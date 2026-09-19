package net.uhhitscam.knightfall.item.client;

import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.component.ModDataComponentTypes;

public final class RemoteDetonatorItemModelProperties {
    public static final Identifier ACTIVATED = Identifier.fromNamespaceAndPath(
            OperationKnightfall.MODID,
            "activated"
    );

    private RemoteDetonatorItemModelProperties() {
    }

    public static void register(Item item) {
        KnightfallItemModelProperties.register(item, ACTIVATED, (stack, level, entity, seed) ->
                stack.has(ModDataComponentTypes.REMOTE_DETONATOR_STATE.get()) ? 1.0F : 0.0F);
    }
}
