package net.uhhitscam.starwars.component;

import net.uhhitscam.starwars.OperationKnightfall;
import net.minecraft.core.component.DataComponentType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.UnaryOperator;

public class ModDataComponentTypes {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.createDataComponents(OperationKnightfall.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<GasAmmoData>> GAS_AMMO = register("gas_ammo",
            builder -> builder.persistent(GasAmmoData.CODEC));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<GasTypeData>> GAS_TYPE = register("gas_type",
            builder -> builder.persistent(GasTypeData.CODEC));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<FiringModeData>> FIRING_MODE = register("firing_mode",
            builder -> builder.persistent(FiringModeData.CODEC));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<CoolDownData>> COOLDOWN = register("cooldown",
            builder -> builder.persistent(CoolDownData.CODEC));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ExtraFiringRateData>> EXTRA_FIRING_RATE = register("extra_firing_rate",
            builder -> builder.persistent(ExtraFiringRateData.CODEC));

    private static <T>DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return DATA_COMPONENT_TYPES.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
    }

    public static void register(IEventBus eventBus) {
        DATA_COMPONENT_TYPES.register(eventBus);
    }
}