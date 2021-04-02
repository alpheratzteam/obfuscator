package pl.alpheratzteam.obfuscator.util

/**
 * @author Unix
 * @since 30.03.2021
 */

object ConditionUtil {

    /**
     * Checks some conditions.
     * if **true**, runs [Runnable].
     * @param condition as conditions to check.
     * @param runnable as runnable to run.
     * @see [Runnable]
     */
    fun checkCondition(condition: Boolean, runnable: Runnable) {
        if (!condition) {
            return
        }

        runnable.run()
    }

}