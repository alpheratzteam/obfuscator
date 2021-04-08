package pl.alpheratzteam.obfuscator.transformer.optimizer

import org.objectweb.asm.Opcodes.ACC_FINAL
import pl.alpheratzteam.obfuscator.Obfuscator
import pl.alpheratzteam.obfuscator.api.transformer.Transformer
import pl.alpheratzteam.obfuscator.util.ASMUtil

/**
 * @author Unix
 * @since 07.04.2021
 */

class FieldAccessTransformer : Transformer {

    override fun transform(obfuscator: Obfuscator) {
        obfuscator.classes.values.stream()
            .flatMap { it.fields.stream() }
            .filter { ASMUtil.isFinal(it.access) }
            .forEach { it.access -= ACC_FINAL }
    }

}