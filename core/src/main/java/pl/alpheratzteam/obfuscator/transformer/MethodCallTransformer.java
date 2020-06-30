package pl.alpheratzteam.obfuscator.transformer;

import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import pl.alpheratzteam.obfuscator.Obfuscator;
import pl.alpheratzteam.obfuscator.util.AccessUtil;
import java.util.*;

/**
 * @author Unix
 * @since 14.04.20
 */

public class MethodCallTransformer extends Transformer
{
    public MethodCallTransformer(Obfuscator obfuscator) {
        super(obfuscator);
    }

    @Override
    public void visit(Map<String, ClassNode> classMap) {
        final Map<String, Set<MethodNode>> methods = new HashMap<>();
        final Optional<ClassNode> mainClass = classMap.values()
                .stream()
                .filter(classNode -> classNode.methods
                        .stream()
                        .anyMatch(methodNode -> methodNode.name.equals("main") || methodNode.name.equals("premain")))
                .findFirst();

        mainClass.ifPresent(clazz -> {
            classMap.values().forEach(classNode -> {
                if (mainClass.get().name.equals(classNode.name))
                    return;

                final Set<MethodNode> methodSet = new HashSet<>();
                classNode.methods
                        .stream()
                        .filter(methodNode -> AccessUtil.isStatic(methodNode.access))
                        .forEach(methodSet::add);

                methods.put(classNode.name, methodSet);
            });

            methods.forEach((key, value) -> {
                if (key.equals("<init>"))
                    return;

                clazz.methods.addAll(value);
                classMap.values().stream()
                        .filter(classNode -> classNode.name.equals(key))
                        .findFirst()
                        .ifPresent(aClass -> aClass.methods.removeAll(value));
            });

            classMap.values().forEach(classNode -> classNode.methods.forEach(methodNode -> {
                if (!classNode.name.equals(clazz.name) && methodNode.name.equals("<init>"))
                    return;

                Arrays.stream(methodNode.instructions.toArray()).forEachOrdered(ain -> {
                    if (!(ain instanceof MethodInsnNode))
                        return;

                    final MethodInsnNode methodInsnNode = (MethodInsnNode) ain;
                    if (!this.isLibrary(classNode.methods, methodInsnNode.name))
                        return;

                    methodNode.instructions.insertBefore(ain, new MethodInsnNode(INVOKESTATIC, clazz.name, methodInsnNode.name, methodInsnNode.desc, false));
                    methodNode.instructions.remove(ain);
                });
            }));
        });
    }

    private boolean isLibrary(@NotNull List<MethodNode> methodNodes, String methodName) {
        return methodNodes
                .stream()
                .anyMatch(methodNode -> methodNode.name.equals(methodName));
    }
}