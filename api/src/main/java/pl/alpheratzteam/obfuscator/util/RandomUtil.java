package pl.alpheratzteam.obfuscator.util;

import java.util.Random;

/**
 * @author Unix
 * @since 21.03.2020
 */

public final class RandomUtil
{
    private static final Random random = new Random();

    private RandomUtil() {
    }

    public static int nextInt(int max) throws IllegalArgumentException {
        return random.nextInt(max);
    }

    public static int nextInt() {
        return random.nextInt();
    }

    public static int nextInt(int min, int max) throws IllegalArgumentException {
        return random.nextInt(max - min + 1) + min;
    }

    public static boolean hasChance(double chance) {
        return Math.random() * 100.0 <= chance;
    }

    public static boolean nextBoolean() {
        return random.nextInt() == 0;
    }
}