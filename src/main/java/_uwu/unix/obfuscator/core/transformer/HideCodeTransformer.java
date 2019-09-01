package _uwu.unix.obfuscator.core.transformer;

import _uwu.unix.obfuscator.api.access.Access;
import _uwu.unix.obfuscator.api.transformer.Transformer;

import _uwu.unix.obfuscator.core.access.ClassAccess;
import _uwu.unix.obfuscator.core.access.FieldAccess;
import _uwu.unix.obfuscator.core.access.MethodAccess;
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
            final Access classAccess = new ClassAccess(classNode);

            if (!(classAccess.isSynthetic() && classNode.visibleAnnotations == null)) {
                classNode.access = classNode.access | ACC_SYNTHETIC;
            }

            classNode.methods.forEach(methodNode -> {
                final Access methodAccess = new MethodAccess(methodNode);

                if (!methodAccess.isSynthetic()) {
                    methodNode.access = methodNode.access | ACC_SYNTHETIC;
                }

                if (!methodNode.name.startsWith("<") && methodAccess.isBridge()) {
                    methodNode.access = methodNode.access | ACC_BRIDGE;
                }
            });

            classNode.fields.forEach(fieldNode -> {
                final Access fieldAccess = new FieldAccess(fieldNode);

                if (!fieldAccess.isSynthetic()) {
                    fieldNode.access = fieldNode.access | ACC_SYNTHETIC;
                }
            });
        });
    }

    @Override
    public String getName() {
        return "HideCode";
    }
}