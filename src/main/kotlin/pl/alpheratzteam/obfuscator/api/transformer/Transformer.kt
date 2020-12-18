package pl.alpheratzteam.obfuscator.api.transformer

import pl.alpheratzteam.obfuscator.Obfuscator

/**
 * @author Unix
 * @since 16.12.2020
 */

interface Transformer {
    fun transform(obfuscator: Obfuscator)
}