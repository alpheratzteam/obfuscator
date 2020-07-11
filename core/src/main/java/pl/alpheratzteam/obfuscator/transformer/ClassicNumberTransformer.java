package pl.alpheratzteam.obfuscator.transformer;

import org.objectweb.asm.tree.*;
import pl.alpheratzteam.obfuscator.Obfuscator;
import pl.alpheratzteam.obfuscator.util.ASMUtil;
import pl.alpheratzteam.obfuscator.util.RandomUtil;

import java.util.Map;

public class ClassicNumberTransformer extends Transformer
{
    public ClassicNumberTransformer(Obfuscator obfuscator) {
        super(obfuscator);
    }

    @Override
    public void visit(Map<String, ClassNode> classMap) {
        classMap.values().forEach(classNode -> classNode.methods.forEach(methodNode -> {
            int sizeLeeway = obfuscator.getSizeLeeway(methodNode);
            for (AbstractInsnNode abstractInsnNode : methodNode.instructions.toArray()) {
                if (sizeLeeway < 10000)
                    break;

                if (!ASMUtil.isIntInsn(abstractInsnNode))
                    return;

                final int originalValue = ASMUtil.getIntegerFromInsn(abstractInsnNode); //original int
                final int fakeValue = RandomUtil.nextInt(); //random int
                final int xorValue = originalValue ^ fakeValue; //xor int

                final InsnList insnList = new InsnList();
                insnList.add(ASMUtil.getNumberInsn(fakeValue));
                insnList.add(ASMUtil.getNumberInsn(RandomUtil.nextInt()));
                insnList.add(new InsnNode(SWAP));
                insnList.add(new InsnNode(DUP_X1));
                insnList.add(new InsnNode(POP2));
                insnList.add(ASMUtil.getNumberInsn(xorValue));
                insnList.add(new InsnNode(IXOR));

                methodNode.instructions.insertBefore(abstractInsnNode, insnList);
                methodNode.instructions.remove(abstractInsnNode);
                sizeLeeway -= 10;
            }
        }));
    }
}