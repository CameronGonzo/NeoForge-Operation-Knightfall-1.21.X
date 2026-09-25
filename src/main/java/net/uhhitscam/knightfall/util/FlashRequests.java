package net.uhhitscam.knightfall.util;

import java.util.concurrent.ConcurrentLinkedQueue;

public final class FlashRequests {
    private static final ConcurrentLinkedQueue<Request> QUEUE = new ConcurrentLinkedQueue<>();

    private FlashRequests() {
    }

    public static void enqueue(Request request) {
        QUEUE.add(request);
    }

    public static Request poll() {
        return QUEUE.poll();
    }

    public record Request(int fullWhiteTicks, int fadeOutTicks) {
    }
}
