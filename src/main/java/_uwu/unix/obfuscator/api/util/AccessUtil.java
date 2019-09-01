package _uwu.unix.obfuscator.api.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static org.objectweb.asm.Opcodes.*;

/**
 * @author Unix on 01.09.2019.
 */
public final class AccessUtil {
    
    @Contract(pure = true)
    private AccessUtil() {
    }
    
    @Contract(pure = true)
    public static boolean isPublic(int access) {
        return (access & ACC_PUBLIC) != 0;
    }
    
    @Contract(pure = true)
    public static boolean isProtected(int access) {
        return (access & ACC_PROTECTED) != 0;
    }

    @Contract(pure = true)
    public static boolean isPrivate(int access) {
        return (access & ACC_PRIVATE) != 0;
    }
    
    @Contract(pure = true)
    public static boolean isStatic(int access) {
        return (access & ACC_STATIC) != 0;
    }
    
    @Contract(pure = true)
    public static boolean isNative(int access) {
        return (access & ACC_NATIVE) != 0;
    }

    @Contract(pure = true)
    public static boolean isAbstract(int access) {
        return (access & ACC_ABSTRACT) != 0;
    }

    @Contract(pure = true)
    public static boolean isFinal(int access) {
        return (access & ACC_FINAL) != 0;
    }

    @Contract(pure = true)
    public static boolean isSynthetic(int access) {
        return (access & ACC_SYNTHETIC) != 0;
    }

    @Contract(pure = true)
    public static boolean isVolatile(int access) {
        return (access & ACC_VOLATILE) != 0;
    }

    @Contract(pure = true)
    public static boolean isBridge(int access) {
        return (access & ACC_BRIDGE) != 0;
    }

    @Contract(pure = true)
    public static boolean isSynchronized(int access) {
        return (access & ACC_SYNCHRONIZED) != 0;
    }

    @Contract(pure = true)
    public static boolean isInterface(int access) {
        return (access & ACC_INTERFACE) != 0;
    }

    @Contract(pure = true)
    public static boolean isEnum(int access) {
        return (access & ACC_ENUM) != 0;
    }

    @Contract(pure = true)
    public static boolean isAnnotation(int access) {
        return (access & ACC_ANNOTATION) != 0;
    }

    @Contract(pure = true)
    public static boolean isDeprecated(int access) {
        return (access & ACC_DEPRECATED) != 0;
    }

    @Contract(pure = true)
    public static boolean isVoid(@NotNull String desc) {
        return desc.endsWith("V");
    }

    @Contract(pure = true)
    public static boolean isBoolean(@NotNull String desc) {
        return desc.endsWith("Z");
    }

    @Contract(pure = true)
    public static boolean isChar(@NotNull String desc) {
        return desc.endsWith("C");
    }

    @Contract(pure = true)
    public static boolean isByte(@NotNull String desc) {
        return desc.endsWith("B");
    }

    @Contract(pure = true)
    public static boolean isShort(@NotNull String desc) {
        return desc.endsWith("S");
    }

    @Contract(pure = true)
    public static boolean isInt(@NotNull String desc) {
        return desc.endsWith("I");
    }

    @Contract(pure = true)
    public static boolean isFloat(@NotNull String desc) {
        return desc.endsWith("F");
    }

    @Contract(pure = true)
    public static boolean isLong(@NotNull String desc) {
        return desc.endsWith("J");
    }

    @Contract(pure = true)
    public static boolean isDouble(@NotNull String desc) {
        return desc.endsWith("D");
    }

    @Contract(pure = true)
    public static boolean isArray(@NotNull String desc) {
        return desc.startsWith("[");
    }

    @Contract(pure = true)
    public static boolean isObject(@NotNull String desc) {
        return desc.endsWith(";");
    }

    @Contract(pure = true)
    public static boolean isMethodReturnTypeGeneric(@NotNull String desc) {
        return desc.contains(")T");
    }

    @Contract("_, null -> false; null, !null -> false")
    public static boolean isFieldGeneric(String desc, String signature) {
        return signature != null && desc != null
                && signature.startsWith("T")
                && signature.endsWith(";")
                && Character.isUpperCase(signature.charAt(1))
                && desc.contains("java/lang/Object");

    }
}