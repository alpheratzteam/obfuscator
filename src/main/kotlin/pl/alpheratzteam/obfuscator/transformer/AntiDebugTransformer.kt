package pl.alpheratzteam.obfuscator.transformer

import org.objectweb.asm.Opcodes.*
import pl.alpheratzteam.obfuscator.Obfuscator
import pl.alpheratzteam.obfuscator.api.transformer.Transformer
import pl.alpheratzteam.obfuscator.util.insnBuilder
import org.objectweb.asm.tree.LabelNode
import org.objectweb.asm.tree.MethodInsnNode
import org.objectweb.asm.tree.MethodNode
import pl.alpheratzteam.obfuscator.util.StringUtil

/**
 * @author Unix
 * @since 17.12.2020
 */

class AntiDebugTransformer : Transformer {

    private val debugTypes = arrayOf("-Xbootclasspath", "-Xdebug", "-agentlib", "-Xrunjdwp:", "-verbose")

    override fun transform(obfuscator: Obfuscator) {
        obfuscator.classes.values.forEach { classNode ->
            classNode.methods.find { it.name.equals("<clinit>") }.also {
                val methodNode = makeMethod()
                it?.instructions?.insertBefore(it.instructions.last, MethodInsnNode(INVOKESTATIC, classNode.name, methodNode.name, methodNode.desc, false))
                classNode.methods.add(methodNode)
            }
        }
    }

    private fun makeMethod(): MethodNode {
        val method = MethodNode()
        with(method) {
            access = ACC_PRIVATE or ACC_STATIC
            name = StringUtil.generateString(8)
            desc = "()V"
            signature = null
            exceptions = null
            maxStack = 2
            instructions = insnBuilder {
                invokestatic("java/lang/management/ManagementFactory", "getRuntimeMXBean", "()Ljava/lang/management/RuntimeMXBean;", false)
                invokeinterface("java/lang/management/RuntimeMXBean", "getInputArguments", "()Ljava/util/List;", true)
                invokeinterface("java/util/List", "iterator", "()Ljava/util/Iterator;", true)
                astore(1)
                val label1 = LabelNode()
                +label1
                frame(F_APPEND, 1, arrayOf("java/util/Iterator"), 0, null)
                aload(1)
                invokeinterface("java/util/Iterator", "hasNext", "()Z", true)
                val label2 = LabelNode()
                ifeq(label2)
                aload(1)
                invokeinterface("java/util/Iterator", "next", "()Ljava/lang/Object;", true)
                checkcast("java/lang/String")
                astore(2)
                +LabelNode()
                aload(2)
                ldc("-javaagent:")
                invokevirtual("java/lang/String", "startsWith", "(Ljava/lang/String;)Z", false)
                val label4 = LabelNode()
                debugTypes.forEach {
                    ifne(label4)
                    aload(2)
                    ldc(it)
                    invokevirtual("java/lang/String", "startsWith", "(Ljava/lang/String;)Z", false)
                }
                val label5 = LabelNode()
                ifeq(label5)
                +label4
                frame(F_APPEND, 1, arrayOf("java/lang/String"), 0, null)
                insn(ICONST_0)
                invokestatic("java/lang/System", "exit", "(I)V", false)
                +label5
                frame(F_CHOP, 1, null, 0, null)
                goto(label1)
                +label2
                frame(F_CHOP, 1, null, 0, null)
                _return()
            }
        }
        return method
    }

}