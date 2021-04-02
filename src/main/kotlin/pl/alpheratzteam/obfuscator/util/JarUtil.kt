package pl.alpheratzteam.obfuscator.util

import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.tree.ClassNode
import java.io.*
import java.lang.Exception
import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream

/**
 * @author Unix
 * @since 16.12.2020
 */

object JarUtil {

    /**
     * Loads jar file as [Pair].
     * @param file input file.
     * @return [Pair] as readed content from the file.
     */
    fun loadJar(file: File): Pair<MutableMap<String, ClassNode>, MutableMap<String, ByteArray>> {
        val classes = mutableMapOf<String, ClassNode>()
        val assets = mutableMapOf<String, ByteArray>()

        JarFile(file).use {
            val entries = it.entries()
            while (entries.hasMoreElements()) {
                val jarEntry = entries.nextElement()
                try {
                    val bytes = asByteArray(it.getInputStream(jarEntry))
                    if (!jarEntry.name.endsWith(".class")) {
                        assets[jarEntry.name] = bytes
                        continue
                    }

                    val classNode = ClassNode()
                    val classReader = ClassReader(bytes)
                    classReader.accept(classNode, ClassReader.EXPAND_FRAMES)
                    classes[classNode.name] = classNode
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
        }

        return Pair(classes, assets)
    }

    /**
     * Saves contents to jar file.
     * @param file output file.
     * @param pair content to save.
     * @throws [IOException] thrown when error is occurred.
     */
    @Throws(IOException::class)
    fun saveJar(file: File, pair: Pair<MutableMap<String, ClassNode>, MutableMap<String, ByteArray>>) {
        JarOutputStream(FileOutputStream(file)).use {
            pair.first.values.forEach { classNode ->
                val classWriter = ClassWriter(ClassWriter.COMPUTE_MAXS)
                it.putNextEntry(JarEntry(classNode.name + ".class"))
                classNode.accept(classWriter)
                it.write(classWriter.toByteArray())
                it.closeEntry()
            }

            pair.second.forEach { (key, value) ->
                run {
                    it.putNextEntry(JarEntry(key))
                    it.write(value)
                    it.closeEntry()
                }
            }
        }
    }

    /**
     * This methods reads content from [InputStream] to [ByteArray].
     * @param inputStream stream to read.
     * @return [ByteArray] readed data.
     */
    private fun asByteArray(inputStream: InputStream): ByteArray {
        return try {
            val out = ByteArrayOutputStream()

            var readed: Int
            while (inputStream.read().also { readed = it } != -1) {
                out.write(readed)
            }

            inputStream.close()
            out.toByteArray()
        } catch (ex: Exception) {
            ex.printStackTrace()
            ByteArray(0)
        }
    }

}