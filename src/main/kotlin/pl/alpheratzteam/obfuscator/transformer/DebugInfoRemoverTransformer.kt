package pl.alpheratzteam.obfuscator.transformer

import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.tree.ClassNode
import pl.alpheratzteam.obfuscator.Obfuscator
import pl.alpheratzteam.obfuscator.api.transformer.Transformer

/**
 * @author Unix
 * @since 18.12.2020
 */

class DebugInfoRemoverTransformer : Transformer {

    override fun transform(obfuscator: Obfuscator) {
        val classes = mutableMapOf<String, ClassNode>()
        obfuscator.classes.values.forEach {
            val classWriter = ClassWriter(ClassWriter.COMPUTE_MAXS)
            it.accept(classWriter)

            val newClassNode = ClassNode()
            ClassReader(classWriter.toByteArray()).accept(newClassNode, ClassReader.SKIP_DEBUG)
            classes[newClassNode.name] = newClassNode
        }

        obfuscator.classes.clear()
        obfuscator.classes.putAll(classes)
    }

}