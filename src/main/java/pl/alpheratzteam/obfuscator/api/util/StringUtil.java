package pl.alpheratzteam.obfuscator.api.util;

import org.jetbrains.annotations.Contract;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Unix on 01.09.2019.
 */
public final class StringUtil {

    private static final char[] chars = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();

    @Contract(pure = true)
    private StringUtil() {
    }

    public static String generateString(int length) {
        return IntStream.range(0, length)
                .mapToObj(i -> Character.toString(chars[RandomUtil.nextInt(chars.length)]))
                .collect(Collectors.joining());
    }
}