package net.uhhitscam.knightfall.component;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record RemoteDetonatorState(long removalGameTime) {
    public static final Codec<RemoteDetonatorState> CODEC = Codec.LONG.xmap(
            RemoteDetonatorState::new,
            RemoteDetonatorState::removalGameTime
    );
    public static final StreamCodec<ByteBuf, RemoteDetonatorState> STREAM_CODEC = ByteBufCodecs.VAR_LONG.map(
            RemoteDetonatorState::new,
            RemoteDetonatorState::removalGameTime
    );
}
