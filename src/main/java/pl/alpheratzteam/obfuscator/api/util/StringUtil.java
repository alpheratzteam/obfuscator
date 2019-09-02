package pl.alpheratzteam.obfuscator.api.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Unix on 01.09.2019.
 */
public final class StringUtil {

    private static final char[]      chars = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
    private static final Set<String> strings = new HashSet<>();

    @Contract(pure = true)
    private StringUtil() {
    }

    public static String generateString(int length) {
        return IntStream.range(0, length)
                .mapToObj(i -> Character.toString(chars[RandomUtil.nextInt(chars.length)]))
                .collect(Collectors.joining());
    }

    public static String generateUniqueString(int length) {
        final String string = generateString(length);

        if (!strings.contains(string)) {
            return string;
        }

        return generateUniqueString(length);
    }

    @NotNull
    public static String makeUnreadable(@NotNull String input) {
        final StringBuilder builder = new StringBuilder();

        for (char c : input.toCharArray()) {
            builder.append((char) (c + '\u7159'));
        }

        return builder.toString();
    }
}