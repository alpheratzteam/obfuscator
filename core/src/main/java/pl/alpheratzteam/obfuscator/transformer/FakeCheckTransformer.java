package pl.alpheratzteam.obfuscator.transformer;

import org.objectweb.asm.tree.*;
import pl.alpheratzteam.obfuscator.Obfuscator;
import java.util.Map;
import java.util.stream.IntStream;

public class FakeCheckTransformer extends Transformer
{
    // TODO: 12.07.2020 add this in every scrap of code, (spam code)
    /**
     * like:
     * if (!bool) {
     *     if (!bool) {
     *     }
     * }
     * System.out.println("hello, world");
     * if (!bool) {
     *     if (!bool) {
     *     }
     * }
     */

    public FakeCheckTransformer(Obfuscator obfuscator) {
        super(obfuscator);
    }

    @Override
    public void visit(Map<String, ClassNode> classMap) {
        classMap.values().forEach(classNode -> classNode.methods
                .stream()
                .filter(methodNode -> !methodNode.name.startsWith("<"))
                .forEach(methodNode -> {
            methodNode.instructions.insertBefore(methodNode.instructions.getLast(), generateInstructions());
        }));
    }
    
    private InsnList generateInstructions() {
        final InsnList insnList = new InsnList();
        insnList.add(new LabelNode()); //first label
        insnList.add(new InsnNode(ICONST_1));
        insnList.add(new VarInsnNode(ISTORE, 0));

        {
            final LabelNode labelNode1 = new LabelNode(),
                    labelNode2 = new LabelNode(),
                    labelNode3 = new LabelNode();

            insnList.add(labelNode1);
            insnList.add(new VarInsnNode(ILOAD, 0));
            insnList.add(new JumpInsnNode(IFEQ, labelNode2));
            insnList.add(labelNode3);
            insnList.add(new InsnNode(RETURN));
            insnList.add(labelNode2);
            insnList.add(new FrameNode(F_APPEND, 1, new Object[] { INTEGER }, 0, null));
        }

        IntStream.range(0, 200).forEach(i -> { // TODO: 12.07.2020 value 200 - config? by default 6
            final LabelNode labelNode1 = new LabelNode(),
                            labelNode2 = new LabelNode();
            
            insnList.add(new VarInsnNode(ILOAD, 0));
            insnList.add(new JumpInsnNode(IFEQ, labelNode1));
            insnList.add(labelNode2);
            insnList.add(new InsnNode(RETURN));
            insnList.add(labelNode1);
            insnList.add(new FrameNode(F_SAME, 0, new Object[] { INTEGER }, 0, null));
        });

        return insnList;
    }
}