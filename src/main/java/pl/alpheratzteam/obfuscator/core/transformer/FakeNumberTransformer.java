package pl.alpheratzteam.obfuscator.core.transformer;

import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.ClassNode;
import pl.alpheratzteam.obfuscator.api.transformer.Transformer;

import java.util.Map;

/**
 * @author Unix on 04.09.2019.
 */
public class FakeNumberTransformer implements Transformer {  //TODO: true == false ? fakenumber : realnumber
    @Override
    public void transform(@NotNull Map<String, ClassNode> classMap) {
        classMap.values().forEach(classNode -> {

        });
    }

    @Override
    public String getName() {
        return "FakeNumber";
    }
}