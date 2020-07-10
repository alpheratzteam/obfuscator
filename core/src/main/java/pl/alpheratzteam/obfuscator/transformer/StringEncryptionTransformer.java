package pl.alpheratzteam.obfuscator.transformer;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Label;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;
import pl.alpheratzteam.obfuscator.Obfuscator;
import pl.alpheratzteam.obfuscator.util.AccessUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Unix
 * @since 14.04.20
 */

public class StringEncryptionTransformer extends Transformer
{
    public StringEncryptionTransformer(Obfuscator obfuscator) {
        super(obfuscator);
    }

    @Override
    public void visit(Map<String, ClassNode> classMap) {
        classMap.values().forEach(classNode -> {
            final MethodNode decrypt = this.createMethod(classNode);
            classNode.methods.add(decrypt);

            classNode.methods.forEach(methodNode ->
                    Arrays.stream(methodNode.instructions.toArray())
                            .filter(ain -> ain.getType() == AbstractInsnNode.LDC_INSN)
                            .map(ain -> (LdcInsnNode) ain)
                            .filter(ain -> ain.cst instanceof String)
                            .filter(ain -> ain.cst.toString().length() != 0)
                            .forEachOrdered(ain -> {
                                final AbstractInsnNode current = ain.getNext();

                                methodNode.instructions.insertBefore(current, new LdcInsnNode(encode(ain.cst.toString(), classNode.superName)));
                                methodNode.instructions.insertBefore(current, new MethodInsnNode((AccessUtil.isStatic(decrypt.access) ? INVOKESTATIC : INVOKEVIRTUAL), classNode.name, decrypt.name, decrypt.desc, false));
                                methodNode.instructions.remove(ain);
                            }));
        });
    }

    @NotNull
    private MethodNode createMethod(@NotNull ClassNode classNode) {
        final MethodNode methodNode = new MethodNode(ACC_PUBLIC | ACC_STATIC, "decode", "(Ljava/lang/String;)Ljava/lang/String;", null, null);
        methodNode.visitCode();

        final Label label0 = new Label();
        methodNode.visitLabel(label0);
        methodNode.visitLineNumber(22, label0);
        methodNode.visitVarInsn(ALOAD, 0);
        methodNode.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "getBytes", "()[B", false);
        methodNode.visitVarInsn(ASTORE, 1);

        final Label label1 = new Label();
        methodNode.visitLabel(label1);
        methodNode.visitLineNumber(23, label1);
        methodNode.visitInsn(ICONST_3);
        methodNode.visitIntInsn(NEWARRAY, T_BYTE);
        methodNode.visitVarInsn(ASTORE, 2);

        final Label label2 = new Label();
        methodNode.visitLabel(label2);
        methodNode.visitLineNumber(25, label2);
        methodNode.visitInsn(ICONST_0);
        methodNode.visitVarInsn(ISTORE, 3);

        final Label label3 = new Label();
        methodNode.visitLabel(label3);
        methodNode.visitFrame(F_APPEND, 3, new Object[]{"[B", "[B", INTEGER}, 0, null);
        methodNode.visitVarInsn(ILOAD, 3);
        methodNode.visitInsn(ICONST_3);

        final Label label4 = new Label();
        methodNode.visitJumpInsn(IF_ICMPGE, label4);

