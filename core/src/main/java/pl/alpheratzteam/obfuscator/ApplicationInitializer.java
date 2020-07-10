package pl.alpheratzteam.obfuscator;

/**
 * @author Unix
 * @since 21.03.2020
 */

public class ApplicationInitializer
{
    public static void main(String... args) {
        new Thread(new ObfuscatorImpl()::onStart, "AlpheratzObfuscator")
            .start();
    }
}