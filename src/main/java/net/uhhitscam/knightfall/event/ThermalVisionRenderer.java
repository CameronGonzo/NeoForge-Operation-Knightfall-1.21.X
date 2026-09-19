package net.uhhitscam.knightfall.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.LivingEntity;
import net.uhhitscam.knightfall.util.ThermalVisionUtil;

public final class ThermalVisionRenderer {
    public static final ContextKey<Boolean> ACTIVE = new ContextKey<>(
            Identifier.fromNamespaceAndPath("knightfall", "thermal_vision"));

    private ThermalVisionRenderer() {}

    public static void extract(LivingEntity entity, LivingEntityRenderState state) {
        state.setRenderData(ACTIVE, ThermalVisionUtil.isThermalActive()
                && entity != Minecraft.getInstance().player && entity.isAlive());
    }
}
