package net.uhhitscam.knightfall.item.custom;

import java.util.List;

public enum AmmoType {
    TIBANNA,
    IONIZED_TIBANNA,
    TIBANNAX,
    SPIN_SEALED_TIBANNA,
    MAGNETIZED_SIG,
    SIG,
    SKEVON,
    PLASTIC_SLUG,
    CERAMIC_SLUG,
    STEEL_SLUG,
    RAZOR_STEEL_SLUG,
    FLECHETTE_CAN,
    FLECHETTE_TOXIC_CAN,
    FLECHETTE_SPREAD_CAN,
    FLECHETTE_TOXIC_SPREAD_CAN;

    public static List<AmmoType> getGasTypes() {
        return List.of(TIBANNA, IONIZED_TIBANNA, TIBANNAX, SPIN_SEALED_TIBANNA, MAGNETIZED_SIG, SIG, SKEVON);
    }

    public static List<AmmoType> getSlugTypes() {
        return List.of(PLASTIC_SLUG, CERAMIC_SLUG, STEEL_SLUG, RAZOR_STEEL_SLUG);
    }

    public static List<AmmoType> getFlechetteTypes() {
        return List.of(FLECHETTE_CAN, FLECHETTE_TOXIC_CAN, FLECHETTE_SPREAD_CAN, FLECHETTE_TOXIC_SPREAD_CAN);
    }
}