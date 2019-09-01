package _uwu.unix.obfuscator.core.transformer;

import _uwu.unix.obfuscator.api.transformer.Transformer;
import _uwu.unix.obfuscator.api.util.RandomUtil;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LineNumberNode;

import java.util.ListIterator;
import java.util.Map;

/**
 * @author Unix on 01.09.2019.
 */
public class LineNumberTransformer implements Transformer {
    @Override
    public void transform(@NotNull Map<String, ClassNode> classMap) {
        classMap.values().forEach(classNode -> classNode.methods.forEach(methodNode -> {
            try {
                ListIterator<AbstractInsnNode> iterator = methodNode.instructions.iterator();
                AbstractInsnNode next;

                while (iterator.hasNext()) {
                    next = iterator.next();

                    if (!(next instanceof LineNumberNode)) {
                        continue;
                    }

                    iterator.set(new LineNumberNode(RandomUtil.nextInt(), ((LineNumberNode) next).start));
                }
            } catch (Exception ignored) {}
        }));
    }

    @Override
    public String getName() {
        return "LineNumber";
    }
}