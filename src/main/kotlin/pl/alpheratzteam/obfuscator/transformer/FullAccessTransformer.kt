package pl.alpheratzteam.obfuscator.transformer

import org.objectweb.asm.Opcodes.*
import pl.alpheratzteam.obfuscator.Obfuscator
import pl.alpheratzteam.obfuscator.api.transformer.Transformer
import pl.alpheratzteam.obfuscator.util.ASMUtil

/**
 * @author Unix
 * @since 18.12.2020
 */

class FullAccessTransformer : Transformer {
    override fun transform(obfuscator: Obfuscator) {
        obfuscator.classes.forEach {
            with (it.value) {
                access = changeAccess(access) // change classNode
                methods.forEach { it.access = changeAccess(it.access) } // change methodNode
                fields.forEach { it.access = changeAccess(it.access) } // change fieldNode
            }
        }
    }

    private fun changeAccess(access: Int): Int { // TODO: 18.12.2020 maybe list? - will it be fast?
        var newAccess: Int = ACC_PUBLIC
        when { ASMUtil.isNative(access) -> newAccess = newAccess or ACC_NATIVE }
        when { ASMUtil.isAbstract(access) -> newAccess = newAccess or ACC_ABSTRACT }
        when { ASMUtil.isAnnotation(access) -> newAccess = newAccess or ACC_ANNOTATION }
        when { ASMUtil.isBridge(access) -> newAccess = newAccess or ACC_BRIDGE }
        when { ASMUtil.isEnum(access) -> newAccess = newAccess or ACC_ENUM }
        when { ASMUtil.isFinal(access) -> newAccess = newAccess or ACC_FINAL }
        when { ASMUtil.isInterface(access) -> newAccess = newAccess or ACC_INTERFACE }
        when { ASMUtil.isMandated(access) -> newAccess = newAccess or ACC_MANDATED }
        when { ASMUtil.isModule(access) -> newAccess = newAccess or ACC_MODULE }
        when { ASMUtil.isOpen(access) -> newAccess = newAccess or ACC_OPEN }
        when { ASMUtil.isStatic(access) -> newAccess = newAccess or ACC_STATIC }
        when { ASMUtil.isStaticPhase(access) -> newAccess = newAccess or ACC_STATIC_PHASE }
        when { ASMUtil.isStrict(access) -> newAccess = newAccess or ACC_STRICT }
        when { ASMUtil.isSuper(access) -> newAccess = newAccess or ACC_SUPER }
        when { ASMUtil.isSynchronized(access) -> newAccess = newAccess or ACC_SYNCHRONIZED }
        when { ASMUtil.isSynthetic(access) -> newAccess = newAccess or ACC_SYNTHETIC }
        when { ASMUtil.isTransient(access) -> newAccess = newAccess or ACC_TRANSIENT }
        when { ASMUtil.isTransitive(access) -> newAccess = newAccess or ACC_TRANSITIVE }
        when { ASMUtil.isVolatile(access) -> newAccess = newAccess or ACC_VOLATILE }
        when { ASMUtil.isVarargs(access) -> newAccess = newAccess or ACC_VARARGS }
        when { ASMUtil.isDeprecated(access) -> newAccess = newAccess or ACC_DEPRECATED }
        return newAccess
    }
}