package pl.alpheratzteam.obfuscator.transformer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Label;
import org.objectweb.asm.tree.*;
import pl.alpheratzteam.obfuscator.Obfuscator;
import pl.alpheratzteam.obfuscator.util.AccessUtil;
import pl.alpheratzteam.obfuscator.util.StringUtil;
import java.util.*;

/**
 * @author Unix
 * @since 14.04.20
 */

public class InlineRemoverTransformer extends Transformer
{
    private final Map<String, Set<MethodNode>> methodSet = new HashMap<>();

    public InlineRemoverTransformer(Obfuscator obfuscator) {
        super(obfuscator);
    }

    @Override
    public void visit(Map<String, ClassNode> classMap) {
        classMap.values().forEach(classNode -> {
            final Set<MethodNode> methods = new HashSet<>();
            classNode.methods.forEach(methodNode -> Arrays.stream(methodNode.instructions.toArray()).forEach(ain -> {
                final MethodNode x = createMethod(ain);
                if (Objects.isNull(x))
                    return;

                methods.add(x);
                methodNode.instructions.insertBefore(ain.getNext(), new MethodInsnNode((AccessUtil.isStatic(x.access) ? INVOKESTATIC : INVOKEVIRTUAL), classNode.name, x.name, x.desc, false));
                methodNode.instructions.remove(ain);
            }));

            methodSet.put(classNode.name, methods);
        });

        classMap.values().forEach(classNode -> {
            if (!methodSet.containsKey(classNode.name))
                return;

            classNode.methods.addAll(methodSet.get(classNode.name));
        });
    }

    @Nullable
    private MethodNode createMethod(@NotNull AbstractInsnNode ain) {
        final String methodName = StringUtil.generateString(16);
        MethodNode methodNode = null;

        switch (ain.getOpcode()) {
            case SIPUSH:
                methodNode = new MethodNode(ACC_PUBLIC | ACC_STATIC, methodName, "()I", null, null);
                methodNode.visitCode();

                final Label label0 = new Label();
                methodNode.visitLabel(label0);
                methodNode.visitLineNumber(9, label0);
                methodNode.visitIntInsn(SIPUSH, ((IntInsnNode) ain).operand);
                methodNode.visitInsn(IRETURN);

                methodNode.visitMaxs(1, 0);
                methodNode.visitEnd();
                break;
            case LDC:
                methodNode = new MethodNode(ACC_PUBLIC | ACC_STATIC, methodName, "()Ljava/lang/String;", null, null);
                methodNode.visitCode();

                final Label label1 = new Label();
                methodNode.visitLabel(label1);
                methodNode.visitLineNumber(16, label1);
                methodNode.visitLdcInsn(((LdcInsnNode) ain).cst.toString());
                methodNode.visitInsn(ARETURN);

                methodNode.visitMaxs(1, 0);
                methodNode.visitEnd();
                break;
        }

        return methodNode;
    }
}