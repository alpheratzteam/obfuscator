package pl.alpheratzteam.obfuscator.transformer;

import org.objectweb.asm.tree.ClassNode;
import pl.alpheratzteam.obfuscator.Obfuscator;
import pl.alpheratzteam.obfuscator.util.StringUtil;

import java.util.Map;

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
    public void visit(Map<String, ClassNode> classMap) {
        classMap.values().forEach(classNode -> classNode.signature = StringUtil.makeUnreadable(StringUtil.generateString(0x04)));
    }
}