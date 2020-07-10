package pl.alpheratzteam.obfuscator.transformer;

import org.objectweb.asm.tree.ClassNode;
import pl.alpheratzteam.obfuscator.Obfuscator;
import java.util.Collections;
import java.util.List;
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
            shuffle(classNode.attrs);
            shuffle(classNode.methods);
            classNode.methods.forEach(methodNode -> {
                shuffle(methodNode.attrs);
                shuffle(methodNode.localVariables);
                shuffle(methodNode.parameters);
            });

            shuffle(classNode.fields);
            classNode.fields.forEach(fieldNode -> shuffle(fieldNode.attrs));
        });
    }


    private void shuffle(final List<?> list) {
        if (Objects.isNull(list))
            return;

        Collections.shuffle(list);
    }
}