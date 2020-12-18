package pl.alpheratzteam.obfuscator.transformer

import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.tree.FieldNode
import org.objectweb.asm.tree.MethodNode
import pl.alpheratzteam.obfuscator.Obfuscator
import pl.alpheratzteam.obfuscator.api.transformer.Transformer
import pl.alpheratzteam.obfuscator.util.ASMUtil
import pl.alpheratzteam.obfuscator.util.RandomUtil
import pl.alpheratzteam.obfuscator.util.StringUtil
import pl.alpheratzteam.obfuscator.util.insnBuilder

/**
 * @author Unix
 * @since 17.12.2020
 */

class NumberTransformer : Transformer {

    private var numberId = 0

    // TODO: 18.12.2020 float, double, long

    override fun transform(obfuscator: Obfuscator) {
        obfuscator.classes.forEach {
            val numbers: MutableMap<Int, Int> = mutableMapOf()
            val classNode = it.value
            val fieldName = StringUtil.generateString(8)
            classNode.fields.add(FieldNode(ACC_STATIC + ACC_PRIVATE, fieldName, "[I", null, null))
            classNode.methods.forEach {
                val methodNode = it
                it.instructions.forEach {
                    when {
                        ASMUtil.isIntInsn(it) -> { // int
                            val fieldNumber = RandomUtil.int(10, 3000)
                            val originalNumber = ASMUtil.getIntFromInsn(it)
                            val fakeNumber = RandomUtil.int(10, 3000)
                            val calcNumber = originalNumber xor fakeNumber * fieldNumber

                            numbers.put(numberId, fieldNumber)
                            methodNode.instructions.insertBefore(it, insnBuilder {
                                ldc(calcNumber)
                                ldc(fakeNumber)
                                getstatic(classNode.name, fieldName, "[I")
                                ldc(numberId)
                                iaload()
                                imul()
                                ixor()
                            })
                            methodNode.instructions.remove(it)
                            ++numberId
                        }
                    }
                }
            }

            with (classNode) {
                methods.removeIf { methodNode -> methodNode.name.equals("<clinit>") }
                methods.add(makeClinit(classNode.name, fieldName, numbers))
            }
        }
    }

    private fun makeClinit(owner: String, fieldName: String, numbers: MutableMap<Int, Int>): MethodNode {
        val method = MethodNode()
        with(method) {
            access = ACC_STATIC
            name = "<clinit>"
            desc = "()V"
            signature = null
            exceptions = null
            instructions = insnBuilder {
                ldc(numberId)
                newarray(T_INT)
                putstatic(owner, fieldName, "[I")
                numbers.forEach {
                    getstatic(owner, fieldName, "[I")
                    ldc(it.key)
                    ldc(it.value)
                    iastore()
                }
                _return()
            }
        }
        return method
    }
}