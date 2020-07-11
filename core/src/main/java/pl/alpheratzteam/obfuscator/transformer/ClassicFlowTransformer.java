package pl.alpheratzteam.obfuscator.transformer;

import org.objectweb.asm.tree.*;
import pl.alpheratzteam.obfuscator.Obfuscator;
import pl.alpheratzteam.obfuscator.util.StringUtil;
import java.util.Map;

public class ClassicFlowTransformer extends Transformer
{
    public ClassicFlowTransformer(Obfuscator obfuscator) {
        super(obfuscator);
    }

    @Override
    public void visit(Map<String, ClassNode> classMap) {
        classMap.values().forEach(classNode -> {
            final String fieldName = StringUtil.generateString(8);
            classNode.methods.forEach(methodNode -> {
                int sizeLeeway = obfuscator.getSizeLeeway(methodNode);

                for (AbstractInsnNode abstractInsnNode : methodNode.instructions.toArray()) {
                    if (sizeLeeway < 10000)
                        break;

                    if (abstractInsnNode.getOpcode() != GOTO)
                        return;

                    methodNode.instructions.insertBefore(abstractInsnNode, new FieldInsnNode(GETSTATIC, classNode.name, fieldName, "Z"));
                    methodNode.instructions.insert(abstractInsnNode, new InsnNode(ATHROW));
                    methodNode.instructions.insert(abstractInsnNode, new InsnNode(ACONST_NULL));
                    methodNode.instructions.set(abstractInsnNode, new JumpInsnNode(IFEQ, ((JumpInsnNode) abstractInsnNode).label));
                    sizeLeeway -= 7;
                }
            });

            classNode.fields.add(new FieldNode(ACC_PUBLIC + ACC_STATIC + ACC_FINAL, fieldName, "Z", null, null));
        });
    }
}