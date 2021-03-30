package pl.alpheratzteam.obfuscator.transformer

import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.tree.LabelNode
import org.objectweb.asm.tree.MethodInsnNode
import pl.alpheratzteam.obfuscator.Obfuscator
import pl.alpheratzteam.obfuscator.api.transformer.Transformer
import pl.alpheratzteam.obfuscator.util.StringUtil
import pl.alpheratzteam.obfuscator.util.insnBuilder
import pl.alpheratzteam.obfuscator.util.RandomUtil

/**
 * @author Unix
 * @since 18.12.2020
 */

class TrashCodeTransformer : Transformer {

    override fun transform(obfuscator: Obfuscator) {
        obfuscator.classes.values.forEach {
            it.methods.filter { !it.name.startsWith("<") }.forEach {
                val methodNode = it
                it.instructions.forEach {
                    if (!(it is MethodInsnNode)) {
                        return@forEach
                    }

                    val methodInsnNode = it
                    if (methodInsnNode.owner.startsWith("\u0000")) {
                        return@forEach
                    }

                    val insnList = insnBuilder {
                        +LabelNode()
                        val string = StringUtil.generateString(2)
                        ldc(string)
                        ldc(string + StringUtil.generateString(2))
                        invokevirtual("java/lang/String", "equals", "(Ljava/lang/Object;)Z", false)
                        val labelNode = LabelNode()
                        ifeq(labelNode)
                        val labelNode1 = LabelNode()
                        +labelNode1
                        // start
                        // todo make switch with shit code.
                        when(0) {
                            0 -> {
                                val size = RandomUtil.int(1, 30)
                                val stringBuilder = StringBuilder()
                                stringBuilder.append("(")
                                repeat((1..size).count()) { stringBuilder.append("Ljava/lang/String;") }
                                stringBuilder.append(")V")

                                for (i in 1..6) {
                                    +LabelNode()
                                    val className = '\u0000' + StringUtil.getStringWithJavaKeywords(6)
                                    new(className)
                                    repeat((1..size).count()) { ldc(StringUtil.generateString(6)) }

                                    invokespecial(
                                        className,
                                        "<init>",
                                        stringBuilder.toString(),
                                        false
                                    )
                                    +LabelNode()
                                }
                            }
                        }
                        // shit code
                        // end
                        +labelNode
                        frame(F_SAME, 0, null, 0, null)
                        +LabelNode()
                    }

                    methodNode.instructions.insertBefore(methodInsnNode.next, insnList)
                }
            }
        }
    }

}