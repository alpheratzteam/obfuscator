package pl.alpheratzteam.obfuscator.api.number

import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.tree.FieldNode

/**
 * @author Unix
 * @since 03.04.2021
 */

class NumberData<T : Number>(val fieldName: String, val numberType: NumberType) {

    var size = 0
    val numbers = mutableMapOf<Int, T>()

    fun addNumber(id: Int, number: T) {
        numbers[id] = number
    }

    fun getFieldNode() : FieldNode {
        return FieldNode(ACC_STATIC + ACC_PRIVATE, fieldName, numberType.descriptor, null, null)
    }

}