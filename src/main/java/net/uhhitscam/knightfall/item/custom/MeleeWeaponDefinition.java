package net.uhhitscam.knightfall.item.custom;

import net.minecraft.world.item.Item;

import java.util.Objects;

public record MeleeWeaponDefinition(
        String registryName,
        Item.Properties itemProperties,
        MeleeWeaponAudioProfile audio,
        MeleeHitEffect hitEffect,
        boolean consumesOnSuccessfulHit
) {
    public static Builder builder(String registryName) {
        return new Builder(registryName);
    }

    public static final class Builder {
        private final String registryName;
        private Item.Properties itemProperties = new Item.Properties().stacksTo(1);
        private MeleeWeaponAudioProfile audio;
        private MeleeHitEffect hitEffect;
        private boolean consumesOnSuccessfulHit;

        private Builder(String registryName) {
            if (registryName == null || registryName.isBlank()) {
                throw new IllegalArgumentException("Melee weapon registry name cannot be blank.");
            }
            this.registryName = registryName;
        }

        public Builder itemProperties(Item.Properties itemProperties) {
            this.itemProperties = Objects.requireNonNull(itemProperties, "Melee weapon item properties cannot be null.");
            return this;
        }

        public Builder audio(MeleeWeaponAudioProfile audio) {
            this.audio = Objects.requireNonNull(audio, "Melee weapon audio profile cannot be null.");
            return this;
        }

        public Builder hitEffect(MeleeHitEffect hitEffect) {
            this.hitEffect = Objects.requireNonNull(hitEffect, "Melee weapon hit effect cannot be null.");
            return this;
        }

        public Builder consumesOnSuccessfulHit() {
            this.consumesOnSuccessfulHit = true;
            return this;
        }

        public MeleeWeaponDefinition build() {
            if (audio == null) {
                throw new IllegalStateException(registryName + " must have a melee weapon audio profile.");
            }
            if (hitEffect == null) {
                throw new IllegalStateException(registryName + " must have a melee hit effect.");
            }
            return new MeleeWeaponDefinition(
                    registryName,
                    itemProperties,
                    audio,
                    hitEffect,
                    consumesOnSuccessfulHit
            );
        }
    }
}
