package net.uhhitscam.knightfall.component;

import net.uhhitscam.knightfall.OperationKnightfall;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.UnaryOperator;

public class ModDataComponentTypes {
    public static final DeferredRegister.DataComponents DATA_COMPONENT_TYPES =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, OperationKnightfall.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> MELEE_ALTERNATE_FORM =
            register("melee_alternate_form", builder -> builder.persistent(com.mojang.serialization.Codec.BOOL)
                    .networkSynchronized(net.minecraft.network.codec.ByteBufCodecs.BOOL));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> MELEE_ACTION =
            register("melee_action", builder -> builder.networkSynchronized(net.minecraft.network.codec.ByteBufCodecs.VAR_INT));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<AmmoData>> AMMO = register("ammo",
            builder -> builder.persistent(AmmoData.CODEC));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<AmmoTypeData>> AMMO_TYPE = register("ammo_type",
            builder -> builder.persistent(AmmoTypeData.CODEC));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<FiringModeData>> FIRING_MODE = register("firing_mode",
            builder -> builder.persistent(FiringModeData.CODEC));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<FireCoolDownData>> FIRE_COOLDOWN = register("fire_cooldown",
            builder -> builder.persistent(FireCoolDownData.CODEC));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ReloadNSwitchCoolDownData>> RELOAD_N_SWITCH_COOLDOWN = register("reload_n_switch_cooldown",
            builder -> builder.persistent(ReloadNSwitchCoolDownData.CODEC));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ExtraFiringRateData>> EXTRA_FIRING_RATE = register("extra_firing_rate",
            builder -> builder.persistent(ExtraFiringRateData.CODEC));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<OverheatData>> OVERHEAT = register("overheat",
            builder -> builder.persistent(OverheatData.CODEC));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<GrenadeRemoteLink>> GRENADE_REMOTE_LINK =
            register("grenade_remote_link", builder -> builder
                    .persistent(GrenadeRemoteLink.CODEC)
                    .networkSynchronized(GrenadeRemoteLink.STREAM_CODEC));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<RemoteDetonatorState>> REMOTE_DETONATOR_STATE =
            register("remote_detonator_state", builder -> builder
                    .persistent(RemoteDetonatorState.CODEC)
                    .networkSynchronized(RemoteDetonatorState.STREAM_CODEC));

    private static <T>DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return DATA_COMPONENT_TYPES.registerComponentType(name, builderOperator);
    }

    public static void register(IEventBus eventBus) {
        DATA_COMPONENT_TYPES.register(eventBus);
    }
}
