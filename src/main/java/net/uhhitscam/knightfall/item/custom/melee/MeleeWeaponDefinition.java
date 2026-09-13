package net.uhhitscam.knightfall.item.custom.melee;

import net.minecraft.world.item.Item;

import java.util.Objects;

public record MeleeWeaponDefinition(
        String registryName,
        Item.Properties itemProperties,
        MeleeWeaponAudioProfile audio,
        MeleeHitEffect hitEffect,
        boolean consumesOnSuccessfulHit,
        MeleeWeaponForm form,
        MeleeWeaponForm alternateForm,
        MeleeTuning tuning,
        MeleeHitEffect electricEffect,
        MeleeBlasterShot blasterShot
) {
    public static Builder builder(String registryName) {
        return new Builder(registryName);
    }

    public static final class Builder {
        private final String registryName;
        private Item.Properties itemProperties = new Item.Properties().stacksTo(1);
        private MeleeWeaponAudioProfile audio = MeleeWeaponAudioProfile.SILENT;
        private MeleeHitEffect hitEffect = context -> false;
        private boolean consumesOnSuccessfulHit;
        private MeleeWeaponForm form;
        private MeleeWeaponForm alternateForm;
        private MeleeTuning tuning = MeleeTuning.DEFAULT;
        private MeleeHitEffect electricEffect = context -> false;
        private MeleeBlasterShot blasterShot = MeleeBlasterShot.DEFAULT;

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

        public Builder form(MeleeWeaponForm form) { this.form = Objects.requireNonNull(form); return this; }
        public Builder alternateForm(MeleeWeaponForm form) { this.alternateForm = Objects.requireNonNull(form); return this; }
        public Builder tuning(MeleeTuning tuning) { this.tuning = Objects.requireNonNull(tuning); return this; }
        public Builder electricEffect(MeleeHitEffect effect) { this.electricEffect = Objects.requireNonNull(effect); return this; }
        public Builder blasterShot(MeleeBlasterShot shot) { this.blasterShot = Objects.requireNonNull(shot); return this; }

        public MeleeWeaponDefinition build() {
            if (alternateForm != null && form == null) throw new IllegalStateException("Alternate form requires a base form.");
            if (form != null) {
                boolean switches = form.attacks().values().stream().anyMatch(a -> a.trait() == MeleeAttackTrait.SWITCH);
                boolean switchesBack = alternateForm != null && alternateForm.attacks().values().stream()
                        .anyMatch(a -> a.trait() == MeleeAttackTrait.SWITCH);
                if (switches != (alternateForm != null) || alternateForm != null && !switchesBack) {
                    throw new IllegalStateException("Switch requires two forms, both with a SWITCH binding.");
                }
                itemProperties.attributes(form.attributes());
            }
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
                    consumesOnSuccessfulHit, form, alternateForm, tuning, electricEffect, blasterShot
            );
        }
    }
}
