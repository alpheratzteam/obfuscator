package pl.alpheratzteam.obfuscator.transformer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import pl.alpheratzteam.obfuscator.Obfuscator;
import java.util.HashMap;
import java.util.Map;

public class DebugInfoRemoverTransformer extends Transformer
{
    public DebugInfoRemoverTransformer(Obfuscator obfuscator) {
        super(obfuscator);
    }

    @Override
    public void visit(Map<String, ClassNode> classMap) {
        final Map<String, ClassNode> map = new HashMap<>();
        classMap.values().forEach(classNode -> {
            final ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            classNode.accept(writer);

            final ClassNode newClassNode = new ClassNode();
            new ClassReader(writer.toByteArray()).accept(newClassNode, ClassReader.SKIP_DEBUG);
            map.put(newClassNode.name, newClassNode);
        });

        classMap.clear();
        classMap.putAll(map);
    }
}