package pl.alpheratzteam.obfuscator.util

import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.tree.*
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.any
import kotlin.collections.filterIndexed
import kotlin.collections.forEachIndexed
import kotlin.collections.set

// https://github.com/ByteZ1337/Vetric/blob/master/src/main/kotlin/xyz/xenondevs/obfuscator/utils/asm/ASMUtils.kt

object ASMUtil {

    /* ------------------ Constnats ------------------ */

    const val OBJECT_TYPE = "java/lang/Object"

    /* ------------------ Access Checks ------------------ */

    fun isPublic(access: Int) = access and ACC_PUBLIC != 0

    fun isPrivate(access: Int) = access and ACC_PRIVATE != 0

    fun isProtected(access: Int) = access and ACC_PROTECTED != 0

    fun isStatic(access: Int) = access and ACC_STATIC != 0

    fun isFinal(access: Int) = access and ACC_FINAL != 0

    fun isAbstract(access: Int) = access and ACC_ABSTRACT != 0

    fun isNative(access: Int) = access and ACC_NATIVE != 0

    fun isEnum(access: Int) = access and ACC_ENUM != 0

    fun isInterface(access: Int) = access and ACC_INTERFACE != 0

    fun isAnnotation(access: Int) = access and ACC_ANNOTATION != 0

    fun isBridge(access: Int) = access and ACC_BRIDGE != 0

    fun isMandated(access: Int) = access and ACC_MANDATED != 0

    fun isModule(access: Int) = access and ACC_MODULE != 0

    fun isOpen(access: Int) = access and ACC_OPEN != 0

    fun isStaticPhase(access: Int) = access and ACC_STATIC_PHASE != 0

    fun isSuper(access: Int) = access and ACC_SUPER != 0

    fun isSynchronized(access: Int) = access and ACC_SYNCHRONIZED != 0

    fun isSynthetic(access: Int) = access and ACC_SYNTHETIC != 0

    fun isTransient(access: Int) = access and ACC_TRANSIENT != 0

    fun isTransitive(access: Int) = access and ACC_TRANSITIVE != 0

    fun isVolatile(access: Int) = access and ACC_VOLATILE != 0

    fun isVarargs(access: Int) = access and ACC_VARARGS != 0

    fun isDeprecated(access: Int) = access and ACC_DEPRECATED != 0

    fun isStrict(access: Int) = access and ACC_STRICT != 0

    fun AnnotationNode.toHashMap(): HashMap<String, Any?> {
        val map = HashMap<String, Any?>()
        values.filterIndexed { index, _ -> index % 2 == 0 }.forEachIndexed { index, any ->
            map[any.toString()] = values[index * 2 + 1]
        }
        return map
    }

    fun hasMethod(name: String, desc: String, clazz: ClassNode) =
        clazz.methods != null && clazz.methods.any { it.desc == desc && it.name == name }

    fun getIntInsn(value: Int) =
        when (value) {
            in -1..5 -> InsnNode(value + 3)
            in Byte.MIN_VALUE..Byte.MAX_VALUE -> IntInsnNode(BIPUSH, value)
            in Short.MIN_VALUE..Short.MAX_VALUE -> IntInsnNode(SIPUSH, value)
            else -> LdcInsnNode(value)
        }

    fun getLongInsn(value: Long) =
        when (value) {
            in 0..1 -> InsnNode((value + 9).toInt())
            else -> LdcInsnNode(value)
        }

    fun getFloatInsn(value: Float) =
        when {
            value % 1 == 0f && value in 0f..2f -> InsnNode((value + 11).toInt())
            else -> LdcInsnNode(value)
        }

    fun getDoubleInsn(value: Double) =
        when {
            value % 1 == 0.0 && value in 0.0..1.0 -> InsnNode((value + 14).toInt())
            else -> LdcInsnNode(value)
        }

    fun isIntInsn(insn: AbstractInsnNode): Boolean {
        return when {
            Objects.isNull(insn) -> false
            else -> {
                val opcode = insn.opcode
                (opcode in ICONST_M1..ICONST_5 || opcode == BIPUSH || opcode == SIPUSH || (insn is LdcInsnNode && insn.cst is Int))
            }
        }
    }

    fun isLongInsn(insn: AbstractInsnNode): Boolean = insn.opcode == LCONST_0 || insn.opcode == LCONST_1 || (insn is LdcInsnNode && insn.cst is Long)

    fun isFloatInsn(insn: AbstractInsnNode): Boolean = (insn.opcode in FCONST_0..FCONST_2 || insn is LdcInsnNode && insn.cst is Float)

    fun isDoubleInsn(insn: AbstractInsnNode): Boolean = (insn.opcode in DCONST_0..DCONST_1 || insn is LdcInsnNode && insn.cst is Double)

    // https://github.com/ItzSomebody/radon/blob/master/src/main/java/me/itzsomebody/radon/utils/ASMUtils.java

    fun getIntFromInsn(insn: AbstractInsnNode): Int {
        val opcode = insn.opcode
        return when {
            opcode in ICONST_M1..ICONST_5 -> opcode - 3
            insn is IntInsnNode && insn.opcode !== NEWARRAY -> insn.operand
            insn is LdcInsnNode && insn.cst is Int -> insn.cst as Int
            else -> throw UnsupportedOperationException()
        }
    }

    fun getLongFromInsn(insn: AbstractInsnNode): Long {
        val opcode = insn.opcode
        return when {
            opcode in LCONST_0..LCONST_1 -> (opcode - 9).toLong()
            insn is LdcInsnNode && insn.cst is Long -> insn.cst as Long
            else -> 0L
        }
    }

    fun getFloatFromInsn(insn: AbstractInsnNode): Float {
        val opcode = insn.opcode
        return when {
            opcode in FCONST_0..FCONST_2 -> (opcode - 11).toFloat()
            insn is LdcInsnNode && insn.cst is Float -> insn.cst as Float
            else -> .0f
        }
    }

    fun getDoubleFromInsn(insn: AbstractInsnNode): Double {
        val opcode = insn.opcode
        return when {
            opcode in DCONST_0..DCONST_1 -> (opcode - 14).toDouble()
            insn is LdcInsnNode && insn.cst is Double -> insn.cst as Double
            else -> .0
        }
    }

}