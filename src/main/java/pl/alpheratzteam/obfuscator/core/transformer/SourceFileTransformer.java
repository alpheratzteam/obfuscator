package pl.alpheratzteam.obfuscator.core.transformer;

import pl.alpheratzteam.obfuscator.api.transformer.Transformer;
import pl.alpheratzteam.obfuscator.api.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.ClassNode;

import java.util.Map;

/**
 * @author Unix on 01.09.2019.
 */
public class SourceFileTransformer implements Transformer {
    @Override
    public void transform(@NotNull Map<String, ClassNode> classMap) {
        classMap.values().forEach(classNode -> classNode.sourceFile = StringUtil.generateString(128));
    }

    @Override
    public String getName() {
        return "SourceFile";
    }
}