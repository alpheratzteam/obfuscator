package pl.alpheratzteam.obfuscator.api.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static org.objectweb.asm.Opcodes.*;

/**
 * @author Unix on 01.09.2019.
 */
public final class AccessUtil {

    private AccessUtil() {
    }

    public static boolean isPublic(int access) {
        return (access & ACC_PUBLIC) != 0;
    }

    public static boolean isProtected(int access) {
        return (access & ACC_PROTECTED) != 0;
    }

    public static boolean isPrivate(int access) {
        return (access & ACC_PRIVATE) != 0;
    }

    public static boolean isStatic(int access) {
        return (access & ACC_STATIC) != 0;
    }

    public static boolean isNative(int access) {
        return (access & ACC_NATIVE) != 0;
    }

    public static boolean isAbstract(int access) {
        return (access & ACC_ABSTRACT) != 0;
    }

    public static boolean isFinal(int access) {
        return (access & ACC_FINAL) != 0;
    }

    public static boolean isSynthetic(int access) {
        return (access & ACC_SYNTHETIC) != 0;
    }

    public static boolean isVolatile(int access) {
        return (access & ACC_VOLATILE) != 0;
    }

    public static boolean isBridge(int access) {
        return (access & ACC_BRIDGE) != 0;
    }

    public static boolean isSynchronized(int access) {
        return (access & ACC_SYNCHRONIZED) != 0;
    }

    public static boolean isInterface(int access) {
        return (access & ACC_INTERFACE) != 0;
    }

    public static boolean isEnum(int access) {
        return (access & ACC_ENUM) != 0;
    }

    public static boolean isAnnotation(int access) {
        return (access & ACC_ANNOTATION) != 0;
    }

    public static boolean isDeprecated(int access) {
        return (access & ACC_DEPRECATED) != 0;
    }

    public static boolean isVoid(@NotNull String desc) {
        return desc.endsWith("V");
    }

    public static boolean isBoolean(@NotNull String desc) {
        return desc.endsWith("Z");
    }

    public static boolean isChar(@NotNull String desc) {
        return desc.endsWith("C");
    }

    public static boolean isByte(@NotNull String desc) {
        return desc.endsWith("B");
    }

    public static boolean isShort(@NotNull String desc) {
        return desc.endsWith("S");
    }

    public static boolean isInt(@NotNull String desc) {
        return desc.endsWith("I");
    }

    public static boolean isFloat(@NotNull String desc) {
        return desc.endsWith("F");
    }

    public static boolean isLong(@NotNull String desc) {
        return desc.endsWith("J");
    }

    public static boolean isDouble(@NotNull String desc) {
        return desc.endsWith("D");
    }

    public static boolean isArray(@NotNull String desc) {
        return desc.startsWith("[");
    }

    public static boolean isObject(@NotNull String desc) {
        return desc.endsWith(";");
    }

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