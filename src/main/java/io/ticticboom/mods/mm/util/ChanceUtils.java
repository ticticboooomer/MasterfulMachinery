package io.ticticboom.mods.mm.util;

import java.util.Random;

public class ChanceUtils {
    private static final Random random = new Random();
    public static boolean shouldProceed(double chance) {
        var rnd = random.nextDouble();
        return chance >= rnd;
    }
}
