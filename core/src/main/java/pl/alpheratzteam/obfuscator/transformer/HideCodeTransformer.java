package pl.alpheratzteam.obfuscator.transformer;

import org.objectweb.asm.tree.ClassNode;
import pl.alpheratzteam.obfuscator.Obfuscator;
import pl.alpheratzteam.obfuscator.util.AccessUtil;

/**
 * @author Unix
 * @since 14.04.20
 */

public class HideCodeTransformer extends Transformer
{
    public HideCodeTransformer(Obfuscator obfuscator) {
        super(obfuscator);
    }

    @Override
    public void visit(ClassNode classNode) {
        if (!(AccessUtil.isSynthetic(classNode.access) && classNode.visibleAnnotations == null))
            classNode.access |= ACC_SYNTHETIC;

        classNode.methods.forEach(methodNode -> {
            if (!AccessUtil.isSynthetic(methodNode.access))
                methodNode.access |= ACC_SYNTHETIC;

            if (!methodNode.name.startsWith("<") && AccessUtil.isBridge(methodNode.access))
                methodNode.access |= ACC_BRIDGE;
        });

        classNode.fields.forEach(fieldNode -> {
            if (!AccessUtil.isSynthetic(fieldNode.access))
                fieldNode.access |= ACC_SYNTHETIC;
        });
    }
}