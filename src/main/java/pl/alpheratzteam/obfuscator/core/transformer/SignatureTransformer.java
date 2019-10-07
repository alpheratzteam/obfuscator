package pl.alpheratzteam.obfuscator.core.transformer;

import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.ClassNode;
import pl.alpheratzteam.obfuscator.api.transformer.Transformer;
import pl.alpheratzteam.obfuscator.api.util.StringUtil;

import java.util.Map;

/**
 * @author Unix on 07.10.2019.
 */
public class SignatureTransformer implements Transformer {
    @Override
    public void transform(@NotNull Map<String, ClassNode> classMap) {
        classMap.values().forEach(classNode -> classNode.signature = StringUtil.generateString(4));
    }

    @Override
    public String getName() {
        return "Signature";
    }
}