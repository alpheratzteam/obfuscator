package pl.alpheratzteam.obfuscator.api.number

import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.tree.FieldNode

/**
 * @author Unix
 * @since 03.04.2021
 */

class NumberData<T : Number>(val fieldName: String, val numberType: NumberType) {

    val numbers = mutableMapOf<Int, T>()
    var size = 0

    fun addNumber(id: Int, number: T) {
        numbers[id] = number
    }

    fun getFieldNode() : FieldNode {
        return FieldNode(ACC_STATIC + ACC_PRIVATE, fieldName, numberType.descriptor, null, null)
    }

}

//class NumberData<T : Number>(val fieldName: String, val numberType: NumberType, val opcodes: Array<Int>) {
//
//    val numbers = mutableMapOf<Int, T>()
//    var size = 0
//
//    fun addNumber(id: Int, number: Any) {
//        numbers[id] = number as T
//    }
//
//    fun getFieldNode() : FieldNode {
//        return FieldNode(ACC_STATIC + ACC_PRIVATE, fieldName, numberType.descriptor, null, null)
//    }
//
//    fun isNumber(abstractInsnNode: AbstractInsnNode) : Boolean {
//        return when(numberType) {
//            NumberType.INTEGER -> ASMUtil.isIntInsn(abstractInsnNode)
//            NumberType.DOUBLE -> ASMUtil.isDoubleInsn(abstractInsnNode)
//            NumberType.FLOAT -> ASMUtil.isFloatInsn(abstractInsnNode)
//            NumberType.LONG -> ASMUtil.isLongInsn(abstractInsnNode)
//        }
//    }
//
//    fun getNumber(abstractInsnNode: AbstractInsnNode) : Number {
//        return when(numberType) {
//            NumberType.INTEGER -> ASMUtil.getIntFromInsn(abstractInsnNode)
//            NumberType.DOUBLE -> ASMUtil.getDoubleFromInsn(abstractInsnNode)
//            NumberType.FLOAT -> ASMUtil.getFloatFromInsn(abstractInsnNode)
//            NumberType.LONG -> ASMUtil.getLongFromInsn(abstractInsnNode)
//        }
//    }
//
//}