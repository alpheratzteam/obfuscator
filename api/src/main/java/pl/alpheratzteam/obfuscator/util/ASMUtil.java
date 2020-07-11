package pl.alpheratzteam.obfuscator.util;

import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import pl.alpheratzteam.obfuscator.exception.ObfuscatorException;

import java.util.Objects;

/**
 * @author Unix
 * @since 21.03.2020
 */

public final class ASMUtil
{
    private ASMUtil() {
    }

    public static MethodNode findMethod(@NotNull ClassNode classNode, String name, String desc) {
        return classNode.methods
                .stream()
                .filter(methodNode -> name.equals(methodNode.name) && desc.equals(methodNode.desc))
                .findAny()
                .orElse(null);
    }

    @NotNull
    public static MethodNode findOrCreateClinit(ClassNode classNode) {
        MethodNode clinit = findMethod(classNode, "<clinit>", "()V");
        if (Objects.isNull(clinit)) {
            clinit = new MethodNode(Opcodes.ACC_STATIC, "<clinit>", "()V", null, null);
            clinit.instructions.add(new InsnNode(Opcodes.RETURN));
            classNode.methods.add(clinit);
        }

        return clinit;
    }

    public static boolean isIntInsn(AbstractInsnNode insn) {
        if (Objects.isNull(insn))
            return false;

        final int opcode = insn.getOpcode();
        return ((opcode >= Opcodes.ICONST_M1 && opcode <= Opcodes.ICONST_5)
                || opcode == Opcodes.BIPUSH
                || opcode == Opcodes.SIPUSH
                || (insn instanceof LdcInsnNode
                && ((LdcInsnNode) insn).cst instanceof Integer)
        );
    }

    public static AbstractInsnNode getNumberInsn(int number) {
        if (number >= -1 && number <= 5)
            return new InsnNode(number + 3);
        else if (number >= -128 && number <= 127)
            return new IntInsnNode(Opcodes.BIPUSH, number);
        else if (number >= -32768 && number <= 32767)
            return new IntInsnNode(Opcodes.SIPUSH, number);

        return new LdcInsnNode(number);
    }

    public static int getIntegerFromInsn(AbstractInsnNode insn) {
        final int opcode = insn.getOpcode();
        if (opcode >= Opcodes.ICONST_M1 && opcode <= Opcodes.ICONST_5)
            return opcode - 3;
        else if (insn instanceof IntInsnNode && insn.getOpcode() != Opcodes.NEWARRAY)
            return ((IntInsnNode) insn).operand;
        else if (insn instanceof LdcInsnNode && ((LdcInsnNode) insn).cst instanceof Integer)
            return (Integer) ((LdcInsnNode) insn).cst;

        throw new ObfuscatorException("Unexpected instruction");
    }
}