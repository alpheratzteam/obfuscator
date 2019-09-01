package _uwu.unix.obfuscator.core.transformer;

import _uwu.unix.obfuscator.api.transformer.Transformer;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;

import java.util.ListIterator;
import java.util.Map;

/**
 * @author Unix on 01.09.2019.
 */
public class GotoTransformer implements Transformer {
    @Override
    public void transform(@NotNull Map<String, ClassNode> classMap) {
        classMap.values().forEach(classNode -> classNode.methods.forEach(methodNode -> {
            final ListIterator<AbstractInsnNode> iterator = methodNode.instructions.iterator();

            while (iterator.hasNext()) {
                final AbstractInsnNode abstractInsnNode = iterator.next();

                if (abstractInsnNode.getOpcode() == GOTO && abstractInsnNode instanceof LabelNode) {
                    continue;
                }

                final LabelNode labelNode = new LabelNode();

                iterator.add(new JumpInsnNode(GOTO, labelNode));
                iterator.add(labelNode);
            }
        }));
    }

    @Override
    public String getName() {
        return "Goto";
    }
}