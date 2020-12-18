package pl.alpheratzteam.obfuscator.transformer

import pl.alpheratzteam.obfuscator.Obfuscator
import pl.alpheratzteam.obfuscator.api.transformer.Transformer

/**
 * @author Unix
 * @since 18.12.2020
 */

class ShuffleMemberTransformer : Transformer {
    override fun transform(obfuscator: Obfuscator) {
        obfuscator.classes.forEach {
            val classNode = it.value
            classNode.attrs?.shuffle()
            classNode.methods?.shuffle()
            classNode.methods?.forEach {
                it.localVariables?.shuffle()
                it.parameters?.shuffle()
                it.attrs?.shuffle()
            }
            classNode.fields?.shuffle()
            classNode.fields?.forEach { it.attrs?.shuffle() }
        }
    }
}