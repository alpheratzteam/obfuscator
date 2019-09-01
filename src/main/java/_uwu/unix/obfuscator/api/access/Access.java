package _uwu.unix.obfuscator.api.access;

import org.objectweb.asm.Opcodes;

/**
 * @author Unix on 01.09.2019.
 */
public interface Access extends Opcodes {

    boolean isPublic();

    boolean isPrivate();

    boolean isProtected();

    boolean isStatic();

    boolean isFinal();

    boolean isSynchronized();

    boolean isSuper();

    boolean isBridge();

    boolean isTransient();

    boolean isNative();

    boolean isInterface();

    boolean isAbstract();

    boolean isStrict();

    boolean isSynthetic();

    boolean isAnnotation();

    boolean isEnum();

    boolean isMandated();

    boolean isModule();

    boolean isDeprecated();

}