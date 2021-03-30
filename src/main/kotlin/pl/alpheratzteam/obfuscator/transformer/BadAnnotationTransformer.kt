package pl.alpheratzteam.obfuscator.transformer

import org.objectweb.asm.tree.AnnotationNode
import pl.alpheratzteam.obfuscator.Obfuscator
import pl.alpheratzteam.obfuscator.api.transformer.Transformer
import pl.alpheratzteam.obfuscator.util.ConditionUtil
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

            classNode.run {
                ConditionUtil.checkCondition(Objects.isNull(visibleAnnotations)) {
                    visibleAnnotations = mutableListOf()
                }

                ConditionUtil.checkCondition(Objects.isNull(invisibleAnnotations)) {
                    invisibleAnnotations = mutableListOf()
                }

                visibleAnnotations.addAll(annotations)
                invisibleAnnotations.addAll(annotations)
            }

            classNode.methods.forEach {
                it.run {
                    ConditionUtil.checkCondition(Objects.isNull(visibleAnnotations)) {
                        visibleAnnotations = mutableListOf()
                    }

                    ConditionUtil.checkCondition(Objects.isNull(invisibleAnnotations)) {
                        invisibleAnnotations = mutableListOf()
                    }

                    visibleAnnotations.addAll(annotations)
                    invisibleAnnotations.addAll(annotations)
                }
            }

            classNode.fields.forEach {
                it.run {
                    ConditionUtil.checkCondition(Objects.isNull(visibleAnnotations)) {
                        visibleAnnotations = mutableListOf()
                    }

                    ConditionUtil.checkCondition(Objects.isNull(invisibleAnnotations)) {
                        invisibleAnnotations = mutableListOf()
                    }

                    visibleAnnotations.addAll(annotations)
                    invisibleAnnotations.addAll(annotations)
                }
            }
        }
    }

}