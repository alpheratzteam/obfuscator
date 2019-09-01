package _uwu.unix.obfuscator.core.access;

import _uwu.unix.obfuscator.api.access.Access;
import _uwu.unix.obfuscator.api.exception.ObfuscatorException;
import org.jetbrains.annotations.Contract;
import org.objectweb.asm.tree.FieldNode;

/**
 * @author Unix on 01.09.2019.
 */
public class FieldAccess implements Access {
    
    private final FieldNode fieldNode;

    @Contract(pure = true)
    public FieldAccess(FieldNode fieldNode) {
        this.fieldNode = fieldNode;
    }

    @Override
    public boolean isPublic() {
        return (ACC_PUBLIC & fieldNode.access) != 0;
    }

    @Override
    public boolean isPrivate() {
        return (ACC_PRIVATE & fieldNode.access) != 0;
    }

    @Override
    public boolean isProtected() {
        return (ACC_PROTECTED & fieldNode.access) != 0;
    }

    @Override
    public boolean isStatic() {
        return (ACC_STATIC & fieldNode.access) != 0;
    }

    @Override
    public boolean isFinal() {
        return (ACC_FINAL & fieldNode.access) != 0;
    }

    @Override
    public boolean isSuper() {
        throw new ObfuscatorException();
    }

    @Override
    public boolean isSynchronized() {
        throw new ObfuscatorException();
    }

    @Override
    public boolean isBridge() {
        throw new ObfuscatorException();
    }

    @Override
    public boolean isTransient() {
        return (ACC_TRANSIENT & fieldNode.access) != 0;
    }

    @Override
    public boolean isNative() {
        throw new ObfuscatorException();
    }

    @Override
    public boolean isInterface() {
        throw new ObfuscatorException();
    }

    @Override
    public boolean isAbstract() {
        throw new ObfuscatorException();
    }

    @Override
    public boolean isStrict() {
        throw new ObfuscatorException();
    }

    @Override
    public boolean isSynthetic() {
        return (ACC_SYNTHETIC & fieldNode.access) != 0;
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
        return (ACC_DEPRECATED & fieldNode.access) != 0;
    }
}