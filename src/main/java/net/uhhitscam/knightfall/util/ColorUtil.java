package net.uhhitscam.knightfall.util;

public final class ColorUtil {
    private ColorUtil() {
    }

    /**
     * Packs red, green, and blue components into Minecraft's 24-bit RGB format.
     */
    public static int rgb(int red, int green, int blue) {
        return component(red, "Red") << 16
                | component(green, "Green") << 8
                | component(blue, "Blue");
    }

    /**
     * Packs alpha, red, green, and blue components into Minecraft's 32-bit ARGB format.
     */
    public static int argb(int alpha, int red, int green, int blue) {
        return component(alpha, "Alpha") << 24 | rgb(red, green, blue);
    }

    /**
     * Adds an alpha component to an existing 24-bit RGB color.
     */
    public static int argb(int alpha, int rgb) {
        if ((rgb & 0xFF000000) != 0) {
            throw new IllegalArgumentException("RGB color must be a 24-bit value.");
        }
        return component(alpha, "Alpha") << 24 | rgb;
    }

    private static int component(int value, String name) {
        if (value < 0 || value > 255) {
            throw new IllegalArgumentException(name + " color component must be between 0 and 255.");
        }
        return value;
    }
}
