package pl.alpheratzteam.obfuscator.util

import java.lang.IllegalArgumentException
import java.util.concurrent.ThreadLocalRandom

/**
 * @author Unix
 * @since 17.12.2020
 */

object RandomUtil {

    private val random: ThreadLocalRandom = ThreadLocalRandom.current()

    @Throws(IllegalArgumentException::class)
    fun int(max: Int): Int {
        return random.nextInt(max)
    }

    fun int(): Int {
        return random.nextInt()
    }

    @Throws(IllegalArgumentException::class)
    fun int(min: Int, max: Int): Int {
        return random.nextInt(min, max)
    }

    fun chance(chance: Double): Boolean {
        return Math.random() * 100.0 <= chance
    }

    fun boolean(): Boolean {
        return random.nextBoolean()
    }
}