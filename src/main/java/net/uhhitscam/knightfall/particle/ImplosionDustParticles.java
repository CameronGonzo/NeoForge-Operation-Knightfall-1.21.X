package net.uhhitscam.knightfall.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class ImplosionDustParticles extends TextureSheetParticle {
    protected ImplosionDustParticles(
            ClientLevel level,
            double x,
            double y,
            double z,
            SpriteSet spriteSet,
            double xSpeed,
            double ySpeed,
            double zSpeed
    ) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);

        this.friction = 1.0F;
        this.gravity = 0.0F;
        this.lifetime = 4 + level.random.nextInt(3);
        this.quadSize = 0.025F + level.random.nextFloat() * 0.015F;
        this.alpha = 0.7F;
        this.hasPhysics = false;

        float shade = 0.28F + level.random.nextFloat() * 0.12F;
        this.setColor(shade * 1.05F, shade, shade * 0.9F);
        this.setSpriteFromAge(spriteSet);

        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(
                SimpleParticleType type,
                ClientLevel level,
                double x,
                double y,
                double z,
                double xSpeed,
                double ySpeed,
                double zSpeed
        ) {
            return new ImplosionDustParticles(
                    level,
                    x,
                    y,
                    z,
                    spriteSet,
                    xSpeed,
                    ySpeed,
                    zSpeed
            );
        }
    }
}
