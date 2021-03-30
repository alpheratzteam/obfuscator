package pl.alpheratzteam.obfuscator.transformer

import org.objectweb.asm.Opcodes.*
import pl.alpheratzteam.obfuscator.Obfuscator
import pl.alpheratzteam.obfuscator.api.transformer.Transformer
import pl.alpheratzteam.obfuscator.util.ASMUtil
import pl.alpheratzteam.obfuscator.util.ConditionUtil
import java.util.Objects

/**
 * @author Unix
 * @since 18.12.2020
 */

class HideCodeTransformer : Transformer {

    override fun transform(obfuscator: Obfuscator) {
        obfuscator.classes.values.forEach {
            ConditionUtil.checkCondition(!(ASMUtil.isSynthetic(it.access) && Objects.isNull(it.visibleAnnotations))) {
                it.access = it.access or ACC_SYNTHETIC
            }

            it.methods.forEach {
                ConditionUtil.checkCondition(!ASMUtil.isSynthetic(it.access)) {
                    it.access = it.access or ACC_SYNTHETIC
                }

                ConditionUtil.checkCondition(!it.name.startsWith("<") && ASMUtil.isBridge(it.access)) {
                    it.access = it.access or ACC_BRIDGE
                }
            }

            it.fields.forEach {
                ConditionUtil.checkCondition(ASMUtil.isSynthetic(it.access)) {
                    it.access = it.access or ACC_SYNTHETIC
                }
            }
        }
    }

}