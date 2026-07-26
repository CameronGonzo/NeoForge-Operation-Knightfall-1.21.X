package net.uhhitscam.knightfall.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record OverheatData(int heat, long nextCoolingTick) {
    public static final Codec<OverheatData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("heat").forGetter(OverheatData::heat),
                    Codec.LONG.fieldOf("next_cooling_tick").forGetter(OverheatData::nextCoolingTick)
            ).apply(instance, OverheatData::new));

    public OverheatData {
        heat = Math.max(0, heat);
    }
}
