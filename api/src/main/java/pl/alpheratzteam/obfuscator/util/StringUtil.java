package pl.alpheratzteam.obfuscator.util;

import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Unix
 * @since 21.03.2020
 */

public final class StringUtil
{
    private static final char[] chars = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();

    private StringUtil() {
    }

    public static String generateString(int length) {
        return IntStream.range(0, length)
                .mapToObj(i -> Character.toString(chars[RandomUtil.nextInt(chars.length)]))
                .collect(Collectors.joining());
    }

    @NotNull
    public static String makeUnreadable(@NotNull String string) {
        final StringBuilder stringBuilder = new StringBuilder();

        for (char c : string.toCharArray()) {
            stringBuilder.append((char) (c + '\u7159'));
        }

        return stringBuilder.toString();
    }
}