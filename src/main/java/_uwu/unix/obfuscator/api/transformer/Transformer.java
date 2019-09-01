package _uwu.unix.obfuscator.api.transformer;

import org.objectweb.asm.tree.ClassNode;

import java.util.Map;

/**
 * @author Unix on 31.08.2019.
 */
public interface Transformer {

    void transform(Map<String, ClassNode> classMap);

    String getName();

}