package pl.alpheratzteam.obfuscator.core.transformer;

import pl.alpheratzteam.obfuscator.api.transformer.Transformer;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;

import java.util.Arrays;
import java.util.ListIterator;
import java.util.Map;

/**
 * @author Unix on 01.09.2019.
 */
public class GotoTransformer implements Transformer {
    @Override
    public void transform(@NotNull Map<String, ClassNode> classMap) {
        classMap.values().forEach(classNode -> classNode.methods.forEach(methodNode -> {
            Arrays.stream(methodNode.instructions.toArray()).forEachOrdered(ain -> {
                final AbstractInsnNode current = ain.getNext();

                if (current == null) {
                    return;
                }

                if (current.getOpcode() == GOTO && current instanceof LabelNode) {
                    return;
                }

                final LabelNode labelNode = new LabelNode();

                methodNode.instructions.iterator().add(new JumpInsnNode(GOTO, labelNode));
                methodNode.instructions.iterator().add(labelNode);
            });
        }));
    }

    @Override
    public String getName() {
        return "Goto";
    }
}