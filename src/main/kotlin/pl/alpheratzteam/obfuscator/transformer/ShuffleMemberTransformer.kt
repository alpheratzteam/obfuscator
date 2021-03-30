package pl.alpheratzteam.obfuscator.transformer

import pl.alpheratzteam.obfuscator.Obfuscator
import pl.alpheratzteam.obfuscator.api.transformer.Transformer

/**
 * @author Unix
 * @since 18.12.2020
 */

class ShuffleMemberTransformer : Transformer {

    override fun transform(obfuscator: Obfuscator) {
        obfuscator.classes.values.forEach {
            it.attrs?.shuffle()
            it.methods?.shuffle()
            it.methods?.forEach {
                it.localVariables?.shuffle()
                it.parameters?.shuffle()
                it.attrs?.shuffle()
            }
            it.fields?.shuffle()
            it.fields?.forEach { it.attrs?.shuffle() }
        }
    }

}