package com.xy.snoweffect.util;

import java.util.Random;

/**
 * Created by Xavier Yin on 4/13/17.
 */

public class RandomTool {
    private static Random random = new Random();

    public static void setSeed(long seed) {
        random.setSeed(seed);
    }

    public static float floatStandard() {
        return random.nextFloat();
    }

    public static float floatInRange(float left, float right) {
        float leftRange = left - right;
        float rightRange = left + right;

        return leftRange + (rightRange - leftRange) * random.nextFloat();
    }

    public static double positiveGaussian() {
        return Math.abs(random.nextGaussian());
    }
}
