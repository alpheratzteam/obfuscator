package pl.alpheratzteam.obfuscator.transformer

import org.objectweb.asm.Opcodes.F_SAME
import org.objectweb.asm.tree.*
import pl.alpheratzteam.obfuscator.Obfuscator
import pl.alpheratzteam.obfuscator.api.transformer.Transformer
import pl.alpheratzteam.obfuscator.util.ASMUtil
import pl.alpheratzteam.obfuscator.util.RandomUtil
import pl.alpheratzteam.obfuscator.util.StringUtil
import pl.alpheratzteam.obfuscator.util.insnBuilder
import java.io.IOException
import java.lang.annotation.AnnotationTypeMismatchException
import java.nio.BufferUnderflowException
import java.util.*
import kotlin.reflect.KClass

/**
 * @author Unix
 * @since 03.04.2021
 */

class ThrowableTransformer : Transformer {

    private val exceptions = listOf(get(Exception::class), get(IOException::class), get(BufferUnderflowException::class), get(ExceptionInInitializerError::class),
        get(ArrayIndexOutOfBoundsException::class), get(ArrayStoreException::class), get(AnnotationTypeMismatchException::class))

    override fun transform(obfuscator: Obfuscator) {
        obfuscator.classes.values.forEach {
            it.methods.filter { !it.name.startsWith("<") }.forEach { methodNode ->
                methodNode.instructions
                    .asSequence()
                    .filter { ASMUtil.isInstruction(it) && !Objects.isNull(it.next) }
                    .forEach { methodNode.instructions.insertBefore(it.next, makeInsn()) }
            }
        }
    }

    fun makeInsn() : InsnList {
        return insnBuilder {
            +LabelNode()
            val bool = RandomUtil.boolean()
            val type = RandomUtil.int(1, 3)
            when (type) {
                1 -> {
                    val string = StringUtil.generateString(2)
                    ldc(string)
                    when {
                        bool -> {
                            ldc(string + StringUtil.generateString(2))
                        }
                        else -> {
                            ldc(string)
                        }
                    }
                    invokevirtual("java/lang/String", "equals", "(Ljava/lang/Object;)Z", false)
                }
                2 -> {
                    when (RandomUtil.int(1, 3)) {
                        1 -> {
                            ldc(RandomUtil.float(1.0, 200.0))
                            when (RandomUtil.int(1, 3)) {
                                1 -> invokestatic("java/lang/Float", "isInfinite", "(F)Z", false)
                                2 -> invokestatic("java/lang/Float", "isNaN", "(F)Z", false)
                            }
                        }
                        2 -> {
                            ldc(RandomUtil.double(1.0, 200.0))
                            when (RandomUtil.int(1, 3)) {
                                1 -> invokestatic("java/lang/Double", "isInfinite", "(D)Z", false)
                                2 -> invokestatic("java/lang/Double", "isNaN", "(D)Z", false)
                            }
                        }
                    }
                }
            }
//            val string = StringUtil.generateString(2)
//            ldc(string)
//            ldc(string + StringUtil.generateString(2))
//            invokevirtual("java/lang/String", "equals", "(Ljava/lang/Object;)Z", false)
            val labelNode = LabelNode()
            when (type) {
                1 -> {
                    when {
                        bool -> {
                            ifeq(labelNode)
                        }
                        else -> {
                            ifne(labelNode)
                        }
                    }
                }
                else -> ifeq(labelNode)
            }
            val labelNode1 = LabelNode()
            +labelNode1

            when (RandomUtil.int(1, 3)) {
                1 -> {
                    val exception: String? = exceptions[RandomUtil.int(exceptions.size)]
                    exception?.let {
                        new(it)
                        dup()
                        invokespecial(it, "<init>", "()V", false)
                    }
                }
                else -> aconst_null()
            }

            athrow()
            +labelNode
            frame(F_SAME, 0, null, 0, null)
            +LabelNode()
        }
    }

    fun <T : Any> get(any: KClass<T>) = any.qualifiedName?.replace(".", "/")

}