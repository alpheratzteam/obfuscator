package _uwu.unix.obfuscator.core.transformer;

import _uwu.unix.obfuscator.api.transformer.Transformer;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.ClassNode;

import java.util.Collections;
import java.util.Map;

/**
 * @author Unix on 01.09.2019.
 */
public class ShuffleMemberTransformer implements Transformer {
    @Override
    public void transform(@NotNull Map<String, ClassNode> classMap) {
        classMap.values().forEach(classNode -> {
            if (classNode.methods != null) {
                Collections.shuffle(classNode.methods);
            }

            if (classNode.fields != null) {
                Collections.shuffle(classNode.fields);
            }
        });
    }

    @Override
    public String getName() {
        return "ShuffleMember";
    }
}