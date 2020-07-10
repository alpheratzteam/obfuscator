package pl.alpheratzteam.obfuscator.transformer;

import org.objectweb.asm.tree.ClassNode;
import pl.alpheratzteam.obfuscator.Obfuscator;
import pl.alpheratzteam.obfuscator.util.AccessUtil;
import java.util.Map;

public class FullAccessTransformer extends Transformer
{
    public FullAccessTransformer(Obfuscator obfuscator) {
        super(obfuscator);
    }

    @Override
    public void visit(Map<String, ClassNode> classMap) {
        classMap.values().forEach(classNode -> {
            classNode.access = changeAccess(classNode.access);
            classNode.methods.forEach(methodNode -> methodNode.access = changeAccess(methodNode.access));
            classNode.fields.forEach(fieldNode -> fieldNode.access = changeAccess(fieldNode.access));
        });
    }

    private int changeAccess(int access) {
        int newAccess = ACC_PUBLIC;
        if (AccessUtil.isNative(access))
            newAccess |= ACC_NATIVE;
        if (AccessUtil.isAbstract(access))
            newAccess |= ACC_ABSTRACT;
        if (AccessUtil.isAnnotation(access))
            newAccess |= ACC_ANNOTATION;
        if (AccessUtil.isBridge(access))
            newAccess |= ACC_BRIDGE;
        if (AccessUtil.isEnum(access))
            newAccess |= ACC_ENUM;
        if (AccessUtil.isFinal(access))
            newAccess |= ACC_FINAL;
        if (AccessUtil.isInterface(access))
            newAccess |= ACC_INTERFACE;
        if (AccessUtil.isMandated(access))
            newAccess |= ACC_MANDATED;
        if (AccessUtil.isModule(access))
            newAccess |= ACC_MODULE;
        if (AccessUtil.isOpen(access))
            newAccess |= ACC_OPEN;
        if (AccessUtil.isStatic(access))
            newAccess |= ACC_STATIC;
        if (AccessUtil.isStaticPhase(access))
            newAccess |= ACC_STATIC_PHASE;
        if (AccessUtil.isStrict(access))
            newAccess |= ACC_STRICT;
        if (AccessUtil.isSuper(access))
            newAccess |= ACC_SUPER;
        if (AccessUtil.isSynchronized(access))
            newAccess |= ACC_SYNCHRONIZED;
        if (AccessUtil.isSynthetic(access))
            newAccess |= ACC_SYNTHETIC;
        if (AccessUtil.isTransient(access))
            newAccess |= ACC_TRANSIENT;
        if (AccessUtil.isTransitive(access))
            newAccess |= ACC_TRANSITIVE;
        if (AccessUtil.isVolatile(access))
            newAccess |= ACC_VOLATILE;
        if (AccessUtil.isVarargs(access))
            newAccess |= ACC_VARARGS;
        if (AccessUtil.isDeprecated(access))
            newAccess |= ACC_DEPRECATED;

        return newAccess;
    }
}