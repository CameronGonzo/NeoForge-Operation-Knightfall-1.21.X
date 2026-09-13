package net.uhhitscam.knightfall.item.custom.melee;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface MeleeLightsaber {
    boolean isBladeActive(ItemStack stack, LivingEntity holder);
    void disableBlade(ItemStack stack, LivingEntity holder, int ticks);
}
