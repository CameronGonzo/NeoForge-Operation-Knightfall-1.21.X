package net.uhhitscam.knightfall.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

public class SmokeScreenParticles extends SingleQuadParticle {
    private final SpriteSet sprites;
    private final float startingAlpha;

    protected SmokeScreenParticles(
            ClientLevel level,
            double x,
            double y,
            double z,
            SpriteSet sprites,
            double xSpeed,
            double ySpeed,
            double zSpeed
    ) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed, sprites.get(0, 1));
        this.sprites = sprites;
        this.hasPhysics = false;
        this.friction = 0.96F;
        this.gravity = -0.015F;
        this.lifetime = 26 + random.nextInt(15);
        this.quadSize = 1.2F + random.nextFloat() * 0.75F;
        this.startingAlpha = 0.95F + random.nextFloat() * 0.05F;
        this.alpha = startingAlpha;

        float shade = 0.18F + random.nextFloat() * 0.10F;
        setColor(shade * 0.95F, shade, shade * 1.02F);
        setSpriteFromAge(sprites);
        xd = xSpeed;
        yd = ySpeed;
        zd = zSpeed;
    }

    @Override
    public void tick() {
        super.tick();
        if (removed) {
            return;
        }

        setSpriteFromAge(sprites);
        float fade = Mth.clamp((lifetime - age) / 2.0F, 0.0F, 1.0F);
        alpha = startingAlpha * fade;
    }

    @Override
    public float getQuadSize(float partialTick) {
        float ageProgress = Math.min(1.0F, (age + partialTick) / lifetime);
        return quadSize * (0.75F + ageProgress * 0.35F);
    }

    @Override
    protected Layer getLayer() {
        return Layer.TRANSLUCENT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
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
                double zSpeed,
                net.minecraft.util.RandomSource random
        ) {
            return new SmokeScreenParticles(level, x, y, z, sprites, xSpeed, ySpeed, zSpeed);
        }
    }
}
