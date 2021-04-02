package pl.alpheratzteam.obfuscator.transformer

import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.tree.FieldNode
import org.objectweb.asm.tree.MethodNode
import pl.alpheratzteam.obfuscator.Obfuscator
import pl.alpheratzteam.obfuscator.api.transformer.Transformer
import pl.alpheratzteam.obfuscator.util.*

/**
 * @author Unix
 * @since 17.12.2020
 */

class NumberTransformer : Transformer {

    private var numberId = 0

    // TODO: 18.12.2020 float, double, long

    override fun transform(obfuscator: Obfuscator) {
        obfuscator.classes.values.forEach { classNode ->
            val numbers = mutableMapOf<Int, Int>()
            val fieldName = StringUtil.generateString(8)
            classNode.fields.add(FieldNode(ACC_STATIC + ACC_PRIVATE, fieldName, "[I", null, null))
            classNode.methods.forEach { methodNode ->
                methodNode.instructions.forEach {
                    ConditionUtil.checkCondition(ASMUtil.isIntInsn(it)) { // int
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

            with (classNode) {
                methods.removeIf { methodNode -> methodNode.name.equals("<clinit>") }
                methods.add(makeClinit(Pair(classNode.name, fieldName), numbers))
            }
        }
    }

    private fun makeClinit(pair: Pair<String, String>, numbers: MutableMap<Int, Int>): MethodNode {
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
                putstatic(pair.first, pair.second, "[I")
                numbers.forEach {
                    getstatic(pair.first, pair.second, "[I")
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