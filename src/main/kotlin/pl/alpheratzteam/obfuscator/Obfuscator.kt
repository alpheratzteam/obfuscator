package pl.alpheratzteam.obfuscator

import org.objectweb.asm.tree.ClassNode
import pl.alpheratzteam.obfuscator.transformer.*
import pl.alpheratzteam.obfuscator.util.JarUtil
import java.io.File

/**
 * @author Unix
 * @since 16.12.2020
 */

class Obfuscator {

    private val dataFolder: File = File("obfuscator") // base folder

    var classes: MutableMap<String, ClassNode> = mutableMapOf() // classes from jar
    var assets: MutableMap<String, ByteArray> = mutableMapOf() // assets from jar

    fun onStart() {
        val jarFile = File(dataFolder, "jars").apply { // create files
            dataFolder.mkdir()
            mkdir()
        }

        JarUtil.loadJar(File(jarFile, "input.jar")).apply { // load classes, assets from jar
            println("Loading jar...")
        }.run {
            println("Loaded jar!")
            println("Starting transformers...")
            classes.putAll(first)
            assets.putAll(second)
        }

        val transformers = mutableListOf(TrashCodeTransformer()) // modifiers
        transformers.forEach {
            val name = it.javaClass.simpleName
            var time = System.currentTimeMillis()
            println("Running $name transformer...")
            it.transform(this) // modify classes
            time = System.currentTimeMillis() - time
            println("Finished running $name transformer. [$time ms]")
            println("---------------------------------------")
        }

        JarUtil.saveJar(File(jarFile, "output.jar"), Pair(classes, assets)).apply { // save output
            println("Saving jar...")
        }.run {
            println("Saved jar!")
        }
    }
}