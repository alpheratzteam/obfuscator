package pl.alpheratzteam.obfuscator.transformer;

import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Label;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import pl.alpheratzteam.obfuscator.Obfuscator;
import pl.alpheratzteam.obfuscator.util.RandomUtil;
import pl.alpheratzteam.obfuscator.util.StringUtil;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @author Unix
 * @since 14.04.20
 */

public class TrashCodeTransformer extends Transformer
{
    public TrashCodeTransformer(Obfuscator obfuscator) {
        super(obfuscator);
    }

    @Override
    public void visit(Map<String, ClassNode> classMap) {
        classMap.values().forEach(classNode -> IntStream.range(0, 50).forEachOrdered(i -> classNode.methods.add(this.createMethod())));
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

        final AtomicInteger index = new AtomicInteger(10);
        final Label label1 = new Label();
        methodNode.visitLabel(label1);
        methodNode.visitLineNumber(index.getAndIncrement(), label1);
        methodNode.visitVarInsn(ILOAD, 0);
        methodNode.visitLdcInsn(300000);
        methodNode.visitInsn(IADD);
        methodNode.visitVarInsn(ISTORE, 0);

        IntStream.range(0, RandomUtil.nextInt(216) + 1).mapToObj(i -> new Label()).forEachOrdered(label2 -> {
            methodNode.visitLabel(label2);
            methodNode.visitLineNumber(index.getAndIncrement(), label2);
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