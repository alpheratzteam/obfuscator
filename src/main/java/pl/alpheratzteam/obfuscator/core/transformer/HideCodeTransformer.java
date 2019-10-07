package pl.alpheratzteam.obfuscator.core.transformer;

import pl.alpheratzteam.obfuscator.api.transformer.Transformer;

import pl.alpheratzteam.obfuscator.api.util.AccessUtil;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.ClassNode;

import java.util.Map;

/**
 * @author Unix on 31.08.2019.
 */
public class HideCodeTransformer implements Transformer {
    @Override
    public void transform(@NotNull Map<String, ClassNode> classMap) {
        classMap.values().forEach(classNode -> {
            if (!(AccessUtil.isSynthetic(classNode.access) && classNode.visibleAnnotations == null)) {
                classNode.access |= ACC_SYNTHETIC;
            }

            classNode.methods.forEach(methodNode -> {
                if (!AccessUtil.isSynthetic(methodNode.access)) {
                    methodNode.access |= ACC_SYNTHETIC;
                }

                if (!methodNode.name.startsWith("<") && AccessUtil.isBridge(methodNode.access)) {
                    methodNode.access |= ACC_BRIDGE;
                }
            });

            classNode.fields.forEach(fieldNode -> {
                if (!AccessUtil.isSynthetic(fieldNode.access)) {
                    fieldNode.access |= ACC_SYNTHETIC;
                }
            });
        });
    }

    @Override
    public String getName() {
        return "HideCode";
    }
}