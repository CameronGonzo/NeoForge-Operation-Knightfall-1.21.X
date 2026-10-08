package net.uhhitscam.knightfall.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

public class IonSmokeParticles extends SingleQuadParticle {
    private static final int FRAME_COUNT = 12;
    private static final float SIZE = 2.0F;
    private static final float FINAL_ALPHA = 0.4F;

    private final SpriteSet sprites;

    protected IonSmokeParticles(ClientLevel level, double x, double y, double z, SpriteSet sprites) {
        super(level, x, y, z, sprites.get(0, 1));
        this.sprites = sprites;
        this.hasPhysics = false;
        this.lifetime = FRAME_COUNT;
        this.quadSize = SIZE;
        this.alpha = 1.0F;
        this.setSprite(sprites.get(0, FRAME_COUNT - 1));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.removed || this.age >= FRAME_COUNT) {
            this.remove();
            return;
        }
        int frame = Math.min(this.age, FRAME_COUNT - 1);
        this.setSprite(sprites.get(frame, FRAME_COUNT - 1));
        this.alpha = 1.0F + (FINAL_ALPHA - 1.0F) * frame / (FRAME_COUNT - 1);
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

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level,
                                       double x, double y, double z,
                                       double xSpeed, double ySpeed, double zSpeed,
                                       RandomSource random) {
            return new IonSmokeParticles(level, x, y, z, sprites);
        }
    }
}
