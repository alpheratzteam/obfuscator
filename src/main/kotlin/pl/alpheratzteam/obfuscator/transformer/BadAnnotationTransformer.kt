package pl.alpheratzteam.obfuscator.transformer

import org.objectweb.asm.tree.AnnotationNode
import pl.alpheratzteam.obfuscator.Obfuscator
import pl.alpheratzteam.obfuscator.api.transformer.Transformer
import pl.alpheratzteam.obfuscator.util.StringUtil
import java.util.Objects

/**
 * @author Unix
 * @since 17.12.2020
 */

class BadAnnotationTransformer : Transformer {

    private val annotations: MutableList<AnnotationNode> = mutableListOf()

    init {
        val string = StringUtil.generateString(Short.MAX_VALUE.toInt())
        repeat((0..10).count()) { annotations.add(AnnotationNode("@$string")) }
    }

    override fun transform(obfuscator: Obfuscator) {
        obfuscator.classes.forEach {
            val classNode = it.value

            when { Objects.isNull(classNode.visibleAnnotations) -> classNode.visibleAnnotations = mutableListOf() }
            when { Objects.isNull(classNode.invisibleAnnotations) -> classNode.invisibleAnnotations = mutableListOf() }

            classNode.run {
                visibleAnnotations.addAll(annotations)
                invisibleAnnotations.addAll(annotations)
            }

            classNode.methods.forEach {
                when { Objects.isNull(it.visibleAnnotations) -> it.visibleAnnotations = mutableListOf() }
                when { Objects.isNull(it.invisibleAnnotations) -> it.invisibleAnnotations = mutableListOf() }

                it.run {
                    visibleAnnotations.addAll(annotations)
                    invisibleAnnotations.addAll(annotations)
                }
            }

            classNode.fields.forEach {
                when { Objects.isNull(it.visibleAnnotations) -> it.visibleAnnotations = mutableListOf() }
                when { Objects.isNull(it.invisibleAnnotations) -> it.invisibleAnnotations = mutableListOf() }

                it.run {
                    visibleAnnotations.addAll(annotations)
                    invisibleAnnotations.addAll(annotations)
                }
            }
        }
    }
}