package net.uhhitscam.knightfall.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.util.Mth;

public class IonFlashParticles extends SingleQuadParticle {
    private static final int DURATION_TICKS = 4;

    protected IonFlashParticles(ClientLevel level, double x, double y, double z, SpriteSet sprites, float size) {
        super(level, x, y, z, sprites.get(0, 1));
        this.hasPhysics = false;
        this.lifetime = DURATION_TICKS;
        this.quadSize = size;
        this.alpha = 0.6F;
    }

    @Override
    public void tick() {
        super.tick();
        this.alpha = 0.6F * (1.0F - (float) this.age / this.lifetime);
    }

    @Override
    public float getQuadSize(float partialTick) {
        float progress = Mth.clamp((this.age + partialTick) / this.lifetime, 0.0F, 1.0F);
        return this.quadSize * Mth.sin(progress * Mth.PI);
    }

    @Override
    protected Layer getLayer() {
        return Layer.TRANSLUCENT;
    }

    @Override
    public int getLightCoords(float partialTick) {
        return 15728880;
    }

    public static class Provider implements ParticleProvider<IonFlashParticleOption> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(
                IonFlashParticleOption option,
                ClientLevel level,
                double x,
                double y,
                double z,
                double xSpeed,
                double ySpeed,
                double zSpeed,
                net.minecraft.util.RandomSource random
        ) {
            return new IonFlashParticles(level, x, y, z, sprites, option.size());
        }
    }
}
