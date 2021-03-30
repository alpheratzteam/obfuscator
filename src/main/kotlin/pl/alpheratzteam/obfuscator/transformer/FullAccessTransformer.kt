package pl.alpheratzteam.obfuscator.transformer

import org.objectweb.asm.Opcodes.*
import pl.alpheratzteam.obfuscator.Obfuscator
import pl.alpheratzteam.obfuscator.api.transformer.Transformer

/**
 * @author Unix
 * @since 18.12.2020
 */

class FullAccessTransformer : Transformer {

    private val accesses = intArrayOf(ACC_ANNOTATION, ACC_FINAL, ACC_ANNOTATION, ACC_ENUM, ACC_INTERFACE, ACC_MANDATED,
        ACC_MODULE, ACC_OPEN, ACC_STRICT, ACC_SYNCHRONIZED, ACC_SYNTHETIC, ACC_TRANSIENT, ACC_TRANSITIVE, ACC_VARARGS, ACC_DEPRECATED
    ) // separate (enum): class, method, field

    override fun transform(obfuscator: Obfuscator) {
        obfuscator.classes.values.forEach {
            with (it) {
//                access = changeAccess(access) // change classNode
                methods.filter { !it.name.startsWith("<") }.forEach { it.access = changeAccess(it.access) } // change methodNode
                fields.forEach { it.access = changeAccess(it.access) } // change fieldNode
            }
        }
    }

    private fun changeAccess(access: Int): Int {
        var newAccess: Int = access
        accesses.filter { access and it == 0 }.forEach { newAccess = newAccess or it }
        return newAccess
    }

}