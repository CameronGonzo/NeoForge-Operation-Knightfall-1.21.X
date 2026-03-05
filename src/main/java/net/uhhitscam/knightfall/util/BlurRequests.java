package net.uhhitscam.knightfall.util;

import net.minecraft.world.phys.Vec3;

import java.util.concurrent.ConcurrentLinkedQueue;

public final class BlurRequests {
    private static final ConcurrentLinkedQueue<Request> QUEUE = new ConcurrentLinkedQueue<>();

    private BlurRequests() {}

    public static void enqueue(Request request) {
        QUEUE.add(request);
    }

    public static Request poll() {
        return QUEUE.poll();
    }

    public record Request(Vec3 impactPos,
                          float effectRadiusBlocks,
                          int holdTicks,
                          int fadeOutTicks,
                          float maxShaderRadius) {}
}