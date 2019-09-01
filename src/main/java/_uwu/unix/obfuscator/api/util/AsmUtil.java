package _uwu.unix.obfuscator.api.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;

/**
 * @author Unix on 31.08.2019.
 */
public final class AsmUtil {

    @Contract(pure = true)
    private AsmUtil() {
    }

    public static MethodNode getMethod(@NotNull ClassNode classNode, String name, String desc) {
        return classNode.methods
                .stream()
                .filter(methodNode -> name.equals(methodNode.name) && desc.equals(methodNode.desc))
                .findAny()
                .orElse(null);
    }

    @NotNull
    public static MethodNode getOrCreateClinit(ClassNode classNode) {
        MethodNode clinit = getMethod(classNode, "<clinit>", "()V");

        if (clinit == null) {
            clinit = new MethodNode(Opcodes.ACC_STATIC, "<clinit>", "()V", null, null);
            clinit.instructions.add(new InsnNode(Opcodes.RETURN));
            classNode.methods.add(clinit);
        }

        return clinit;
    }

    //TODO: isSynthetic & more
}