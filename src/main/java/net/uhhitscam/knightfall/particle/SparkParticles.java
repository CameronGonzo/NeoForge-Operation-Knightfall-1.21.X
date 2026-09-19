package net.uhhitscam.knightfall.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class SparkParticles extends SingleQuadParticle {
    protected SparkParticles(ClientLevel level, double x, double y, double z, SpriteSet spriteSet, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet.get(0, 1));
        this.friction = 0.98f; // Slows down over time (closer to block breaking behavior)
        this.lifetime = 5 + level.getRandom().nextInt(10); // Randomized lifespan for variation
        this.setSpriteFromAge(spriteSet);
        // Apply randomization to initial velocity
        this.xd *= 0.5 + level.getRandom().nextDouble() * 0.5;
        this.yd *= 0.5 + level.getRandom().nextDouble() * 0.5;
        this.zd *= 0.5 + level.getRandom().nextDouble() * 0.5;
        // Enable gravity effect
        this.gravity = 0.05f;
        this.quadSize = 0.1f;

//        //recolor particles
//        this.rCol = 1f;
//        this.gCol = 1f;
//        this.bCol = 1f;
    }

    @Override
    public void tick() {
        super.tick();

        // Apply gravity
        this.yd -= this.gravity;

        // If particle touches the ground, reduce velocity and fade out
        if (this.onGround) {
            this.xd *= 0.7; // Reduce horizontal movement
            this.zd *= 0.7;
            this.yd *= -0.1; // Make it bounce slightly
        }
    }

    @Override
    protected SingleQuadParticle.Layer getLayer() {
        return SingleQuadParticle.Layer.OPAQUE;
    }

    @Override
    public int getLightCoords(float partialTicks) {
        return 15728880; // Maximum brightness (light level 15)
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, net.minecraft.util.RandomSource random) {
            return new SparkParticles(clientLevel, pX, pY, pZ, this.spriteSet, pXSpeed, pYSpeed, pZSpeed);
        }
    }
}
