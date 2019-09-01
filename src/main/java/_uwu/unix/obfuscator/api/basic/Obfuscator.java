package _uwu.unix.obfuscator.api.basic;

import _uwu.unix.obfuscator.api.configuration.Configuration;
import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author Unix on 31.08.2019.
 */
public interface Obfuscator {

    void onLoad();

    Logger getLogger();

    File getDataFolder();

    Configuration getConfiguration();

    Map<String, ClassNode> getClassMap();

    Map<String, byte[]> getFileMap();

}