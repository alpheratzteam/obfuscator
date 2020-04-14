package pl.alpheratzteam.obfuscator;

/**
 * @author Unix
 * @since 21.03.2020
 */

public class ApplicationInitializer
{
    public static void main(String... args) {
        final Obfuscator obfuscator = new ObfuscatorImpl();
        final Thread thread = new Thread(obfuscator::onStart);

        thread.setName("AlpheratzObfuscator");
        thread.start();
    }
}