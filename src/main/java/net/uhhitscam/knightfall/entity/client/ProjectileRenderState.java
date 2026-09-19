package net.uhhitscam.knightfall.entity.client;

import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.Identifier;

public class ProjectileRenderState extends EntityRenderState {
    public float yaw;
    public float pitch;
    public Identifier texture;
    public Identifier coreTexture;
    public Identifier glowTexture;
}
