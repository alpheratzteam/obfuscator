package pl.alpheratzteam.obfuscator.transformer;

import org.objectweb.asm.tree.ClassNode;
import pl.alpheratzteam.obfuscator.Obfuscator;

import java.util.Collections;

/**
 * @author Unix
 * @since 14.04.20
 */

public class ShuffleMemberTransformer extends Transformer
{
    public ShuffleMemberTransformer(Obfuscator obfuscator) {
        super(obfuscator);
    }

    @Override
    public void visit(ClassNode classNode) {
        if (classNode.methods != null)
            Collections.shuffle(classNode.methods);

        if (classNode.fields != null)
            Collections.shuffle(classNode.fields);
    }
}