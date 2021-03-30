package pl.alpheratzteam.obfuscator.util

import org.objectweb.asm.Handle
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.Type
import org.objectweb.asm.tree.*

/**
 * @author cookiedragon234, ByteZ
 */

class InsnBuilder {

    val list = InsnList()

    operator fun InsnList.unaryPlus() = list.add(this)
    operator fun AbstractInsnNode.unaryPlus() = list.add(this)
    fun Int.insn() = InsnNode(this)

    fun insn(opcode: Int) = +InsnNode(opcode)

    fun nop() = insn(NOP)

    /* Loading constant values */

    fun aconst_null() = insn(ACONST_NULL)
    fun ldc(int: Int) = +ASMUtil.getIntInsn(int)
    fun ldc(long: Long) = +ASMUtil.getLongInsn(long)
    fun ldc(float: Float) = +ASMUtil.getFloatInsn(float)
    fun ldc(double: Double) = +ASMUtil.getDoubleInsn(double)
    fun ldc(string: String) = +LdcInsnNode(string)
    fun ldc(type: Type) = +LdcInsnNode(type)
    fun ldc(handle: Handle) = +LdcInsnNode(handle)

    /* Locals */

    fun istore(`var`: Int) = +VarInsnNode(ISTORE, `var`)
    fun iload(`var`: Int) = +VarInsnNode(ILOAD, `var`)
    fun lstore(`var`: Int) = +VarInsnNode(LSTORE, `var`)
    fun lload(`var`: Int) = +VarInsnNode(LLOAD, `var`)
    fun fstore(`var`: Int) = +VarInsnNode(FSTORE, `var`)
    fun fload(`var`: Int) = +VarInsnNode(FLOAD, `var`)
    fun dstore(`var`: Int) = +VarInsnNode(DSTORE, `var`)
    fun dload(`var`: Int) = +VarInsnNode(DLOAD, `var`)
    fun astore(`var`: Int) = +VarInsnNode(ASTORE, `var`)
    fun aload(`var`: Int) = +VarInsnNode(ALOAD, `var`)

    /* Array storing & loading */

    fun iastore() = insn(IASTORE)
    fun iaload() = insn(IALOAD)
    fun lastore() = insn(LASTORE)
    fun laload() = insn(LALOAD)
    fun fastore() = insn(FASTORE)
    fun faload() = insn(FALOAD)
    fun dastore() = insn(DASTORE)
    fun daload() = insn(DALOAD)
    fun aastore() = insn(AASTORE)
    fun aaload() = insn(AALOAD)
    fun bastore() = insn(BASTORE)
    fun baload() = insn(BALOAD)
    fun castore() = insn(CASTORE)
    fun caload() = insn(CALOAD)
    fun sastore() = insn(SASTORE)
    fun saload() = insn(SALOAD)

    /* Stack manipulation */

    fun pop() = insn(POP)
    fun pop2() = insn(POP2)
    fun dup() = insn(DUP)
    fun dup_x1() = insn(DUP_X1)
    fun dup_x2() = insn(DUP_X2)
    fun dup2() = insn(DUP2)
    fun dup2_x1() = insn(DUP2_X1)
    fun dup2_x2() = insn(DUP2_X2)
    fun swap() = insn(SWAP)

    /* Arithmetic & Bitwise */

    fun iadd() = insn(IADD)
    fun isub() = insn(ISUB)
    fun imul() = insn(IMUL)
    fun idiv() = insn(IDIV)
    fun irem() = insn(IREM)
    fun ineg() = insn(INEG)
    fun ishl() = insn(ISHL)
    fun ishr() = insn(ISHR)
    fun iushr() = insn(IUSHR)
    fun iand() = insn(IAND)
    fun ior() = insn(IOR)
    fun ixor() = insn(IXOR)
    fun iinc(`var`: Int, incr: Int) = +IincInsnNode(`var`, incr)

    fun ladd() = insn(LADD)
    fun lsub() = insn(LSUB)
    fun lmul() = insn(LMUL)
    fun ldiv() = insn(LDIV)
    fun lrem() = insn(LREM)
    fun lneg() = insn(LNEG)
    fun lshl() = insn(LSHL)
    fun lshr() = insn(LSHR)
    fun lushr() = insn(LUSHR)
    fun lor() = insn(LOR)
    fun land() = insn(LAND)
    fun lxor() = insn(LXOR)

    fun fadd() = insn(FADD)
    fun fsub() = insn(FSUB)
    fun fmul() = insn(FMUL)
    fun fdiv() = insn(FDIV)
    fun frem() = insn(FREM)
    fun fneg() = insn(FNEG)

    fun dadd() = insn(DADD)
    fun dsub() = insn(DSUB)
    fun dmul() = insn(DMUL)
    fun ddiv() = insn(DDIV)
    fun drem() = insn(DREM)
    fun dneg() = insn(DNEG)

    /* Primitive type conversion */

    fun i2l() = insn(I2L)
    fun i2f() = insn(I2F)
    fun i2d() = insn(I2D)
    fun i2b() = insn(I2B)
    fun i2c() = insn(I2C)
    fun i2s() = insn(I2S)
    fun l2i() = insn(L2I)
    fun l2f() = insn(L2F)
    fun l2d() = insn(L2D)
    fun f2i() = insn(F2I)
    fun f2l() = insn(F2L)
    fun f2d() = insn(F2D)
    fun d2i() = insn(D2I)
    fun d2l() = insn(D2L)
    fun d2f() = insn(D2F)

