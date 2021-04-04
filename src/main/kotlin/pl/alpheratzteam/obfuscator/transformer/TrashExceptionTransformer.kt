package pl.alpheratzteam.obfuscator.transformer

import pl.alpheratzteam.obfuscator.Obfuscator
import pl.alpheratzteam.obfuscator.api.transformer.Transformer
import java.io.IOException
import java.lang.annotation.AnnotationTypeMismatchException
import java.nio.BufferUnderflowException
import kotlin.reflect.KClass

/**
 * @author Unix
 * @since 04.04.2021
 */

class TrashExceptionTransformer : Transformer {

    // TODO: 04.04.2021 get automatic all exceptions from package java/util

    private val exceptions = listOf(get(Exception::class), get(IOException::class), get(BufferUnderflowException::class), get(ExceptionInInitializerError::class),
        get(ArrayIndexOutOfBoundsException::class), get(ArrayStoreException::class), get(AnnotationTypeMismatchException::class))

    override fun transform(obfuscator: Obfuscator) = obfuscator.classes.values.forEach { it.methods.forEach { it.exceptions.addAll(exceptions) } }

    fun <T : Any> get(any: KClass<T>) = any.qualifiedName?.replace(".", "/")

//    fun <T : Any> gets(classes: List<KClass<T>>) : List<String> {
//        val list = mutableListOf<String>()
//        classes.forEach { get(it)?.let { list.add(it) } }
//
//        return list
//    }

}