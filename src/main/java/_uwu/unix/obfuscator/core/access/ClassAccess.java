package _uwu.unix.obfuscator.core.access;

import _uwu.unix.obfuscator.api.access.Access;
import _uwu.unix.obfuscator.api.exception.ObfuscatorException;
import org.jetbrains.annotations.Contract;
import org.objectweb.asm.tree.ClassNode;

/**
 * @author Unix on 01.09.2019.
 */
public class ClassAccess implements Access {

    private final ClassNode classNode;

    @Contract(pure = true)
    public ClassAccess(ClassNode classNode) {
        this.classNode = classNode;
    }

    @Override
    public boolean isPublic() {
        return (ACC_PUBLIC & classNode.access) != 0;
    }

    @Override
    public boolean isPrivate() {
        return (ACC_PRIVATE & classNode.access) != 0;
    }

    @Override
    public boolean isProtected() {
        return (ACC_PROTECTED & classNode.access) != 0;
    }

    @Override
    public boolean isStatic() {
        throw new ObfuscatorException();
    }

    @Override
    public boolean isFinal() {
        return (ACC_FINAL & classNode.access) != 0;
    }

    @Override
    public boolean isSynchronized() {
        throw new ObfuscatorException();
    }

    @Override
    public boolean isSuper() {
        return (ACC_SUPER & classNode.access) != 0;
    }

    @Override
    public boolean isBridge() {
        throw new ObfuscatorException();
    }

    @Override
    public boolean isTransient() {
        throw new ObfuscatorException();
    }

    @Override
    public boolean isNative() {
        throw new ObfuscatorException();
    }

    @Override
    public boolean isInterface() {
        return (ACC_INTERFACE & classNode.access) != 0;
    }

    @Override
    public boolean isAbstract() {
        return (ACC_ABSTRACT & classNode.access) != 0;
    }

    @Override
    public boolean isStrict() {
        throw new ObfuscatorException();
    }

    @Override
    public boolean isSynthetic() {
        return (ACC_SYNTHETIC & classNode.access) != 0;
    }

    @Override
    public boolean isAnnotation() {
        return (ACC_ANNOTATION & classNode.access) != 0;
    }

    @Override
    public boolean isEnum() {
        return (ACC_ENUM & classNode.access) != 0;
    }

    @Override
    public boolean isMandated() {
        throw new ObfuscatorException();
    }

    @Override
    public boolean isModule() {
        return (ACC_MODULE & classNode.access) != 0;
    }

    @Override
    public boolean isDeprecated() {
        return (ACC_DEPRECATED & classNode.access) != 0;
    }
}