package net.uhhitscam.knightfall.component;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.StreamCodec;

import java.util.UUID;

public record GrenadeRemoteLink(UUID id) {
    public static final Codec<GrenadeRemoteLink> CODEC = UUIDUtil.CODEC.xmap(
            GrenadeRemoteLink::new,
            GrenadeRemoteLink::id
    );
    public static final StreamCodec<ByteBuf, GrenadeRemoteLink> STREAM_CODEC = UUIDUtil.STREAM_CODEC.map(
            GrenadeRemoteLink::new,
            GrenadeRemoteLink::id
    );

    public static GrenadeRemoteLink create() {
        return new GrenadeRemoteLink(UUID.randomUUID());
    }
}
