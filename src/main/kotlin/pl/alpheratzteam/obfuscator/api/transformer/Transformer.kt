package pl.alpheratzteam.obfuscator.api.transformer

import pl.alpheratzteam.obfuscator.Obfuscator

/**
 * @author Unix
 * @since 16.12.2020
 */

interface Transformer {

    /**
     * This method transform some classes by obfuscator.
     * @param obfuscator as instance of obfuscator.
     * @see [ClassNode] class to transform.
     */
    fun transform(obfuscator: Obfuscator)

}