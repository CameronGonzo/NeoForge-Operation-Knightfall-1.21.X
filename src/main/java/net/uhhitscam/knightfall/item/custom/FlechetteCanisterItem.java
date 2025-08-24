package net.uhhitscam.knightfall.item.custom;

import net.minecraft.world.item.Item;

public class FlechetteCanisterItem extends Item {
    private final AmmoType ammoType;

    public FlechetteCanisterItem(Properties pProperties, AmmoType ammoType) {
        super(pProperties);
        this.ammoType = ammoType;
    }

    public AmmoType getAmmoType() {
        return ammoType;
    }
}