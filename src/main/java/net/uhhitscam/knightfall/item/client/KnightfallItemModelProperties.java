package net.uhhitscam.knightfall.item.client;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterRangeSelectItemModelPropertyEvent;
import org.jspecify.annotations.Nullable;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

@EventBusSubscriber(modid = "knightfall", value = Dist.CLIENT)
public final class KnightfallItemModelProperties {
    private static final Map<Identifier, Map<Item, Value>> VALUES = new HashMap<>();

    private KnightfallItemModelProperties() {}

    public static void register(Item item, Identifier property, Value value) {
        VALUES.computeIfAbsent(property, ignored -> new IdentityHashMap<>()).put(item, value);
    }

    @SubscribeEvent
    public static void registerTypes(RegisterRangeSelectItemModelPropertyEvent event) {
        for (String name : new String[]{"held", "alternate_form", "melee_action", "grenade_state", "activated"}) {
            Identifier id = Identifier.fromNamespaceAndPath("knightfall", name);
            NumericProperty property = new NumericProperty(id);
            event.register(id, property.type());
        }
    }

    @FunctionalInterface
    public interface Value {
        float get(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed);
    }

    private static final class NumericProperty implements RangeSelectItemModelProperty {
        private final Identifier id;
        private final MapCodec<NumericProperty> codec;

        private NumericProperty(Identifier id) {
            this.id = id;
            this.codec = MapCodec.unit(this);
        }

        @Override
        public float get(ItemStack stack, @Nullable ClientLevel level, @Nullable ItemOwner owner, int seed) {
            Value value = VALUES.getOrDefault(id, Map.of()).get(stack.getItem());
            return value == null ? 0 : value.get(stack, level, owner == null ? null : owner.asLivingEntity(), seed);
        }

        @Override
        public MapCodec<NumericProperty> type() { return codec; }
    }
}
