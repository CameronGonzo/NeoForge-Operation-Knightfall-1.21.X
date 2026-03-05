package net.uhhitscam.knightfall.util;

public class ThermalVisionUtil {
    private static boolean thermalActive = false;

    public static boolean isThermalActive() {
        return thermalActive;
    }

    public static void setThermalActive(boolean active) {
        thermalActive = active;
    }
}