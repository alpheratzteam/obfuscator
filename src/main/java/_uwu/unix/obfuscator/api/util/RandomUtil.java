package _uwu.unix.obfuscator.api.util;

import org.jetbrains.annotations.Contract;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Unix on 01.09.2019.
 */
public final class RandomUtil {

    private static Random random = ThreadLocalRandom.current();

    @Contract(pure = true)
    private RandomUtil() {
    }

    @Contract(pure = true)
    public static Random getRandom() {
        return random;
    }

    public static int nextInt() {
        return random.nextInt();
    }

    public static int nextInt(int bound) {
        return random.nextInt(bound);
    }
}