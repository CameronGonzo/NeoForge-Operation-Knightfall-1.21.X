package net.uhhitscam.knightfall.event;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.util.ThermalVisionUtil;

@EventBusSubscriber(modid = OperationKnightfall.MODID, value = Dist.CLIENT)
public class ThermalVisionRenderer {
    private static final int FULL_BRIGHT_LIGHT = 0xF000F0;

    private static final float THERMAL_RED = 1.0F;
    private static final float THERMAL_GREEN = 0.05F;
    private static final float THERMAL_BLUE = 0.02F;
    private static final float THERMAL_ALPHA = 0.65F;

    @SubscribeEvent
    public static void onRenderLevelStage(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_ENTITIES) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;

        if (player == null || minecraft.level == null) {
            return;
        }

        if (!ThermalVisionUtil.isThermalActive()) {
            return;
        }

        float partialTick = event.getPartialTick().getGameTimeDeltaPartialTick(false);
        PoseStack poseStack = event.getPoseStack();

        Camera camera = minecraft.gameRenderer.getMainCamera();
        Vec3 cameraPosition = camera.getPosition();

        MultiBufferSource.BufferSource buffer = minecraft.renderBuffers().bufferSource();
        EntityRenderDispatcher dispatcher = minecraft.getEntityRenderDispatcher();

        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        dispatcher.setRenderShadow(false);
        RenderSystem.setShaderColor(THERMAL_RED, THERMAL_GREEN, THERMAL_BLUE, THERMAL_ALPHA);

        for (Entity entity : minecraft.level.entitiesForRendering()) {
            if (!(entity instanceof LivingEntity)) {
                continue;
            }

            if (entity == player) {
                continue;
            }

            renderThermalEntity(entity, partialTick, cameraPosition, poseStack, buffer, dispatcher);
        }

        buffer.endBatch();

        dispatcher.setRenderShadow(true);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(true);
    }

    private static void renderThermalEntity(
            Entity entity,
            float partialTick,
            Vec3 cameraPosition,
            PoseStack poseStack,
            MultiBufferSource buffer,
            EntityRenderDispatcher dispatcher
    ) {
        double interpolatedX = Mth.lerp(partialTick, entity.xo, entity.getX());
        double interpolatedY = Mth.lerp(partialTick, entity.yo, entity.getY());
        double interpolatedZ = Mth.lerp(partialTick, entity.zo, entity.getZ());

        double renderX = interpolatedX - cameraPosition.x;
        double renderY = interpolatedY - cameraPosition.y;
        double renderZ = interpolatedZ - cameraPosition.z;

        float yaw = Mth.lerp(partialTick, entity.yRotO, entity.getYRot());

        dispatcher.render(
                entity,
                renderX,
                renderY,
                renderZ,
                yaw,
                partialTick,
                poseStack,
                buffer,
                FULL_BRIGHT_LIGHT
        );
    }
}