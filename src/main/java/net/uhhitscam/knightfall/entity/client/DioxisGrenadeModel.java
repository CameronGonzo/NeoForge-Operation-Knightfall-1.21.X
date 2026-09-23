package net.uhhitscam.knightfall.entity.client;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;

public class DioxisGrenadeModel extends BactaBombModel {
    public DioxisGrenadeModel(ModelPart root) {
        super(root, "dioxisGrenade");
    }

    public static LayerDefinition createBodyLayer() {
        return createBodyLayer("dioxisGrenade");
    }
}
