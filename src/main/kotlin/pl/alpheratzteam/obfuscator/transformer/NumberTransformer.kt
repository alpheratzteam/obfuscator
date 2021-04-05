package pl.alpheratzteam.obfuscator.transformer

import org.objectweb.asm.tree.InsnList
import org.objectweb.asm.tree.MethodNode
import pl.alpheratzteam.obfuscator.Obfuscator
import pl.alpheratzteam.obfuscator.api.number.NumberData
import pl.alpheratzteam.obfuscator.api.number.NumberType.DOUBLE
import pl.alpheratzteam.obfuscator.api.number.NumberType.FLOAT
import pl.alpheratzteam.obfuscator.api.number.NumberType.LONG
import pl.alpheratzteam.obfuscator.api.number.NumberType.INTEGER
import pl.alpheratzteam.obfuscator.api.transformer.Transformer
import pl.alpheratzteam.obfuscator.util.*

/**
 * @author Unix
 * @since 17.12.2020
 */

class NumberTransformer : Transformer {

    // TODO: 04.04.2021 cleanup code, static initialization

    override fun transform(obfuscator: Obfuscator) {
        obfuscator.classes.values.forEach { classNode ->
            val intNumbers = NumberData<Int>(StringUtil.generateString(8), INTEGER)
            val doubleNumbers = NumberData<Double>(StringUtil.generateString(8), DOUBLE)
            val floatNumbers = NumberData<Float>(StringUtil.generateString(8), FLOAT)
            val longNumbers = NumberData<Long>(StringUtil.generateString(8), LONG)

            classNode.fields.add(intNumbers.getFieldNode())
            classNode.fields.add(doubleNumbers.getFieldNode())
            classNode.fields.add(floatNumbers.getFieldNode())
            classNode.fields.add(longNumbers.getFieldNode())

            classNode.methods.forEach { methodNode ->
                methodNode.instructions.forEach {
                    ConditionUtil.checkCondition(ASMUtil.isIntInsn(it)) { // int
                        val fieldNumber = RandomUtil.int(10, 3000)
                        val originalNumber = ASMUtil.getIntFromInsn(it)
                        val fakeNumber = RandomUtil.int(10, 3000)
                        val calcNumber = originalNumber xor fakeNumber * fieldNumber

                        intNumbers.addNumber(intNumbers.size, fieldNumber)
                        methodNode.instructions.insertBefore(it, insnBuilder {
                            ldc(calcNumber)
                            ldc(fakeNumber)
                            getstatic(classNode.name, intNumbers.fieldName, intNumbers.numberType.descriptor)
                            ldc(intNumbers.size)
                            iaload()
                            imul()
                            ixor()
                        })
                        methodNode.instructions.remove(it)
                        ++intNumbers.size
                    }

                    ConditionUtil.checkCondition(ASMUtil.isDoubleInsn(it)) { // double
                        val fieldNumber = RandomUtil.int(10, 3000).toDouble()
                        val originalNumber = ASMUtil.getDoubleFromInsn(it)
                        val fakeNumber = RandomUtil.int(10, 3000).toDouble()
                        val calcNumber = originalNumber * fakeNumber * fieldNumber

                        doubleNumbers.addNumber(doubleNumbers.size, fieldNumber)
                        methodNode.instructions.insertBefore(it, insnBuilder {
                            ldc(calcNumber)
                            ldc(fakeNumber)
                            getstatic(classNode.name, doubleNumbers.fieldName, doubleNumbers.numberType.descriptor)
                            ldc(doubleNumbers.size)
                            daload()
                            dmul()
                            ddiv()
                        })
                        methodNode.instructions.remove(it)
                        ++doubleNumbers.size
                    }

                    ConditionUtil.checkCondition(ASMUtil.isFloatInsn(it)) { // float
                        val fieldNumber = RandomUtil.int(10, 3000).toFloat()
                        val originalNumber = ASMUtil.getFloatFromInsn(it)
                        val fakeNumber = RandomUtil.int(10, 3000).toFloat()
                        val calcNumber = originalNumber * fakeNumber * fieldNumber

                        floatNumbers.addNumber(floatNumbers.size, fieldNumber)
                        methodNode.instructions.insertBefore(it, insnBuilder {
                            ldc(calcNumber)
                            ldc(fakeNumber)
                            getstatic(classNode.name, floatNumbers.fieldName, floatNumbers.numberType.descriptor)
                            ldc(floatNumbers.size)
                            faload()
                            fmul()
                            fdiv()
                        })
                        methodNode.instructions.remove(it)
                        ++floatNumbers.size
                    }

                    ConditionUtil.checkCondition(ASMUtil.isLongInsn(it)) { // long
                        val fieldNumber = RandomUtil.int(10, 3000).toLong()
                        val originalNumber = ASMUtil.getLongFromInsn(it)
                        val fakeNumber = RandomUtil.int(10, 3000).toLong()
                        val calcNumber = originalNumber xor fakeNumber * fieldNumber

                        longNumbers.addNumber(longNumbers.size, fieldNumber)
                        methodNode.instructions.insertBefore(it, insnBuilder {
                            ldc(calcNumber)
                            ldc(fakeNumber)
                            getstatic(classNode.name, longNumbers.fieldName, longNumbers.numberType.descriptor)
                            ldc(longNumbers.size)
                            laload()
                            lmul()
                            lxor()
                        })
                        methodNode.instructions.remove(it)
                        ++longNumbers.size
                    }
                }
            }

            with (classNode) {
                methods.filter { it.name.equals("<clinit>") }.forEach {
                    it.instructions = makeClinit(it, classNode.name, intNumbers)
                    it.instructions = makeClinit(it, classNode.name, doubleNumbers)
                    it.instructions = makeClinit(it, classNode.name, floatNumbers)
                    it.instructions = makeClinit(it, classNode.name, longNumbers)
                }
            }
        }
    }

//    private fun <T : Number> changeInsn(classNode: ClassNode, methodNode: MethodNode, abstractInsnNode: AbstractInsnNode, numberData: NumberData<T>) {
//        ConditionUtil.checkCondition(ASMUtil.isDoubleInsn(abstractInsnNode)) { // double
//            val fieldNumber = RandomUtil.int(10, 3000)
//            val originalNumber = ASMUtil.getDoubleFromInsn(abstractInsnNode)
//            val fakeNumber = RandomUtil.int(10, 3000)
//            val calcNumber = originalNumber * fakeNumber * fieldNumber
//
//
//            when (numberData.numberType) {
//                INTEGER -> numberData.addNumber(numberData.size, fieldNumber)
//                LONG -> numberData.addNumber(numberData.size, fieldNumber.toLong() as Long)
//                DOUBLE -> numberData.addNumber(numberData.size, fieldNumber.toDouble() as Double)
//                FLOAT -> numberData.addNumber(numberData.size, fieldNumber.toFloat() as Float)
//            }
//
//            methodNode.instructions.insertBefore(abstractInsnNode, insnBuilder {
//                ldc(calcNumber)
//                ldc(fakeNumber)
//                getstatic(classNode.name, numberData.fieldName, numberData.numberType.descriptor)
//                ldc(numberData.size)
//                daload()
//                dmul()
//                ddiv()
//            })
//
//            methodNode.instructions.remove(abstractInsnNode)
//            ++numberData.size
//        }
//    }

    private fun <T : Number> makeClinit(methodNode: MethodNode, className: String, numberData: NumberData<T>): InsnList {
        val insnNode = methodNode.instructions
        return insnBuilder {
            ldc(numberData.size)
            newarray(numberData.numberType.opcode)
            putstatic(className, numberData.fieldName, numberData.numberType.descriptor)
            numberData.numbers.forEach { (key, value) ->
                getstatic(className, numberData.fieldName, numberData.numberType.descriptor)
                ldc(key)
                when (numberData.numberType) {
                    INTEGER -> ldc(value as Int)
                    LONG -> ldc(value as Long)
                    DOUBLE -> ldc(value as Double)
                    FLOAT -> ldc(value as Float)
                }
                insn(numberData.numberType.store)
            }

            +insnNode
            _return()
        }
    }

}