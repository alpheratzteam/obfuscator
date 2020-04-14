package pl.alpheratzteam.obfuscator.transformer;

import org.objectweb.asm.tree.ClassNode;
import pl.alpheratzteam.obfuscator.Obfuscator;
import pl.alpheratzteam.obfuscator.util.StringUtil;

/**
 * @author Unix
 * @since 14.04.20
 */

public class SignatureTransformer extends Transformer
{
    public SignatureTransformer(Obfuscator obfuscator) {
        super(obfuscator);
    }

    @Override
    public void visit(ClassNode classNode) {
        classNode.signature = StringUtil.makeUnreadable(StringUtil.generateString(4));
    }
}