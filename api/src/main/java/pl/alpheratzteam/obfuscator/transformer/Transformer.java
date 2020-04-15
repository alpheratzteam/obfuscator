package pl.alpheratzteam.obfuscator.transformer;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import pl.alpheratzteam.obfuscator.Obfuscator;

import java.util.Map;

/**
 * @author Unix
 * @since 21.03.2020
 */

public abstract class Transformer implements Opcodes
{
    protected final Obfuscator obfuscator;

    public Transformer(Obfuscator obfuscator) {
        this.obfuscator = obfuscator;
    }

    public abstract void visit(ClassNode classNode);

    public void after(ClassNode classNode) {
    }

}