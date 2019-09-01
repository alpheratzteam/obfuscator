package _uwu.unix.obfuscator.core.transformer;

import _uwu.unix.obfuscator.api.transformer.Transformer;
import static org.objectweb.asm.Opcodes.*;
import org.objectweb.asm.tree.ClassNode;

import java.util.Map;

/**
 * @author Unix on 31.08.2019.
 */
public class TestTransformer implements Transformer {
    @Override
    public void transform(Map<String, ClassNode> classMap) {
        classMap.values().forEach(classNode -> classNode.access |= ACC_SYNTHETIC);
    }

    @Override
    public String getName() {
        return "Test";
    }
}