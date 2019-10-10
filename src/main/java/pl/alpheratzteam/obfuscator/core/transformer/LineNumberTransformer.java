package pl.alpheratzteam.obfuscator.core.transformer;

import pl.alpheratzteam.obfuscator.api.transformer.Transformer;
import pl.alpheratzteam.obfuscator.api.util.RandomUtil;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LineNumberNode;

import java.util.Arrays;
import java.util.ListIterator;
import java.util.Map;

/**
 * @author Unix on 01.09.2019.
 */
public class LineNumberTransformer implements Transformer {
    @Override
    public void transform(@NotNull Map<String, ClassNode> classMap) {
        classMap.values().forEach(classNode -> classNode.methods.forEach(methodNode -> {
            Arrays.stream(methodNode.instructions.toArray()).forEachOrdered(ain -> {
                try {
                    final AbstractInsnNode current = ain.getNext();

                    if (current == null) {
                        return;
                    }

                    if (!(current instanceof LineNumberNode)) {
                        return;
                    }

                    methodNode.instructions.iterator().set(new LineNumberNode(RandomUtil.nextInt(), ((LineNumberNode) current).start));
                } catch (Exception ignored) {}
            });
        }));
    }

    @Override
    public String getName() {
        return "LineNumber";
    }
}