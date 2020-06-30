package pl.alpheratzteam.obfuscator.transformer;

import org.objectweb.asm.tree.ClassNode;
import pl.alpheratzteam.obfuscator.Obfuscator;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

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
    public void visit(Map<String, ClassNode> classMap) {
        classMap.values().forEach(classNode -> {
            if (Objects.nonNull(classNode.methods))
                Collections.shuffle(classNode.methods);

            if (Objects.nonNull(classNode.fields))
                Collections.shuffle(classNode.fields);
        });
    }
}