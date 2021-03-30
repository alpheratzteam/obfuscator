package pl.alpheratzteam.obfuscator.transformer

import pl.alpheratzteam.obfuscator.Obfuscator
import pl.alpheratzteam.obfuscator.api.transformer.Transformer
import pl.alpheratzteam.obfuscator.util.StringUtil
import java.util.*

/**
 * @author Unix
 * @since 18.12.2020
 */

class LocalVariableTransformer : Transformer {

    override fun transform(obfuscator: Obfuscator) {
        obfuscator.classes.flatMap { it.value.methods }
            .filter { Objects.nonNull(it.localVariables) }
            .flatMap { it.localVariables }
            .forEach { it.name = StringUtil.generateString(16) }
    }

}