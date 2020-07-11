package pl.alpheratzteam.obfuscator.transformer.optimizers;

import org.objectweb.asm.tree.ClassNode;
import pl.alpheratzteam.obfuscator.Obfuscator;
import pl.alpheratzteam.obfuscator.transformer.Transformer;
import java.util.Arrays;
import java.util.Map;

public class NopRemoverTransformer extends Transformer
{
    public NopRemoverTransformer(Obfuscator obfuscator) {
        super(obfuscator);
    }

    @Override
    public void visit(Map<String, ClassNode> classMap) {
        classMap.values().forEach(classNode ->
                classNode.methods.forEach(methodNode ->
                        Arrays.stream(methodNode.instructions.toArray())
                                .filter(insnNode -> insnNode.getOpcode() == NOP)
                                .forEach(insnNode -> methodNode.instructions.remove(insnNode))));
    }
}