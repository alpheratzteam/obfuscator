package pl.alpheratzteam.obfuscator.transformer

import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.tree.FieldInsnNode
import org.objectweb.asm.tree.FieldNode
import pl.alpheratzteam.obfuscator.Obfuscator
import pl.alpheratzteam.obfuscator.api.transformer.Transformer
import pl.alpheratzteam.obfuscator.util.StringUtil
import org.objectweb.asm.tree.InsnNode
import org.objectweb.asm.tree.JumpInsnNode
import pl.alpheratzteam.obfuscator.util.ConditionUtil

/**
 * @author Unix
 * @since 18.12.2020
 */

class FlowTransformer : Transformer {

    override fun transform(obfuscator: Obfuscator) {
        obfuscator.classes.values.forEach { classNode ->
            var hasField = false
            val fieldName = StringUtil.generateString(8)
            classNode.methods.forEach {
                val methodNode = it
                methodNode.instructions.forEach {
                    if (it.opcode != GOTO) {
                        return@forEach
                    }

                    hasField = true
                    with(methodNode) {
                        instructions.insertBefore(it, FieldInsnNode(GETSTATIC, classNode.name, fieldName, "Z"))
                        instructions.insert(it, InsnNode(ATHROW))
                        instructions.insert(it, InsnNode(ACONST_NULL))
                        instructions.set(it, JumpInsnNode(IFEQ, (it as JumpInsnNode).label))
                    }
                }
            }

            ConditionUtil.checkCondition(hasField) {
                classNode.fields.add(FieldNode(ACC_PUBLIC + ACC_STATIC + ACC_FINAL, fieldName, "Z", null, null))
            }
        }
    }

}