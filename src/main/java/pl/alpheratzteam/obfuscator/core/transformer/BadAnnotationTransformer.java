package pl.alpheratzteam.obfuscator.core.transformer;

import pl.alpheratzteam.obfuscator.api.transformer.Transformer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;

import java.util.*;
import java.util.stream.IntStream;

/**
 * @author Unix on 01.09.2019.
 */
public class BadAnnotationTransformer implements Transformer {

    private final Set<AnnotationNode> annotations;

    @Contract(pure = true)
    public BadAnnotationTransformer() {
        this.annotations = new HashSet<>();
        IntStream.range(0, 10).forEachOrdered(i -> this.annotations.add(new AnnotationNode("@")));
    }

    @Override
    public void transform(@NotNull Map<String, ClassNode> classMap) {
        classMap.values().forEach(classNode -> {
            if (classNode.visibleAnnotations == null) classNode.visibleAnnotations = new ArrayList<>();
            if (classNode.invisibleAnnotations == null) classNode.invisibleAnnotations = new ArrayList<>();

            classNode.visibleAnnotations.addAll(this.annotations);
            classNode.invisibleAnnotations.addAll(this.annotations);

            classNode.methods.forEach(methodNode -> {
                if (methodNode.visibleAnnotations == null) methodNode.visibleAnnotations = new ArrayList<>();
                if (methodNode.invisibleAnnotations == null) methodNode.invisibleAnnotations = new ArrayList<>();

                methodNode.visibleAnnotations.addAll(this.annotations);
                methodNode.invisibleAnnotations.addAll(this.annotations);
            });

            classNode.fields.forEach(fieldNode -> {
                if (fieldNode.visibleAnnotations == null) fieldNode.visibleAnnotations = new ArrayList<>();
                if (fieldNode.invisibleAnnotations == null) fieldNode.invisibleAnnotations = new ArrayList<>();

                fieldNode.visibleAnnotations.addAll(this.annotations);
                fieldNode.invisibleAnnotations.addAll(this.annotations);
            });
        });
    }

    @Override
    public String getName() {
        return "BadAnnotation";
    }
}