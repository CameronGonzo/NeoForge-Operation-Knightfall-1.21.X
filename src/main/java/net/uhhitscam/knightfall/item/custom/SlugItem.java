package net.uhhitscam.knightfall.item.custom;

import net.minecraft.world.item.Item;

public class SlugItem extends Item {
    private final AmmoType ammoType;

    public SlugItem(Properties pProperties, AmmoType ammoType) {
        super(pProperties);
        this.ammoType = ammoType;
    }

    public AmmoType getAmmoType() {
        return ammoType;
    }
}
