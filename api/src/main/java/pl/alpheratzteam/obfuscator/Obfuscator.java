package pl.alpheratzteam.obfuscator;

import org.objectweb.asm.tree.ClassNode;

import java.util.Map;
import java.util.logging.Logger;

/**
 * @author Unix
 * @since 21.03.2020
 */

public interface Obfuscator
{
    void onStart();

    Logger getLogger();

    Map<String, ClassNode> getClassMap();

    Map<String, byte[]> getFileMap();

}