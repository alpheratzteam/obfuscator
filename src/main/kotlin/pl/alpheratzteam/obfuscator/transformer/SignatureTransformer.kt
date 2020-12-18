package pl.alpheratzteam.obfuscator.transformer

import pl.alpheratzteam.obfuscator.Obfuscator
import pl.alpheratzteam.obfuscator.api.transformer.Transformer
import pl.alpheratzteam.obfuscator.util.StringUtil

/**
 * @author Unix
 * @since 16.12.2020
 */

class SignatureTransformer : Transformer {
    override fun transform(obfuscator: Obfuscator) = obfuscator.classes.forEach { it.value.signature = StringUtil.makeUnreadable(StringUtil.generateString(4)) }
}