package pl.alpheratzteam.obfuscator.transformer.optimizer

import org.objectweb.asm.Opcodes.GOTO
import org.objectweb.asm.tree.JumpInsnNode
import pl.alpheratzteam.obfuscator.Obfuscator
import pl.alpheratzteam.obfuscator.api.transformer.Transformer
import pl.alpheratzteam.obfuscator.util.ConditionUtil
import java.util.*

/**
 * @author Unix
 * @since 18.12.2020
 */

class GotoInlinerTransformer : Transformer {

    override fun transform(obfuscator: Obfuscator) {
        obfuscator.classes.values.forEach {
            it.methods.forEach {
                it.instructions.filter { it.opcode == GOTO }.forEach {
                    val gotoJump = it as JumpInsnNode
                    val insnAfterTarget = gotoJump.label.next
                    ConditionUtil.checkCondition(Objects.nonNull(insnAfterTarget)) {
                        when (insnAfterTarget.opcode) {
                            GOTO -> gotoJump.label = (insnAfterTarget as JumpInsnNode).label
                        }
                    }
                }
            }
        }
    }

}