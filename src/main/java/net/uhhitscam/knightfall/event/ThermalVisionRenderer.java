package net.uhhitscam.knightfall.event;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
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

    @SubscribeEvent
    public static void onRenderLevelStage(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_ENTITIES) return;

        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null || mc.level == null) return;
        if (!ThermalVisionUtil.isThermalActive()) return;

        float pt = event.getPartialTick().getGameTimeDeltaPartialTick(false);

        PoseStack poseStack = event.getPoseStack();
        Camera camera = mc.gameRenderer.getMainCamera();
        Vec3 camPos = camera.getPosition();

        MultiBufferSource.BufferSource buffer = mc.renderBuffers().bufferSource();
        EntityRenderDispatcher dispatcher = mc.getEntityRenderDispatcher();

        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        dispatcher.setRenderShadow(false);

        RenderSystem.setShaderColor(1.0F, 0.4F, 0.08F, 0.5F);

        for (Entity entity : mc.level.entitiesForRendering()) {
            if (!(entity instanceof LivingEntity)) continue;
            if (entity == player) continue;

            double ix = net.minecraft.util.Mth.lerp(pt, entity.xo, entity.getX());
            double iy = net.minecraft.util.Mth.lerp(pt, entity.yo, entity.getY());
            double iz = net.minecraft.util.Mth.lerp(pt, entity.zo, entity.getZ());

            double rx = ix - camPos.x;
            double ry = iy - camPos.y;
            double rz = iz - camPos.z;

            float yaw = net.minecraft.util.Mth.lerp(pt, entity.yRotO, entity.getYRot());

            dispatcher.render(entity, rx, ry, rz, yaw, pt, poseStack, buffer, 0xF000F0);
        }

        buffer.endBatch();

        dispatcher.setRenderShadow(true);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

        RenderSystem.disablePolygonOffset();
        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(true);
    }
}
