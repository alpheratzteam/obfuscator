package pl.alpheratzteam.obfuscator.transformer.optimizer

import org.objectweb.asm.Opcodes.NOP
import pl.alpheratzteam.obfuscator.Obfuscator
import pl.alpheratzteam.obfuscator.api.transformer.Transformer

/**
 * @author Unix
 * @since 18.12.2020
 */

class NopRemoverTransformer : Transformer {
    override fun transform(obfuscator: Obfuscator) = obfuscator.classes.values.forEach { it.methods.forEach { methodNode -> methodNode.instructions.filter { it.opcode == NOP }.forEach { methodNode.instructions.remove(it) } } }
}