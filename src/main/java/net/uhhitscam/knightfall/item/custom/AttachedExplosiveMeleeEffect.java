package net.uhhitscam.knightfall.item.custom;

import net.uhhitscam.knightfall.entity.ModEntities;
import net.uhhitscam.knightfall.entity.custom.ExplosiveKnifeEntity;

import java.util.Objects;

public record AttachedExplosiveMeleeEffect(AttachedExplosiveSpec spec) implements MeleeHitEffect {
    public AttachedExplosiveMeleeEffect {
        Objects.requireNonNull(spec, "Attached explosive specification cannot be null.");
    }

    @Override
    public boolean apply(MeleeHitContext context) {
        ExplosiveKnifeEntity explosive = new ExplosiveKnifeEntity(ModEntities.EXPLOSIVE_KNIFE.get(), context.level());
        explosive.attach(
                context.target(),
                context.attacker(),
                context.weaponStack().copyWithCount(1),
                spec
        );
        return context.level().addFreshEntity(explosive);
    }
}
