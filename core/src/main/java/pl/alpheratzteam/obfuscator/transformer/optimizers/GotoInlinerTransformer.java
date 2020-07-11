package pl.alpheratzteam.obfuscator.transformer.optimizers;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.JumpInsnNode;
import pl.alpheratzteam.obfuscator.Obfuscator;
import pl.alpheratzteam.obfuscator.transformer.Transformer;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public class GotoInlinerTransformer extends Transformer
{
    public GotoInlinerTransformer(Obfuscator obfuscator) {
        super(obfuscator);
    }

    @Override
    public void visit(Map<String, ClassNode> classMap) {
        classMap.values().forEach(classNode ->
                classNode.methods.forEach(methodNode ->
                        Arrays.stream(methodNode.instructions.toArray())
                                .filter(abstractInsnNode -> abstractInsnNode.getOpcode() == GOTO)
                                .forEach(abstractInsnNode -> {
                                    final JumpInsnNode gotoJump = (JumpInsnNode) abstractInsnNode;
                                    final AbstractInsnNode insnAfterTarget = gotoJump.label.getNext();
                                    if (Objects.isNull(insnAfterTarget))
                                        return;

                                    if (insnAfterTarget.getOpcode() != GOTO)
                                        return;

                                    gotoJump.label = ((JumpInsnNode) insnAfterTarget).label;
                                })
                )
        );
    }
}