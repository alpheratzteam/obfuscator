package _uwu.unix.obfuscator.core.access;

import _uwu.unix.obfuscator.api.access.Access;
import _uwu.unix.obfuscator.api.exception.ObfuscatorException;
import org.jetbrains.annotations.Contract;
import org.objectweb.asm.tree.MethodNode;

/**
 * @author Unix on 01.09.2019.
 */
public class MethodAccess implements Access {
    
    private final MethodNode methodNode;

    @Contract(pure = true)
    public MethodAccess(MethodNode methodNode) {
        this.methodNode = methodNode;
    }

    @Override
    public boolean isPublic() {
        return (ACC_PUBLIC & methodNode.access) != 0;
    }

    @Override
    public boolean isPrivate() {
        return (ACC_PRIVATE & methodNode.access) != 0;
    }

    @Override
    public boolean isProtected() {
        return (ACC_PROTECTED & methodNode.access) != 0;
    }

    @Override
    public boolean isStatic() {
        return (ACC_STATIC & methodNode.access) != 0;
    }

    @Override
    public boolean isFinal() {
        return (ACC_FINAL & methodNode.access) != 0;
    }

    @Override
    public boolean isSynchronized() {
        return (ACC_SYNCHRONIZED & methodNode.access) != 0;
    }

    @Override
    public boolean isSuper() {
        throw new ObfuscatorException();
    }

    @Override
    public boolean isBridge() {
        return (ACC_BRIDGE & methodNode.access) != 0;
    }

    @Override
    public boolean isTransient() {
        throw new ObfuscatorException();
    }

    @Override
    public boolean isNative() {
        return (ACC_NATIVE & methodNode.access) != 0;
    }

    @Override
    public boolean isInterface() {
        throw new ObfuscatorException();
    }

    @Override
    public boolean isAbstract() {
        return (ACC_ABSTRACT & methodNode.access) != 0;
    }

    @Override
    public boolean isStrict() {
        return (ACC_STRICT & methodNode.access) != 0;
    }

    @Override
    public boolean isSynthetic() {
        return (ACC_SYNTHETIC & methodNode.access) != 0;
    }

    @Override
    public boolean isAnnotation() {
        throw new ObfuscatorException();
    }

    @Override
    public boolean isEnum() {
        throw new ObfuscatorException();
    }

    @Override
    public boolean isMandated() {
        throw new ObfuscatorException();
    }

    @Override
    public boolean isModule() {
        throw new ObfuscatorException();
    }

    @Override
    public boolean isDeprecated() {
        return (ACC_DEPRECATED & methodNode.access) != 0;
    }
}