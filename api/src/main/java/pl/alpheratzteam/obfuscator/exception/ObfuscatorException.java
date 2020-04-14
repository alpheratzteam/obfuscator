package pl.alpheratzteam.obfuscator.exception;

/**
 * @author Unix
 * @since 21.03.2020
 */

public class ObfuscatorException extends RuntimeException
{
    public ObfuscatorException() {
    }

    public ObfuscatorException(String message) {
        super(message);
    }

    public ObfuscatorException(Throwable cause) {
        super(cause);
    }
}