package net.uhhitscam.knightfall.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class ExplosiveShotMagnetizedSigParticles extends TextureSheetParticle {
    private final SpriteSet sprites;
    private static final int FRAME_COUNT = 4;
    private static final int FRAME_DURATION_TICKS = 1;

    protected ExplosiveShotMagnetizedSigParticles(ClientLevel level, double x, double y, double z,
                                                  SpriteSet spriteSet, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
        this.sprites = spriteSet;
        this.friction = 0.98f;
        this.gravity = 0.0f;
        this.quadSize = 0.6f;
        this.xd = 0;
        this.yd = 0;
        this.zd = 0;

        this.lifetime = FRAME_COUNT * FRAME_DURATION_TICKS;

        this.setSpriteFromAge(sprites);
    }

    @Override
    public void tick() {
        super.tick();

        int currentFrame = age / FRAME_DURATION_TICKS;
        this.setSprite(sprites.get(currentFrame, FRAME_COUNT));
    }


    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    @Override
    public int getLightColor(float partialTicks) {
        return 15728880;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level,
                                       double x, double y, double z,
                                       double xSpeed, double ySpeed, double zSpeed) {
            return new ExplosiveShotMagnetizedSigParticles(level, x, y, z, this.spriteSet, xSpeed, ySpeed, zSpeed);
        }
    }
}
