package net.uhhitscam.knightfall.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class DisintegrationMediumParticles extends TextureSheetParticle {
    protected DisintegrationMediumParticles(ClientLevel level, double x, double y, double z,
                                           SpriteSet spriteSet, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);

        this.friction = 0.9f;
        this.gravity = -0.035f;
        this.lifetime = 22 + this.random.nextInt(8);
        this.quadSize = 0.25f + this.random.nextFloat() * 0.04f;

        this.setSpriteFromAge(spriteSet);

        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;
    }

    @Override
    public float getQuadSize(float scaleFactor) {
        float ageProgress = (this.age + scaleFactor) / this.lifetime;
        return this.quadSize * (1.0F + ageProgress * 0.35F);
    }

    @Override
    public void tick() {
        super.tick();
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
            return new DisintegrationMediumParticles(level, x, y, z, this.spriteSet, xSpeed, ySpeed, zSpeed);
        }
    }
}