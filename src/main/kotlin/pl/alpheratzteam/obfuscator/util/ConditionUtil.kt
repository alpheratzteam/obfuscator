package pl.alpheratzteam.obfuscator.util

/**
 * @author Unix
 * @since 30.03.2021
 */

object ConditionUtil {

    fun checkCondition(condition: Boolean, runnable: Runnable) {
        if (!condition) {
            return
        }

        runnable.run()
    }

}