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
    private final Map<String, Set<MethodNode>> methods = new HashMap<>();

    public MethodCallTransformer(Obfuscator obfuscator) {
        super(obfuscator);
    }

    @Override
    public void visit(ClassNode classNode) {
        Optional<ClassNode> mainClass = Optional.empty();
        if (classNode.methods.stream().anyMatch(methodNode -> methodNode.name.equals("main")
                || methodNode.name.equals("premain")
                || methodNode.name.equals("agentmain"))) {
            mainClass = Optional.of(classNode);
        }

        mainClass.ifPresent(clazz -> {
            final Set<MethodNode> methodSet = new HashSet<>();
            classNode.methods
                    .stream()
                    .filter(methodNode -> AccessUtil.isStatic(methodNode.access))
                    .forEach(methodSet::add);

            methods.put(classNode.name, methodSet);

            methods.forEach((key, value) -> {
                if (key.equals("<init>") && !classNode.name.equals(key))
                    return;

                clazz.methods.addAll(value);
                classNode.methods.removeAll(value);
            });

            classNode.methods
                    .stream()
                    .filter(methodNode -> !methodNode.name.equals("<init>"))
                    .forEach(methodNode -> Arrays.stream(methodNode.instructions.toArray())
                            .filter(ain -> ain instanceof MethodInsnNode)
                            .forEachOrdered(ain -> {
                                final MethodInsnNode methodInsnNode = (MethodInsnNode) ain;
                                if (!this.isLibrary(classNode.methods, methodInsnNode.name))
                                    return;

                                methodNode.instructions.insertBefore(ain, new MethodInsnNode(INVOKESTATIC, clazz.name, methodInsnNode.name, methodInsnNode.desc, false));
                                methodNode.instructions.remove(ain);
                            }));
        });
    }

    private boolean isLibrary(@NotNull List<MethodNode> methodNodes, String methodName) {
        return methodNodes
                .stream()
                .anyMatch(methodNode -> methodNode.name.equals(methodName));
    }
}