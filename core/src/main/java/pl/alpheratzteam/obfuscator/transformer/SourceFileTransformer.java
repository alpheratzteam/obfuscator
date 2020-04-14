package pl.alpheratzteam.obfuscator.transformer;

import org.objectweb.asm.tree.ClassNode;
import pl.alpheratzteam.obfuscator.Obfuscator;
import pl.alpheratzteam.obfuscator.util.StringUtil;

/**
 * @author Unix
 * @since 14.04.20
 */

public class SourceFileTransformer extends Transformer
{
    public SourceFileTransformer(Obfuscator obfuscator) {
        super(obfuscator);
    }

    @Override
    public void visit(ClassNode classNode) {
        classNode.sourceFile = StringUtil.generateString(Byte.MAX_VALUE);
    }
}