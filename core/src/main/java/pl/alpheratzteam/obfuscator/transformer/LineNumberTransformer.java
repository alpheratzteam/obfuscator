package pl.alpheratzteam.obfuscator.transformer;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LineNumberNode;
import pl.alpheratzteam.obfuscator.Obfuscator;
import pl.alpheratzteam.obfuscator.util.RandomUtil;

import java.util.Arrays;

/**
 * @author Unix
 * @since 14.04.20
 */

public class LineNumberTransformer extends Transformer
{
    public LineNumberTransformer(Obfuscator obfuscator) {
        super(obfuscator);
    }

    @Override
    public void visit(ClassNode classNode) {
        classNode.methods.forEach(methodNode ->
                Arrays.stream(methodNode.instructions.toArray()).forEachOrdered(ain -> {
            try {
                final AbstractInsnNode current = ain.getNext();
                if (current == null)
                    return;
                if (!(current instanceof LineNumberNode))
                    return;

                methodNode.instructions.iterator().set(new LineNumberNode(RandomUtil.nextInt(), ((LineNumberNode) current).start));
            } catch (Exception ex) {
            }
        }));
    }
}