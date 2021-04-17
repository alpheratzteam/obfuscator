package pl.alpheratzteam.obfuscator.transformer

import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.tree.LabelNode
import org.objectweb.asm.tree.MethodInsnNode
import pl.alpheratzteam.obfuscator.Obfuscator
import pl.alpheratzteam.obfuscator.api.transformer.Transformer
import pl.alpheratzteam.obfuscator.util.StringUtil
import pl.alpheratzteam.obfuscator.util.insnBuilder
import pl.alpheratzteam.obfuscator.util.RandomUtil
import pl.alpheratzteam.obfuscator.util.printlnAsm

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
                        val bool = RandomUtil.boolean()

                        +LabelNode()

                        // TODO: 17.04.2021 code cleanup.

                        val type = RandomUtil.int(1, 3)
                        when (type) {
                            1 -> {
                                val string = StringUtil.generateString(2)
                                ldc(string)
                                when {
                                    bool -> {
                                        ldc(string + StringUtil.generateString(2))
                                    }
                                    else -> {
                                        ldc(string)
                                    }
                                }
                                invokevirtual("java/lang/String", "equals", "(Ljava/lang/Object;)Z", false)
                            }
                            2 -> {
                                when (RandomUtil.int(1, 3)) {
                                    1 -> {
                                        ldc(RandomUtil.float(1.0, 200.0))
                                        when (RandomUtil.int(1, 3)) {
                                            1 -> invokestatic("java/lang/Float", "isInfinite", "(F)Z", false)
                                            2 -> invokestatic("java/lang/Float", "isNaN", "(F)Z", false)
                                        }
                                    }
                                    2 -> {
                                        ldc(RandomUtil.double(1.0, 200.0))
                                        when (RandomUtil.int(1, 3)) {
                                            1 -> invokestatic("java/lang/Double", "isInfinite", "(D)Z", false)
                                            2 -> invokestatic("java/lang/Double", "isNaN", "(D)Z", false)
                                        }
                                    }
                                }
                            }
                            3 -> {

                            }
                        }

                        val labelNode = LabelNode()

                        when (type) {
                            1 -> {
                                when {
                                    bool -> {
                                        ifeq(labelNode)
                                    }
                                    else -> {
                                        ifne(labelNode)
                                    }
                                }
                            }
                            else -> ifeq(labelNode)
                        }

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

                                repeat((1..RandomUtil.int(3, 10)).count()) {
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
                                    when(RandomUtil.int(1, 6)) {
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
                                        2 -> {
                                            val size = RandomUtil.int(1, 30)
                                            val stringBuilder = StringBuilder()
                                            stringBuilder.append("(")
                                            repeat((1..size).count()) { stringBuilder.append("Ljava/lang/String;") }
                                            stringBuilder.append(")V")

                                            repeat((1..RandomUtil.int(3, 10)).count()) {
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
                                        3 -> {
                                            ldc("AlpheratzObfuscator - https://github.com/alpheratzteam/obfuscator")
                                            printlnAsm()
                                        }
                                        4 -> {
                                            aconst_null()
                                            athrow()
                                        }
                                        else -> goto(label3)
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