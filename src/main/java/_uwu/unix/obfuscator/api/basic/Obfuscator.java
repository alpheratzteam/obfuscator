package _uwu.unix.obfuscator.api.basic;

import org.objectweb.asm.tree.ClassNode;

import java.util.Map;

/**
 * @author Unix on 31.08.2019.
 */
public interface Obfuscator {

    void onLoad();

    Map<String, ClassNode> getClassMap();

    Map<String, byte[]> getFileMap();

}