    /* Number comparisons */

    fun lcmp() = insn(LCMP)
    fun fcmpl() = insn(FCMPL)
    fun fcmpg() = insn(FCMPG)
    fun dcmpl() = insn(DCMPL)
    fun dcmpg() = insn(DCMPG)

    /* Jumping */

    fun goto(label: LabelNode) = +JumpInsnNode(GOTO, label)
    fun jsr(label: LabelNode) = +JumpInsnNode(JSR, label)

    fun ifeq(label: LabelNode) = +JumpInsnNode(IFEQ, label)
    fun ifne(label: LabelNode) = +JumpInsnNode(IFNE, label)
    fun iflt(label: LabelNode) = +JumpInsnNode(IFLT, label)
    fun ifle(label: LabelNode) = +JumpInsnNode(IFLE, label)
    fun ifge(label: LabelNode) = +JumpInsnNode(IFGE, label)
    fun ifgt(label: LabelNode) = +JumpInsnNode(IFGT, label)

    fun if_icmplt(label: LabelNode) = +JumpInsnNode(IF_ICMPLT, label)
    fun if_icmple(label: LabelNode) = +JumpInsnNode(IF_ICMPLE, label)
    fun if_icmpge(label: LabelNode) = +JumpInsnNode(IF_ICMPGE, label)
    fun if_icmpgt(label: LabelNode) = +JumpInsnNode(IF_ICMPGT, label)
    fun if_icmpeq(label: LabelNode) = +JumpInsnNode(IF_ICMPEQ, label)
    fun if_icmpne(label: LabelNode) = +JumpInsnNode(IF_ICMPNE, label)

    fun ifnull(label: LabelNode) = +JumpInsnNode(IFNULL, label)
    fun ifnonnull(label: LabelNode) = +JumpInsnNode(IFNONNULL, label)

//    fun tableswitch(baseNumber: Int, dflt: LabelNode, vararg targets: LabelNode) = +constructTableSwitch(baseNumber, dflt, *targets)
    fun lookupswitch(defaultLabel: LabelNode, lookup: Pair<IntArray, Array<LabelNode>>) = +LookupSwitchInsnNode(defaultLabel, lookup.first, lookup.second)

    /* Fields */

    fun getstatic(owner: String, name: String, desc: String) = +FieldInsnNode(GETSTATIC, owner, name, desc)
    fun putstatic(owner: String, name: String, desc: String) = +FieldInsnNode(PUTSTATIC, owner, name, desc)
    fun getfield(owner: String, name: String, desc: String) = +FieldInsnNode(GETFIELD, owner, name, desc)
    fun putfield(owner: String, name: String, desc: String) = +FieldInsnNode(PUTFIELD, owner, name, desc)

    /* Method invocation */

    fun invokevirtual(owner: String, name: String, desc: String, `interface`: Boolean = false) = +MethodInsnNode(INVOKEVIRTUAL, owner, name, desc, `interface`)
    fun invokespecial(owner: String, name: String, desc: String, `interface`: Boolean = false) = +MethodInsnNode(INVOKESPECIAL, owner, name, desc, `interface`)
    fun invokestatic(owner: String, name: String, desc: String, `interface`: Boolean = false) = +MethodInsnNode(INVOKESTATIC, owner, name, desc, `interface`)
    fun invokeinterface(owner: String, name: String, desc: String, `interface`: Boolean = false) = +MethodInsnNode(INVOKEINTERFACE, owner, name, desc, `interface`)

    /* Creating new instances */

    fun new(type: String) = +TypeInsnNode(NEW, type)
    fun newarray(type: Int) = +IntInsnNode(NEWARRAY, type)
    fun anewarray(desc: String) = +TypeInsnNode(ANEWARRAY, desc)
    fun newboolarray() = newarray(T_BOOLEAN)
    fun newchararray() = newarray(T_CHAR)
    fun newbytearray() = newarray(T_BYTE)
    fun newshortarray() = newarray(T_SHORT)
    fun newintarray() = newarray(T_INT)
    fun newlongarray() = newarray(T_LONG)
    fun newfloatarray() = newarray(T_FLOAT)
    fun newdoublearray() = newarray(T_DOUBLE)

    /* Array information */

    fun arraylength() = insn(ARRAYLENGTH)

    /* Throwing exceptions */

    fun athrow() = insn(ATHROW)

    /* Type checks and casts */

    fun checkcast(descriptor: String) = +TypeInsnNode(CHECKCAST, descriptor)
    fun instanceof(descriptor: String) = +TypeInsnNode(INSTANCEOF, descriptor)

    /* Returns */

    fun ireturn() = insn(IRETURN)
    fun lreturn() = insn(LRETURN)
    fun freturn() = insn(FRETURN)
    fun dreturn() = insn(DRETURN)
    fun areturn() = insn(ARETURN)
    fun _return() = insn(RETURN)


    /* Frames */
    fun frame(type: Int, numLocal: Int, local: Array<Any>?, numStack: Int, stack: Array<Any>?) = +FrameNode(type, numLocal, local, numStack, stack)
}

fun insnBuilder(builder: InsnBuilder.() -> Unit) = InsnBuilder().also(builder).list