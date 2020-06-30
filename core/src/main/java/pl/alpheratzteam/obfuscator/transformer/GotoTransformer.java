package pl.alpheratzteam.obfuscator.transformer;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import pl.alpheratzteam.obfuscator.Obfuscator;
import java.util.Arrays;
import java.util.Map;

/**
 * @author Unix
 * @since 14.04.20
 */

public class GotoTransformer extends Transformer
{
    public GotoTransformer(Obfuscator obfuscator) {
        super(obfuscator);
    }

    @Override
    public void visit(Map<String, ClassNode> classMap) {
        classMap.values().forEach(classNode -> classNode.methods.forEach(methodNode ->
                Arrays.stream(methodNode.instructions.toArray()).forEachOrdered(ain -> {
                    final AbstractInsnNode current = ain.getNext();
                    if (current == null)
                        return;
                    if (current.getOpcode() == GOTO && current instanceof LabelNode)
                        return;

                    final LabelNode labelNode = new LabelNode();
                    methodNode.instructions.iterator().add(new JumpInsnNode(GOTO, labelNode));
                    methodNode.instructions.iterator().add(labelNode);
                })));
    }
}