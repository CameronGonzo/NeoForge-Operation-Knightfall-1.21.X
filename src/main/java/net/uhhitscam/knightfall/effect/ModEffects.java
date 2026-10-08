package net.uhhitscam.knightfall.effect;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.effect.custom.StunEffect;
import net.uhhitscam.knightfall.effect.custom.LightningShockEffect;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, OperationKnightfall.MODID);

    public static final Holder<MobEffect> STUN_EFFECT = MOB_EFFECTS.register("stun",
            () -> new StunEffect(MobEffectCategory.HARMFUL, 0x36ebab));

    public static final Holder<MobEffect> LIGHTNING_SHOCK_EFFECT = MOB_EFFECTS.register("lightning_shock",
            () -> new LightningShockEffect(MobEffectCategory.HARMFUL, 0x82DCFF));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
