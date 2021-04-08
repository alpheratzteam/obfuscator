package pl.alpheratzteam.obfuscator.transformer

import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.tree.*
import pl.alpheratzteam.obfuscator.Obfuscator
import pl.alpheratzteam.obfuscator.api.transformer.Transformer
import pl.alpheratzteam.obfuscator.util.RandomUtil
import pl.alpheratzteam.obfuscator.util.StringUtil
import pl.alpheratzteam.obfuscator.util.insnBuilder
import java.util.concurrent.atomic.AtomicInteger

/**
 * @author Unix
 * @since 07.04.2021
 */

class BooleanObfuscationTransformer : Transformer {

    override fun transform(obfuscator: Obfuscator) {
        obfuscator.classes.values.forEach { classNode ->
            val fieldName = StringUtil.generateString(8)
            val ints = mutableListOf<Int>()
            classNode.fields.add(FieldNode(ACC_STATIC + ACC_PRIVATE, fieldName, "[I", null, null))
            classNode.methods.forEach { methodNode ->
                methodNode.instructions.forEach {
                    if (it.previous is InsnNode && it is VarInsnNode) {
                        val value = RandomUtil.int(10, 2000)
                        val insnNode = it.previous as InsnNode
                        if (insnNode.opcode == 4 || insnNode.opcode == 3) {
                            methodNode.instructions.insertBefore(it, makeInsn(classNode, fieldName, ints.size, ints.size + 1))
                            methodNode.instructions.remove(it.previous)
                            ints.add(value)

                            when (insnNode.opcode) {
                                4 -> ints.add(value)
                                3 -> ints.add(value + RandomUtil.int(10, 2000))
                            }
                        }
                    }
                }
            }

            classNode.methods.filter { it.name.equals("<clinit>") }.forEach { it.instructions = makeClinit(it, classNode.name, fieldName, ints) }
        }
    }

    private fun makeClinit(methodNode: MethodNode, className: String, fieldName: String, ints: List<Int>): InsnList {
        val insnNode = methodNode.instructions
        return insnBuilder {
            ldc(ints.size)
            newintarray()
            putstatic(className, fieldName, "[I")

            val size = AtomicInteger()
            ints.forEach {
                getstatic(className, fieldName, "[I")
                ldc(size.getAndIncrement())
                ldc(it)
                iastore()
            }

            +insnNode
            _return()
        }
    }

    fun makeInsn(classNode: ClassNode, fieldName: String, first: Int, second: Int) : InsnList {
        return insnBuilder {
            getstatic(classNode.name, fieldName, "[I")
            ldc(first)
            iaload()
            getstatic(classNode.name, fieldName, "[I")
            ldc(second)
            iaload()
            val labelNode = LabelNode()
            if_icmpne(labelNode)
            insn(ICONST_1)
            val labelNode2 = LabelNode()
            goto(labelNode2)
            +labelNode
            frame(F_SAME, 0, null, 0, null)
            insn(ICONST_0)
            +labelNode2
            frame(F_SAME1, 0, null, 1, arrayOf(INTEGER))
            istore(0)
        }
    }

}