package pl.alpheratzteam.obfuscator.util

import org.objectweb.asm.Type
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.tree.*
import java.io.PrintStream
import kotlin.reflect.KClass
import kotlin.reflect.KFunction

/**
 * @author cookiedragon234 23/Apr/2020
 */
inline fun <reified T: Any> Any?.cast(type: KClass<T>): T = this as T
inline fun <reified T: Any> Any?.cast(type: Class<T>): T = this as T

val <T: Any> KClass<T>.internalName: String
    get() = Type.getInternalName(this.java)

inline val KFunction<*>.descriptor: String
    inline get() {
        val params = parameters.map { Type.getType(it.type.classifier.cast(KClass::class).java) }
        val returnType = Type.getType(returnType.classifier.cast(KClass::class).java)
        return Type.getMethodDescriptor(returnType, *params.toTypedArray())
    }

fun InsnList.add(opcode: Int) = add(InsnNode(opcode))

fun ldcInt(int: Int): AbstractInsnNode {
    return if (int == -1) {
        InsnNode(ICONST_M1)
    } else if (int == 0) {
        InsnNode(ICONST_0)
    } else if (int == 1) {
        InsnNode(ICONST_1)
    } else if (int == 2) {
        InsnNode(ICONST_2)
    } else if (int == 3) {
        InsnNode(ICONST_3)
    } else if (int == 4) {
        InsnNode(ICONST_4)
    } else if (int == 5) {
        InsnNode(ICONST_5)
    } else if (int >= -128 && int <= 127) {
        IntInsnNode(BIPUSH, int)
    } else if (int >= -32768 && int <= 32767) {
        IntInsnNode(SIPUSH, int)
    } else {
        LdcInsnNode(int)
    }
}

fun ldcLong(long: Long): AbstractInsnNode {
    return when (long) {
        0L -> InsnNode(LCONST_0)
        1L -> InsnNode(LCONST_1)
        else -> LdcInsnNode(long)
    }
}

fun ldcDouble(double: Double): AbstractInsnNode {
    return when (double) {
        0.0 -> InsnNode(DCONST_0)
        1.0 -> InsnNode(DCONST_1)
        else -> LdcInsnNode(double)
    }
}

fun ldcFloat(float: Float): AbstractInsnNode {
    return when (float) {
        0f -> InsnNode(FCONST_0)
        1f -> InsnNode(FCONST_1)
        2f -> InsnNode(FCONST_2)
        else -> LdcInsnNode(float)
    }
}

inline fun constructTableSwitch(
    baseNumber: Int,
    defaultLabel: LabelNode,
    vararg targetLabels: LabelNode
): TableSwitchInsnNode {
    return TableSwitchInsnNode(
        baseNumber,
        baseNumber + targetLabels.size - 1,
        defaultLabel,
        *targetLabels
    )
}

inline fun constructLookupSwitch(
    defaultLabel: LabelNode,
    lookup: Array<Pair<Int, LabelNode>>
): LookupSwitchInsnNode {
    lookup.sortWith { a, b -> a.first.compareTo(b.first) }

    val keys = lookup.map {
        it.first
    }.toIntArray()

    val values = lookup.map {
        it.second
    }.toTypedArray()

    return LookupSwitchInsnNode(
        defaultLabel,
        keys,
        values
    )
}

fun InsnList.printlnAsm() {
    add(FieldInsnNode(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"))
    add(SWAP)
    add(MethodInsnNode(INVOKEVIRTUAL, PrintStream::class.internalName, "println", "(Ljava/lang/Object;)V", false))
}

fun InsnBuilder.printlnAsm() {
    +FieldInsnNode(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;")
    +SWAP.insn()
    +MethodInsnNode(INVOKEVIRTUAL, PrintStream::class.internalName, "println", "(Ljava/lang/Object;)V", false)
}

fun InsnList.printlnIntAsm() {
    add(FieldInsnNode(GETSTATIC, System::class.internalName, "out", "Ljava/io/PrintStream;"))
    add(InsnNode(SWAP))
    add(MethodInsnNode(INVOKEVIRTUAL, PrintStream::class.internalName, "println", "(I)V", false))
}
fun InsnBuilder.printlnIntAsm() {
    +FieldInsnNode(GETSTATIC, System::class.internalName, "out", "Ljava/io/PrintStream;")
    +SWAP.insn()
    +MethodInsnNode(INVOKEVIRTUAL, PrintStream::class.internalName, "println", "(I)V", false)
}

inline fun InsnList.forEach(op: (insn: AbstractInsnNode) -> Unit) = this.iterator().forEach(op)