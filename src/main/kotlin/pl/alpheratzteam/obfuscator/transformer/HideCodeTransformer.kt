package pl.alpheratzteam.obfuscator.transformer

import org.objectweb.asm.Opcodes.*
import pl.alpheratzteam.obfuscator.Obfuscator
import pl.alpheratzteam.obfuscator.api.transformer.Transformer
import pl.alpheratzteam.obfuscator.util.ASMUtil
import java.util.Objects

/**
 * @author Unix
 * @since 18.12.2020
 */

class HideCodeTransformer : Transformer {
    override fun transform(obfuscator: Obfuscator) {
        obfuscator.classes.forEach {
            val classNode = it.value
            when { !(ASMUtil.isSynthetic(classNode.access) && Objects.isNull(classNode.visibleAnnotations)) -> classNode.access = classNode.access or ACC_SYNTHETIC }

            classNode.methods.forEach {
                when { !ASMUtil.isSynthetic(it.access) -> it.access = it.access or ACC_SYNTHETIC }
                when { !it.name.startsWith("<") && ASMUtil.isBridge(it.access) -> it.access = it.access or ACC_BRIDGE }
            }
            classNode.fields.forEach { when { !ASMUtil.isSynthetic(it.access) -> it.access = it.access or ACC_SYNTHETIC } }
        }
    }
}