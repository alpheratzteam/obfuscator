package pl.alpheratzteam.obfuscator.transformer

import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.tree.LabelNode
import org.objectweb.asm.tree.MethodInsnNode
import pl.alpheratzteam.obfuscator.Obfuscator
import pl.alpheratzteam.obfuscator.api.transformer.Transformer
import pl.alpheratzteam.obfuscator.util.StringUtil
import pl.alpheratzteam.obfuscator.util.insnBuilder
import pl.alpheratzteam.obfuscator.util.RandomUtil
import java.util.concurrent.atomic.AtomicInteger

/**
 * @author Unix
 * @since 18.12.2020
 */

class TrashCodeTransformer : Transformer {

    override fun transform(obfuscator: Obfuscator) {
        obfuscator.classes.values.forEach {
            it.methods.filter { !it.name.startsWith("<") }.forEach { methodNode ->
                methodNode.instructions.filter { it is MethodInsnNode }.map { it as MethodInsnNode }
                    .filter { !it.owner.startsWith("\u0000") }.forEach {
                    methodNode.instructions.insertBefore(it.next, insnBuilder {
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
                        when(RandomUtil.int(1, 3)) {
                            1 -> {
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
                            2 -> {
                                val list = mutableListOf<Pair<LabelNode, Int>>()
                                repeat((0..RandomUtil.int(10, 30)).count()) { list.add(Pair(LabelNode(), it))}
                                val label3 = LabelNode()

                                val labels = mutableListOf<LabelNode>()
                                val ints = mutableListOf<Int>()
                                list.forEach {
                                    labels.add(it.first)
                                    ints.add(it.second)
                                }
                                labels.add(label3)
                                ints.add(100)

                                +LabelNode()
                                ldc(RandomUtil.int(10, 2000))
                                ldc(RandomUtil.int(10, 2000))
                                ixor()
                                lookupswitch(label3, Pair(ints.toIntArray(), labels.toTypedArray()))
                                +label3
                                frame(F_SAME, 0, null, 0, null)
                                goto(label3)

                                labels.remove(label3)
                                labels.forEach {
                                    +it
                                    frame(F_SAME, 0, null, 0, null)
                                    when(RandomUtil.int(1, 5)) {
                                        1 -> {
                                            new("java/lang/NullPointerException")
                                            dup()
                                            ldc("AlpheratzObfuscator")
                                            invokespecial(
                                                "java/lang/NullPointerException",
                                                "<init>",
                                                "(Ljava/lang/String;)V",
                                                false
                                            )
                                            athrow()
                                        }
                                        2 -> goto(label3)
                                        3 -> {
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
                                        4 -> goto(label3)
                                    }
                                }

                                +LabelNode()
                            }
                        }
                        // shit code
                        // end
                        +labelNode
                        frame(F_SAME, 0, null, 0, null)
//                        +LabelNode()
                    })
                }
            }
        }
    }

}