package net.uhhitscam.knightfall.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.entity.custom.GrenadeEntity;
import net.uhhitscam.knightfall.item.ModItems;
import net.uhhitscam.knightfall.item.custom.GrenadeDefinition;
import net.uhhitscam.knightfall.item.custom.GrenadeVisualState;

public class GrenadeRenderer extends EntityRenderer<GrenadeEntity> {
    private static final GrenadeTextures THERMAL_DETONATOR_TEXTURES = textures("thermal_detonator");
    private static final GrenadeTextures IMPACT_THERMAL_DETONATOR_TEXTURES = textures("impact_thermal_detonator");

    private final ThermalDetonatorModel thermalDetonatorModel;
    private final ImpactThermalDetonatorModel impactThermalDetonatorModel;
    private final ItemRenderer itemRenderer;

    public GrenadeRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.thermalDetonatorModel = new ThermalDetonatorModel(
                context.bakeLayer(ModModelLayers.THERMAL_DETONATOR)
        );
        this.impactThermalDetonatorModel = new ImpactThermalDetonatorModel(
                context.bakeLayer(ModModelLayers.IMPACT_THERMAL_DETONATOR)
        );
        this.itemRenderer = context.getItemRenderer();
        this.shadowRadius = 0.15F;
    }

    @Override
    public void render(
            GrenadeEntity entity,
            float entityYaw,
            float partialTicks,
            PoseStack poseStack,
            MultiBufferSource bufferSource,
            int packedLight
    ) {
        HierarchicalModel<GrenadeEntity> model = getModel(entity);
        if (model == null) {
            renderFallbackItem(entity, poseStack, bufferSource, packedLight);
            super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
            return;
        }

        poseStack.pushPose();
        poseStack.translate(0.0F, 0.125F, 0.0F);
        poseStack.scale(0.8F, -0.8F, 0.8F);

        float yaw = Mth.lerp(partialTicks, entity.yRotO, entity.getYRot());
        float pitch = entity.isResting()
                ? 0.0F
                : Mth.lerp(partialTicks, entity.xRotO, entity.getXRot());
        poseStack.mulPose(Axis.YP.rotationDegrees(yaw));
        poseStack.mulPose(Axis.XP.rotationDegrees(-pitch));

        ResourceLocation texture = getTextureLocation(entity);
        VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(texture));
        model.renderToBuffer(
                poseStack,
                consumer,
                packedLight,
                OverlayTexture.NO_OVERLAY
        );

        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }

    private void renderFallbackItem(
            GrenadeEntity entity,
            PoseStack poseStack,
            MultiBufferSource bufferSource,
            int packedLight
    ) {
        poseStack.pushPose();
        poseStack.mulPose(entityRenderDispatcher.cameraOrientation());
        itemRenderer.renderStatic(
                entity.getItem(),
                ItemDisplayContext.GROUND,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                bufferSource,
                entity.level(),
                entity.getId()
        );
        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(GrenadeEntity entity) {
        GrenadeTextures textures = getTextures(entity);
        if (textures == null) {
            return THERMAL_DETONATOR_TEXTURES.base();
        }

        GrenadeDefinition definition = entity.getGrenadeDefinition();
        if (definition == null) {
            return textures.base();
        }

        return switch (GrenadeVisualState.forThrownGrenade(definition, entity.getFuseTicks())) {
            case INACTIVE -> textures.base();
            case ACTIVE -> textures.active();
            case BEEP -> textures.beep();
        };
    }

    private HierarchicalModel<GrenadeEntity> getModel(GrenadeEntity entity) {
        if (entity.getItem().is(ModItems.THERMAL_DETONATOR.get())) {
            return thermalDetonatorModel;
        }
        if (entity.getItem().is(ModItems.IMPACT_THERMAL_DETONATOR.get())) {
            return impactThermalDetonatorModel;
        }
        return null;
    }

    private static GrenadeTextures getTextures(GrenadeEntity entity) {
        if (entity.getItem().is(ModItems.THERMAL_DETONATOR.get())) {
            return THERMAL_DETONATOR_TEXTURES;
        }
        if (entity.getItem().is(ModItems.IMPACT_THERMAL_DETONATOR.get())) {
            return IMPACT_THERMAL_DETONATOR_TEXTURES;
        }
        return null;
    }

    private static GrenadeTextures textures(String name) {
        return new GrenadeTextures(
                texture(name),
                texture(name + "_1"),
                texture(name + "_2")
        );
    }

    private static ResourceLocation texture(String name) {
        return ResourceLocation.fromNamespaceAndPath(
                OperationKnightfall.MODID,
                "textures/entity/" + name + ".png"
        );
    }

    private record GrenadeTextures(
            ResourceLocation base,
            ResourceLocation active,
            ResourceLocation beep
    ) {
    }
}
