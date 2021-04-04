package pl.alpheratzteam.obfuscator.api.number

import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.tree.InsnNode

/**
 * @author Unix
 * @since 03.04.2021
 */

enum class NumberType(val store: Int, val opcode: Int, val descriptor: String) {

    INTEGER(IASTORE, T_INT, "[I"),
    DOUBLE(DASTORE, T_DOUBLE, "[D"),
    FLOAT(FASTORE, T_FLOAT, "[F"),
    LONG(LASTORE, T_LONG, "[J")

}