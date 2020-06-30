package pl.alpheratzteam.obfuscator.transformer;

import org.objectweb.asm.Label;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import pl.alpheratzteam.obfuscator.Obfuscator;

import java.util.Map;

/**
 * @author Unix
 * @since 30.06.2020
 */

public class MarkerTransformer extends Transformer
{
    private final String methodName = "hello",
            text = "your text here";

    public MarkerTransformer(Obfuscator obfuscator) {
        super(obfuscator);
    }

    @Override
    public void visit(Map<String, ClassNode> classMap) {
        final MethodNode methodNode = createMethod();
        classMap.values().forEach(classNode -> classNode.methods.add(methodNode));
    }


    private MethodNode createMethod() {
        final MethodNode methodNode = new MethodNode(ACC_PUBLIC | ACC_STATIC, methodName, "()V", null, null);
        methodNode.visitCode();

        final Label label0 = new Label();
        methodNode.visitLabel(label0);
        methodNode.visitLineNumber(10, label0);
        methodNode.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        methodNode.visitLdcInsn(text);
        methodNode.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

        final Label label1 = new Label();
        methodNode.visitLabel(label1);
        methodNode.visitLineNumber(11, label1);
        methodNode.visitInsn(RETURN);

        methodNode.visitMaxs(2, 0);
        methodNode.visitEnd();
        return methodNode;
    }
}