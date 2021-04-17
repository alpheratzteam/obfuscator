package pl.alpheratzteam.obfuscator.transformer

import org.objectweb.asm.Opcodes.*
import pl.alpheratzteam.obfuscator.Obfuscator
import pl.alpheratzteam.obfuscator.api.transformer.Transformer
import pl.alpheratzteam.obfuscator.util.ASMUtil
import org.objectweb.asm.tree.InsnNode
import org.objectweb.asm.tree.IntInsnNode
import org.objectweb.asm.tree.LdcInsnNode
import pl.alpheratzteam.obfuscator.util.RandomUtil
import java.util.concurrent.atomic.AtomicInteger


/**
 * @author Unix
 * @since 16.04.2021
 */

class FakeInstructionsTransformer : Transformer {

    // https://github.com/sim0n/Caesium/blob/master/src/main/java/dev/sim0n/caesium/mutator/impl/PolymorphMutator.java

    override fun transform(obfuscator: Obfuscator) {
        obfuscator.classes.values.forEach {
            it.methods.filter { ASMUtil.hasInstructions(it) }.forEach {
                val index = AtomicInteger()
                val instructions = it.instructions

                it.instructions.forEach {
                    if (it is LdcInsnNode) {
                        if (RandomUtil.boolean()) {
                            instructions.insertBefore(
                                it,
                                IntInsnNode(BIPUSH, RandomUtil.int(-64, 64))
                            )
                            instructions.insertBefore(it, InsnNode(POP))
                        }
                    } else if (index.getAndIncrement() % 6 == 0) {
                        if (RandomUtil.float() > 0.6) {
                            instructions.insertBefore(
                                it,
                                IntInsnNode(BIPUSH, RandomUtil.int(-27, 37))
                            )
                            instructions.insertBefore(it, InsnNode(POP))
                        } else {
                            instructions.insertBefore(it, InsnNode(NOP))
                        }
                    }
                }
            }
        }
    }

}