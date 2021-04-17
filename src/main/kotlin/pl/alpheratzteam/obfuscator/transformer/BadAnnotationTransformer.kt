package pl.alpheratzteam.obfuscator.transformer

import org.objectweb.asm.tree.AnnotationNode
import pl.alpheratzteam.obfuscator.Obfuscator
import pl.alpheratzteam.obfuscator.api.transformer.Transformer
import pl.alpheratzteam.obfuscator.util.RandomUtil
import pl.alpheratzteam.obfuscator.util.StringUtil
import java.util.Objects

/**
 * @author Unix
 * @since 17.12.2020
 */

class BadAnnotationTransformer : Transformer {

    private val annotations: MutableList<AnnotationNode> = mutableListOf()

    init {
        val string = StringUtil.generateString(RandomUtil.int(10, 64))
        repeat((0..RandomUtil.int(1, 10)).count()) { annotations.add(AnnotationNode("L$string;")) }
        repeat((0..RandomUtil.int(1, 10)).count()) { annotations.add(AnnotationNode(string)) }
        repeat((0..RandomUtil.int(1, 10)).count()) { annotations.add(AnnotationNode("@$string")) }
    }

    override fun transform(obfuscator: Obfuscator) {
        obfuscator.classes.values.forEach {
            it.run {
                if (Objects.isNull(visibleAnnotations)) {
                    visibleAnnotations = mutableListOf()
                }

                if (Objects.isNull(invisibleAnnotations)) {
                    invisibleAnnotations = mutableListOf()
                }

                val pair = change(visibleAnnotations, invisibleAnnotations)
                visibleAnnotations = pair.first
                invisibleAnnotations = pair.second
            }

            it.methods.forEach {
                it.run {
                    if (Objects.isNull(visibleAnnotations)) {
                        visibleAnnotations = mutableListOf()
                    }

                    if (Objects.isNull(invisibleAnnotations)) {
                        invisibleAnnotations = mutableListOf()
                    }

                    val pair = change(visibleAnnotations, invisibleAnnotations)
                    visibleAnnotations = pair.first
                    invisibleAnnotations = pair.second
                }
            }

            it.fields.forEach {
                it.run {
                    if (Objects.isNull(visibleAnnotations)) {
                        visibleAnnotations = mutableListOf()
                    }

                    if (Objects.isNull(invisibleAnnotations)) {
                        invisibleAnnotations = mutableListOf()
                    }

                    val pair = change(visibleAnnotations, invisibleAnnotations)
                    visibleAnnotations = pair.first
                    invisibleAnnotations = pair.second
                }
            }
        }
    }

    fun change(visibleAnnotations: MutableList<AnnotationNode>, invisibleAnnotations: MutableList<AnnotationNode>): Pair<List<AnnotationNode>, List<AnnotationNode>> {
        visibleAnnotations.addAll(annotations)
        invisibleAnnotations.addAll(annotations)
        return Pair(visibleAnnotations, invisibleAnnotations)
    }

}