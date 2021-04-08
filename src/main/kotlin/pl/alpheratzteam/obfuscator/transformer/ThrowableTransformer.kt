package pl.alpheratzteam.obfuscator.transformer

import org.objectweb.asm.Opcodes.F_SAME
import org.objectweb.asm.tree.*
import pl.alpheratzteam.obfuscator.Obfuscator
import pl.alpheratzteam.obfuscator.api.transformer.Transformer
import pl.alpheratzteam.obfuscator.util.ASMUtil
import pl.alpheratzteam.obfuscator.util.StringUtil
import pl.alpheratzteam.obfuscator.util.insnBuilder
import java.util.*

/**
 * @author Unix
 * @since 03.04.2021
 */

class ThrowableTransformer : Transformer {

    override fun transform(obfuscator: Obfuscator) {
        obfuscator.classes.values.forEach {
            it.methods.filter { !it.name.startsWith("<") }.forEach { methodNode ->
                methodNode.instructions
                    .asSequence()
                    .filter { ASMUtil.isInstruction(it) && !Objects.isNull(it.next) }
                    .forEach { methodNode.instructions.insertBefore(it.next, makeInsn()) }
            }
        }
    }

    fun makeInsn() : InsnList {
        return insnBuilder {
            +LabelNode()
            val string = StringUtil.generateString(2)
            ldc(string)
            ldc(string + StringUtil.generateString(2))
            invokevirtual("java/lang/String", "equals", "(Ljava/lang/Object;)Z", false)
            val labelNode = LabelNode()
            ifeq(labelNode)
            val labelNode1 = LabelNode()
            +labelNode1
            aconst_null()
            athrow()
            +labelNode
            frame(F_SAME, 0, null, 0, null)
            +LabelNode()
        }
    }

}