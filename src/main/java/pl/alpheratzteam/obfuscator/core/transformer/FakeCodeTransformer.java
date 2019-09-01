package pl.alpheratzteam.obfuscator.core.transformer;

import pl.alpheratzteam.obfuscator.api.transformer.Transformer;
import pl.alpheratzteam.obfuscator.api.util.RandomUtil;
import pl.alpheratzteam.obfuscator.api.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Label;
import org.objectweb.asm.tree.*;

import java.util.Map;
import java.util.stream.IntStream;

/**
 * @author Unix on 01.09.2019.
 */
public class FakeCodeTransformer implements Transformer {
    @Override
    public void transform(@NotNull Map<String, ClassNode> classMap) {
        classMap.values().forEach(classNode -> IntStream.range(0, 5).forEachOrdered(i -> classNode.methods.add(this.createMethod())));
    }

    @Override
    public String getName() {
        return "FakeCode";
    }
    
    @NotNull
    private MethodNode createMethod() {
        final MethodNode methodNode = new MethodNode((RandomUtil.nextInt(2) == 1 ? ACC_PUBLIC | ACC_STATIC : ACC_PRIVATE | ACC_STATIC), StringUtil.generateString(16), "()V", null, null);
        methodNode.visitCode();
        
        final Label label0 = new Label();
        final Label label1 = new Label();
        final Label label2 = new Label();
        methodNode.visitTryCatchBlock(label0, label1, label2, "java/io/IOException");

        final Label label3 = new Label();
        methodNode.visitLabel(label3);
        methodNode.visitLineNumber(13, label3);
        methodNode.visitLdcInsn("hello, world");
        methodNode.visitVarInsn(ASTORE, 0);

        final Label label4 = new Label();
        methodNode.visitLabel(label4);
        methodNode.visitLineNumber(14, label4);
        methodNode.visitLdcInsn("how are u");
        methodNode.visitVarInsn(ASTORE, 1);

        final Label label5 = new Label();
        methodNode.visitLabel(label5);
        methodNode.visitLineNumber(16, label5);
        methodNode.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        methodNode.visitLdcInsn("hello, world");
        methodNode.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

        final Label label6 = new Label();
        methodNode.visitLabel(label6);
        methodNode.visitLineNumber(17, label6);
        methodNode.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        methodNode.visitLdcInsn("how are u");
        methodNode.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

        final Label label7 = new Label();
        methodNode.visitLabel(label7);
        methodNode.visitLineNumber(19, label7);
        methodNode.visitTypeInsn(NEW, "java/lang/Throwable");
        methodNode.visitInsn(DUP);
        methodNode.visitMethodInsn(INVOKESPECIAL, "java/lang/Throwable", "<init>", "()V", false);
        methodNode.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Throwable", "getStackTrace", "()[Ljava/lang/StackTraceElement;", false);
        methodNode.visitVarInsn(ASTORE, 2);

        final Label label8 = new Label();
        methodNode.visitLabel(label8);
        methodNode.visitLineNumber(21, label8);
        methodNode.visitIntInsn(SIPUSH, 128);
        methodNode.visitIntInsn(NEWARRAY, T_BYTE);
        methodNode.visitVarInsn(ASTORE, 3);

        final Label label9 = new Label();
        methodNode.visitLabel(label9);
        methodNode.visitLineNumber(23, label9);
        methodNode.visitInsn(ICONST_0);
        methodNode.visitVarInsn(ISTORE, 4);

        final Label label10 = new Label();
        methodNode.visitLabel(label10);
        methodNode.visitFrame(F_FULL, 5, new Object[]{"java/lang/String", "java/lang/String", "[Ljava/lang/StackTraceElement;", "[B", INTEGER}, 0, new Object[]{});
        methodNode.visitVarInsn(ILOAD, 4);
        methodNode.visitIntInsn(SIPUSH, 128);

        final Label label11 = new Label();
        methodNode.visitJumpInsn(IF_ICMPGE, label11);

        final Label label12 = new Label();
        methodNode.visitLabel(label12);
        methodNode.visitLineNumber(24, label12);
        methodNode.visitVarInsn(ALOAD, 3);
        methodNode.visitVarInsn(ILOAD, 4);
        methodNode.visitVarInsn(ILOAD, 4);
        methodNode.visitIntInsn(BIPUSH, 12);
        methodNode.visitInsn(IAND);
        methodNode.visitIntInsn(BIPUSH, 48);
        methodNode.visitInsn(IXOR);
        methodNode.visitIntInsn(BIPUSH, 12);
        methodNode.visitInsn(IOR);
        methodNode.visitInsn(I2B);
        methodNode.visitInsn(BASTORE);

        final Label label13 = new Label();
        methodNode.visitLabel(label13);
        methodNode.visitLineNumber(25, label13);
        methodNode.visitVarInsn(ALOAD, 3);
        methodNode.visitInsn(ICONST_2);
        methodNode.visitIntInsn(BIPUSH, 12);
        methodNode.visitIntInsn(BIPUSH, 43);
        methodNode.visitVarInsn(ILOAD, 4);
        methodNode.visitInsn(IAND);
        methodNode.visitInsn(IXOR);
        methodNode.visitInsn(I2B);
        methodNode.visitInsn(BASTORE);

        final Label label14 = new Label();
        methodNode.visitLabel(label14);
        methodNode.visitLineNumber(23, label14);
        methodNode.visitIincInsn(4, 1);
        methodNode.visitJumpInsn(GOTO, label10);
        methodNode.visitLabel(label11);
        methodNode.visitLineNumber(28, label11);
        methodNode.visitFrame(F_CHOP, 1, null, 0, null);
        methodNode.visitTypeInsn(NEW, "java/io/ByteArrayOutputStream");
        methodNode.visitInsn(DUP);
        methodNode.visitMethodInsn(INVOKESPECIAL, "java/io/ByteArrayOutputStream", "<init>", "()V", false);
        methodNode.visitVarInsn(ASTORE, 4);
        methodNode.visitLabel(label0);
        methodNode.visitLineNumber(31, label0);
        methodNode.visitVarInsn(ALOAD, 4);
        methodNode.visitVarInsn(ALOAD, 3);
        methodNode.visitMethodInsn(INVOKEVIRTUAL, "java/io/ByteArrayOutputStream", "write", "([B)V", false);

        final Label label15 = new Label();
        methodNode.visitLabel(label15);
        methodNode.visitLineNumber(32, label15);
        methodNode.visitVarInsn(ALOAD, 4);
        methodNode.visitIntInsn(BIPUSH, 24);
        methodNode.visitMethodInsn(INVOKEVIRTUAL, "java/io/ByteArrayOutputStream", "write", "(I)V", false);
        methodNode.visitLabel(label1);
        methodNode.visitLineNumber(35, label1);

        final Label label16 = new Label();
        methodNode.visitJumpInsn(GOTO, label16);
        methodNode.visitLabel(label2);
        methodNode.visitLineNumber(33, label2);
        methodNode.visitFrame(F_FULL, 5, new Object[]{"java/lang/String", "java/lang/String", "[Ljava/lang/StackTraceElement;", "[B", "java/io/ByteArrayOutputStream"}, 1, new Object[]{"java/io/IOException"});
        methodNode.visitVarInsn(ASTORE, 5);

        final Label label17 = new Label();
        methodNode.visitLabel(label17);
        methodNode.visitLineNumber(34, label17);
        methodNode.visitVarInsn(ALOAD, 5);
        methodNode.visitMethodInsn(INVOKEVIRTUAL, "java/io/IOException", "printStackTrace", "()V", false);
        methodNode.visitLabel(label16);
        methodNode.visitLineNumber(37, label16);
        methodNode.visitFrame(F_SAME, 0, null, 0, null);
        methodNode.visitInsn(ICONST_3);
        methodNode.visitTypeInsn(ANEWARRAY, "java/lang/String");
        methodNode.visitInsn(DUP);
        methodNode.visitInsn(ICONST_0);
        methodNode.visitLdcInsn("xd");
        methodNode.visitInsn(AASTORE);
        methodNode.visitInsn(DUP);
        methodNode.visitInsn(ICONST_1);
        methodNode.visitLdcInsn("123");
        methodNode.visitInsn(AASTORE);
        methodNode.visitInsn(DUP);
        methodNode.visitInsn(ICONST_2);
        methodNode.visitLdcInsn("1234");
        methodNode.visitInsn(AASTORE);
        methodNode.visitVarInsn(ASTORE, 5);

        final Label label18 = new Label();
        methodNode.visitLabel(label18);
        methodNode.visitLineNumber(43, label18);
        methodNode.visitVarInsn(ALOAD, 5);
        methodNode.visitVarInsn(ASTORE, 6);
        methodNode.visitVarInsn(ALOAD, 6);
        methodNode.visitInsn(ARRAYLENGTH);
        methodNode.visitVarInsn(ISTORE, 7);
        methodNode.visitInsn(ICONST_0);
        methodNode.visitVarInsn(ISTORE, 8);

        final Label label19 = new Label();
        methodNode.visitLabel(label19);
        methodNode.visitFrame(F_FULL, 9, new Object[]{"java/lang/String", "java/lang/String", "[Ljava/lang/StackTraceElement;", "[B", "java/io/ByteArrayOutputStream", "[Ljava/lang/String;", "[Ljava/lang/String;", INTEGER, INTEGER}, 0, new Object[]{});
        methodNode.visitVarInsn(ILOAD, 8);
        methodNode.visitVarInsn(ILOAD, 7);

        final Label label20 = new Label();
        methodNode.visitJumpInsn(IF_ICMPGE, label20);
        methodNode.visitVarInsn(ALOAD, 6);
        methodNode.visitVarInsn(ILOAD, 8);
        methodNode.visitInsn(AALOAD);
        methodNode.visitVarInsn(ASTORE, 9);

        final Label label21 = new Label();
        methodNode.visitLabel(label21);
        methodNode.visitLineNumber(44, label21);
        methodNode.visitTypeInsn(NEW, "java/lang/StringBuilder");
        methodNode.visitInsn(DUP);
        methodNode.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
        methodNode.visitVarInsn(ALOAD, 9);
        methodNode.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
        methodNode.visitVarInsn(ALOAD, 9);
        methodNode.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
        methodNode.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
        methodNode.visitVarInsn(ASTORE, 9);

        final Label label22 = new Label();
        methodNode.visitLabel(label22);
        methodNode.visitLineNumber(43, label22);
        methodNode.visitIincInsn(8, 1);
        methodNode.visitJumpInsn(GOTO, label19);
        methodNode.visitLabel(label20);
        methodNode.visitLineNumber(48, label20);
        methodNode.visitFrame(F_CHOP, 3, null, 0, null);
        methodNode.visitTypeInsn(NEW, "java/util/ArrayList");
        methodNode.visitInsn(DUP);
        methodNode.visitMethodInsn(INVOKESPECIAL, "java/util/ArrayList", "<init>", "()V", false);
        methodNode.visitVarInsn(ASTORE, 6);

        final Label label23 = new Label();
        methodNode.visitLabel(label23);
        methodNode.visitLineNumber(49, label23);
        methodNode.visitVarInsn(ALOAD, 6);
        methodNode.visitIntInsn(BIPUSH, 6);
        methodNode.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
        methodNode.visitMethodInsn(INVOKEINTERFACE, "java/util/List", "add", "(Ljava/lang/Object;)Z", true);
        methodNode.visitInsn(POP);

        final Label label24 = new Label();
        methodNode.visitLabel(label24);
        methodNode.visitLineNumber(50, label24);
        methodNode.visitVarInsn(ALOAD, 6);
        methodNode.visitIntInsn(BIPUSH, 6);
        methodNode.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
        methodNode.visitMethodInsn(INVOKEINTERFACE, "java/util/List", "add", "(Ljava/lang/Object;)Z", true);
        methodNode.visitInsn(POP);

        final Label label25 = new Label();
        methodNode.visitLabel(label25);
        methodNode.visitLineNumber(51, label25);
        methodNode.visitVarInsn(ALOAD, 6);
        methodNode.visitIntInsn(BIPUSH, 6);
        methodNode.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
        methodNode.visitMethodInsn(INVOKEINTERFACE, "java/util/List", "add", "(Ljava/lang/Object;)Z", true);
        methodNode.visitInsn(POP);

        final Label label26 = new Label();
        methodNode.visitLabel(label26);
        methodNode.visitLineNumber(53, label26);
        methodNode.visitInsn(ICONST_0);
        methodNode.visitVarInsn(ISTORE, 7);

        final Label label27 = new Label();
        methodNode.visitLabel(label27);
        methodNode.visitFrame(F_APPEND, 2, new Object[]{"java/util/List", INTEGER}, 0, null);
        methodNode.visitVarInsn(ILOAD, 7);
        methodNode.visitInsn(ICONST_3);

        final Label label28 = new Label();
        methodNode.visitJumpInsn(IF_ICMPGE, label28);

        final Label label29 = new Label();
        methodNode.visitLabel(label29);
        methodNode.visitLineNumber(54, label29);
        methodNode.visitVarInsn(ALOAD, 6);
        methodNode.visitIntInsn(BIPUSH, 6);
        methodNode.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
        methodNode.visitMethodInsn(INVOKEINTERFACE, "java/util/List", "add", "(Ljava/lang/Object;)Z", true);
        methodNode.visitInsn(POP);

        final Label label30 = new Label();
        methodNode.visitLabel(label30);
        methodNode.visitLineNumber(53, label30);
        methodNode.visitIincInsn(7, 1);
        methodNode.visitJumpInsn(GOTO, label27);
        methodNode.visitLabel(label28);
        methodNode.visitLineNumber(57, label28);
        methodNode.visitFrame(F_CHOP, 1, null, 0, null);
        methodNode.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        methodNode.visitVarInsn(ALOAD, 6);
        methodNode.visitMethodInsn(INVOKEINTERFACE, "java/util/List", "isEmpty", "()Z", true);
        methodNode.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Z)V", false);

        final Label label31 = new Label();
        methodNode.visitLabel(label31);
        methodNode.visitLineNumber(58, label31);
        methodNode.visitInsn(RETURN);

        final Label label32 = new Label();
        methodNode.visitLabel(label32);
        methodNode.visitLocalVariable("i", "I", null, label10, label11, 4);
        methodNode.visitLocalVariable("e", "Ljava/io/IOException;", null, label17, label16, 5);
        methodNode.visitLocalVariable("string", "Ljava/lang/String;", null, label21, label22, 9);
        methodNode.visitLocalVariable("i", "I", null, label27, label28, 7);
        methodNode.visitLocalVariable("abc", "Ljava/lang/String;", null, label4, label32, 0);
        methodNode.visitLocalVariable("xd", "Ljava/lang/String;", null, label5, label32, 1);
        methodNode.visitLocalVariable("stackTraceElement", "[Ljava/lang/StackTraceElement;", null, label8, label32, 2);
        methodNode.visitLocalVariable("bytes", "[B", null, label9, label32, 3);
        methodNode.visitLocalVariable("byteArrayOutputStream", "Ljava/io/ByteArrayOutputStream;", null, label0, label32, 4);
        methodNode.visitLocalVariable("hi", "[Ljava/lang/String;", null, label18, label32, 5);
        methodNode.visitLocalVariable("ints", "Ljava/util/List;", "Ljava/util/List<Ljava/lang/Integer;>;", label23, label32, 6);

        methodNode.visitMaxs(5, 10);
        methodNode.visitEnd();

        return methodNode;
    }
}