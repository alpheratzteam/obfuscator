package pl.alpheratzteam.obfuscator.core.transformer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import pl.alpheratzteam.obfuscator.api.transformer.Transformer;
import pl.alpheratzteam.obfuscator.api.util.AccessUtil;
import pl.alpheratzteam.obfuscator.api.util.RandomUtil;
import pl.alpheratzteam.obfuscator.api.util.StringUtil;

import java.util.*;

/**
 * @author Unix on 02.09.2019.
 */
public class InlineRemoverTransformer implements Transformer {
    @Override
    public void transform(@NotNull Map<String, ClassNode> classMap) {
        final Map<String, Set<MethodNode>> methods = new HashMap<>();

        classMap.values().forEach(classNode -> {
            final Set<MethodNode> methodSet = new HashSet<>();

            classNode.methods.forEach(methodNode -> Arrays.stream(methodNode.instructions.toArray()).forEach(ain -> {
                final MethodNode x = this.createMethod(ain);

                if (x == null) {
                    return;
                }

                methodSet.add(x);
                final AbstractInsnNode current = ain.getNext();

                if (AccessUtil.isStatic(x.access)) {
                    methodNode.instructions.insertBefore(current, new MethodInsnNode(INVOKESTATIC, classNode.name, x.name, x.desc, false));
                } else {
                    methodNode.instructions.insertBefore(current, new MethodInsnNode(INVOKEVIRTUAL, classNode.name, x.name, x.desc, false));
                }

                methodNode.instructions.remove(ain);
            }));

            methods.put(classNode.name, methodSet);
        });

        classMap.values().forEach(classNode -> methods.forEach((key, value) -> {
            if (!classNode.name.contains(key)) {
                return;
            }

            classNode.methods.addAll(value);
        }));
    }

    @Override
    public String getName() {
        return "InlineRemover";
    }

    @Nullable
    private MethodNode createMethod(@NotNull AbstractInsnNode ain) {
        final String string = StringUtil.generateUniqueString(16);
        MethodNode methodNode = null;

        switch (ain.getOpcode()) {
            case SIPUSH:
                methodNode = new MethodNode(ACC_PUBLIC | ACC_STATIC, string, "()I", null, null);
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
                methodNode = new MethodNode(ACC_PUBLIC | ACC_STATIC, string, "()Ljava/lang/String;", null, null);
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