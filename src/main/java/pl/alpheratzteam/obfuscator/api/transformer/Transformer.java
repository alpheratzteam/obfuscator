package pl.alpheratzteam.obfuscator.api.transformer;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

import java.util.Map;

/**
 * @author Unix on 31.08.2019.
 */
public interface Transformer extends Opcodes {

    void transform(Map<String, ClassNode> classMap);

    String getName();

}