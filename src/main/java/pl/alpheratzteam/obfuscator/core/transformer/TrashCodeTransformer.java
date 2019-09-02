package pl.alpheratzteam.obfuscator.core.transformer;

import pl.alpheratzteam.obfuscator.api.transformer.Transformer;
import pl.alpheratzteam.obfuscator.api.util.RandomUtil;
import pl.alpheratzteam.obfuscator.api.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Label;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @author Unix on 01.09.2019.
 */
public class TrashCodeTransformer implements Transformer {
    @Override
    public void transform(@NotNull Map<String, ClassNode> classMap) {
        classMap.values().forEach(classNode -> IntStream.range(0, 50).forEachOrdered(i -> classNode.methods.add(this.createMethod())));
    }

    @Override
    public String getName() {
        return "TrashCode";
    }

    @NotNull
    private MethodNode createMethod() {
        final MethodNode methodNode = new MethodNode((RandomUtil.nextInt(2) == 1 ? ACC_PUBLIC | ACC_STATIC : ACC_PRIVATE | ACC_STATIC), StringUtil.generateString(RandomUtil.nextInt(16) + 2), "()V", null, null);
        methodNode.visitCode();

        final Label label0 = new Label();
        methodNode.visitLabel(label0);
        methodNode.visitLineNumber(8, label0);
        methodNode.visitInsn(ICONST_0);
        methodNode.visitVarInsn(ISTORE, 0);

        final AtomicInteger atomicInteger = new AtomicInteger(10);

        final Label label1 = new Label();
        methodNode.visitLabel(label1);
        methodNode.visitLineNumber(atomicInteger.getAndIncrement(), label1);
        methodNode.visitVarInsn(ILOAD, 0);
        methodNode.visitLdcInsn(300000);
        methodNode.visitInsn(IADD);
        methodNode.visitVarInsn(ISTORE, 0);

        IntStream.range(0, RandomUtil.nextInt(216) + 1).mapToObj(i -> new Label()).forEachOrdered(label2 -> {
            methodNode.visitLabel(label2);
            methodNode.visitLineNumber(atomicInteger.getAndIncrement(), label2);
            methodNode.visitVarInsn(ILOAD, 0);
            methodNode.visitLdcInsn(300000);
            methodNode.visitInsn(IADD);
            methodNode.visitVarInsn(ISTORE, 0);
        });

        final Label label3 = new Label();
        methodNode.visitLabel(label3);
        methodNode.visitLineNumber(66, label3);
        methodNode.visitInsn(RETURN);

        final Label label58 = new Label();
        methodNode.visitLabel(label58);
        methodNode.visitLocalVariable("i", "I", null, label1, label58, 0);
        methodNode.visitMaxs(2, 1);
        methodNode.visitEnd();

        return methodNode;
    }
}