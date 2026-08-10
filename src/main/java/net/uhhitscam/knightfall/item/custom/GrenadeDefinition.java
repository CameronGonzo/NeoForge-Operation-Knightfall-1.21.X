package net.uhhitscam.knightfall.item.custom;

import net.minecraft.world.item.Item;

import java.util.Objects;

public record GrenadeDefinition(
        String registryName,
        Item.Properties itemProperties,
        int fuseTicks,
        GrenadeTrigger trigger,
        float throwVelocity,
        float minimumThrowVelocity,
        int throwChargeTicks,
        float throwInaccuracy,
        int cooldownTicks,
        GrenadePhysics physics,
        GrenadeAudioProfile audio,
        GrenadeEffect effect
) {
    public static Builder builder(String registryName) {
        return new Builder(registryName);
    }

    public static final class Builder {
        private final String registryName;

        private Item.Properties itemProperties = new Item.Properties().stacksTo(16);
        private int fuseTicks = -1;
        private GrenadeTrigger trigger = GrenadeTrigger.FUSE;
        private float throwVelocity = 1.5F;
        private Float minimumThrowVelocity;
        private int throwChargeTicks = 10;
        private float throwInaccuracy = 1.0F;
        private int cooldownTicks;
        private GrenadePhysics physics = GrenadePhysics.defaults();
        private GrenadeAudioProfile audio;
        private GrenadeEffect effect;

        private Builder(String registryName) {
            if (registryName == null || registryName.isBlank()) {
                throw new IllegalArgumentException("Grenade registryName cannot be blank.");
            }
            this.registryName = registryName;
        }

        public Builder itemProperties(Item.Properties itemProperties) {
            this.itemProperties = Objects.requireNonNull(itemProperties, "Grenade item properties cannot be null.");
            return this;
        }

        public Builder fuseTicks(int fuseTicks) {
            this.fuseTicks = fuseTicks;
            return this;
        }

        public Builder trigger(GrenadeTrigger trigger) {
            this.trigger = Objects.requireNonNull(trigger, "Grenade trigger cannot be null.");
            return this;
        }

        public Builder throwVelocity(float throwVelocity) {
            this.throwVelocity = throwVelocity;
            return this;
        }

        public Builder minimumThrowVelocity(float minimumThrowVelocity) {
            this.minimumThrowVelocity = minimumThrowVelocity;
            return this;
        }

        public Builder throwChargeTicks(int throwChargeTicks) {
            this.throwChargeTicks = throwChargeTicks;
            return this;
        }

        public Builder throwInaccuracy(float throwInaccuracy) {
            this.throwInaccuracy = throwInaccuracy;
            return this;
        }

        public Builder cooldownTicks(int cooldownTicks) {
            this.cooldownTicks = cooldownTicks;
            return this;
        }

        public Builder physics(GrenadePhysics physics) {
            this.physics = Objects.requireNonNull(physics, "Grenade physics cannot be null.");
            return this;
        }

        public Builder audio(GrenadeAudioProfile audio) {
            this.audio = Objects.requireNonNull(audio, "Grenade audio profile cannot be null.");
            return this;
        }

        public Builder effect(GrenadeEffect effect) {
            this.effect = Objects.requireNonNull(effect, "Grenade effect cannot be null.");
            return this;
        }

        public GrenadeDefinition build() {
            if (fuseTicks <= 0) {
                throw new IllegalStateException(registryName + " must have fuseTicks greater than 0.");
            }
            if (throwVelocity <= 0.0F) {
                throw new IllegalStateException(registryName + " must have throwVelocity greater than 0.");
            }
            float resolvedMinimumThrowVelocity = minimumThrowVelocity != null
                    ? minimumThrowVelocity
                    : throwVelocity * 0.4F;
            if (resolvedMinimumThrowVelocity <= 0.0F) {
                throw new IllegalStateException(registryName + " must have minimumThrowVelocity greater than 0.");
            }
            if (resolvedMinimumThrowVelocity > throwVelocity) {
                throw new IllegalStateException(registryName + " minimumThrowVelocity cannot exceed throwVelocity.");
            }
            if (throwChargeTicks <= 0) {
                throw new IllegalStateException(registryName + " must have throwChargeTicks greater than 0.");
            }
            if (throwInaccuracy < 0.0F) {
                throw new IllegalStateException(registryName + " cannot have negative throwInaccuracy.");
            }
            if (cooldownTicks < 0) {
                throw new IllegalStateException(registryName + " cannot have a negative cooldown.");
            }
            if (audio == null) {
                throw new IllegalStateException(registryName + " must have a GrenadeAudioProfile.");
            }
            if (audio.urgentThresholdTicks() > fuseTicks) {
                throw new IllegalStateException(registryName + " urgent beep threshold cannot exceed its fuse.");
            }
            if (effect == null) {
                throw new IllegalStateException(registryName + " must have a GrenadeEffect.");
            }

            return new GrenadeDefinition(
                    registryName,
                    itemProperties,
                    fuseTicks,
                    trigger,
                    throwVelocity,
                    resolvedMinimumThrowVelocity,
                    throwChargeTicks,
                    throwInaccuracy,
                    cooldownTicks,
                    physics,
                    audio,
                    effect
            );
        }
    }

    public float throwVelocity(int useTicks) {
        float charge = Math.max(0.0F, Math.min(1.0F, (float) useTicks / throwChargeTicks));
        return minimumThrowVelocity + (throwVelocity - minimumThrowVelocity) * charge;
    }
}