        final Label label5 = new Label();
        methodNode.visitLabel(label5);
        methodNode.visitLineNumber(26, label5);
        methodNode.visitVarInsn(ALOAD, 2);
        methodNode.visitVarInsn(ILOAD, 3);
        methodNode.visitIntInsn(BIPUSH, 127);
        methodNode.visitVarInsn(ALOAD, 0);
        methodNode.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "length", "()I", false);
        methodNode.visitVarInsn(ILOAD, 3);
        methodNode.visitInsn(IAND);
        methodNode.visitLdcInsn(Type.getType("L" + classNode.name + ";"));
        methodNode.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getSimpleName", "()Ljava/lang/String;", false);
        methodNode.visitInsn(ICONST_0);
        methodNode.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "charAt", "(I)C", false);
        methodNode.visitLdcInsn(Type.getType("L" + classNode.name + ";"));
        methodNode.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getSimpleName", "()Ljava/lang/String;", false);
        methodNode.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "length", "()I", false);
        methodNode.visitInsn(IREM);
        methodNode.visitInsn(IXOR);
        methodNode.visitInsn(IOR);
        methodNode.visitInsn(I2B);
        methodNode.visitInsn(BASTORE);

        final Label label6 = new Label();
        methodNode.visitLabel(label6);
        methodNode.visitLineNumber(25, label6);
        methodNode.visitIincInsn(3, 1);
        methodNode.visitJumpInsn(GOTO, label3);
        methodNode.visitLabel(label4);
        methodNode.visitLineNumber(29, label4);
        methodNode.visitFrame(F_CHOP, 1, null, 0, null);
        methodNode.visitVarInsn(ALOAD, 1);
        methodNode.visitInsn(ARRAYLENGTH);
        methodNode.visitIntInsn(NEWARRAY, T_BYTE);
        methodNode.visitVarInsn(ASTORE, 3);

        final Label label7 = new Label();
        methodNode.visitLabel(label7);
        methodNode.visitLineNumber(31, label7);
        methodNode.visitInsn(ICONST_0);
        methodNode.visitVarInsn(ISTORE, 4);

        final Label label8 = new Label();
        methodNode.visitLabel(label8);
        methodNode.visitFrame(F_APPEND, 2, new Object[]{"[B", INTEGER}, 0, null);
        methodNode.visitVarInsn(ILOAD, 4);
        methodNode.visitVarInsn(ALOAD, 1);
        methodNode.visitInsn(ARRAYLENGTH);

        final Label label9 = new Label();
        methodNode.visitJumpInsn(IF_ICMPGE, label9);

        final Label label10 = new Label();
        methodNode.visitLabel(label10);
        methodNode.visitLineNumber(32, label10);
        methodNode.visitVarInsn(ALOAD, 3);
        methodNode.visitVarInsn(ILOAD, 4);
        methodNode.visitVarInsn(ALOAD, 1);
        methodNode.visitVarInsn(ILOAD, 4);
        methodNode.visitInsn(BALOAD);
        methodNode.visitVarInsn(ALOAD, 2);
        methodNode.visitVarInsn(ILOAD, 4);
        methodNode.visitVarInsn(ALOAD, 2);
        methodNode.visitInsn(ARRAYLENGTH);
        methodNode.visitInsn(IREM);
        methodNode.visitInsn(BALOAD);
        methodNode.visitInsn(IXOR);
        methodNode.visitInsn(I2B);
        methodNode.visitInsn(BASTORE);

        final Label label11 = new Label();
        methodNode.visitLabel(label11);
        methodNode.visitLineNumber(31, label11);
        methodNode.visitIincInsn(4, 1);
        methodNode.visitJumpInsn(GOTO, label8);
        methodNode.visitLabel(label9);
        methodNode.visitLineNumber(35, label9);
        methodNode.visitFrame(F_CHOP, 1, null, 0, null);
        methodNode.visitTypeInsn(NEW, "java/lang/String");
        methodNode.visitInsn(DUP);
        methodNode.visitVarInsn(ALOAD, 3);
        methodNode.visitMethodInsn(INVOKESPECIAL, "java/lang/String", "<init>", "([B)V", false);
        methodNode.visitInsn(ARETURN);

        final Label label12 = new Label();
        methodNode.visitLabel(label12);
        methodNode.visitLocalVariable("i", "I", null, label3, label4, 3);
        methodNode.visitLocalVariable("i", "I", null, label8, label9, 4);
        methodNode.visitLocalVariable("string", "Ljava/lang/String;", null, label0, label12, 0);
        methodNode.visitLocalVariable("data", "[B", null, label1, label12, 1);
        methodNode.visitLocalVariable("key", "[B", null, label2, label12, 2);
        methodNode.visitLocalVariable("out", "[B", null, label7, label12, 3);
        methodNode.visitMaxs(6, 5);
        methodNode.visitEnd();

        return methodNode;
    }

    @Contract("_, _ -> new")
    @NotNull
    private String encode(@NotNull String string, String className) { //TODO: unique keys: class -> key
        final byte[] data = string.getBytes();
        final byte[] key = new byte[3];
        IntStream.range(0, 3).forEachOrdered(i -> key[i] = (byte) (127 | string.length() & i ^ className.charAt(0) % className.length()));

        final byte[] out = new byte[data.length];
        IntStream.range(0, data.length).forEachOrdered(i -> out[i] = (byte) (data[i] ^ key[i % key.length]));
        return new String(out);
    }

    @NotNull
    private List<String> getStringsFromMethod(@NotNull MethodNode methodNode) {
        return Arrays.stream(methodNode.instructions.toArray())
                .filter(ain -> ain.getType() == AbstractInsnNode.LDC_INSN)
                .map(ain -> (LdcInsnNode) ain)
                .filter(ldc -> ldc.cst instanceof String)
                .map(ldc -> ldc.cst.toString())
                .distinct()
                .collect(Collectors.toList());
    }
}