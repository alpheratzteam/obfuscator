package pl.alpheratzteam.obfuscator.util

import java.util.concurrent.ThreadLocalRandom

/**
 * @author Unix
 * @since 17.12.2020
 */

object RandomUtil {

    private val random = ThreadLocalRandom.current()

    fun int(max: Int) = random.nextInt(max)

    fun int() = random.nextInt()

    fun double() = random.nextDouble()

    fun int(min: Int, max: Int) = random.nextInt(min, max)

    fun double(min: Double, max: Double) = random.nextDouble(min, max)

    fun chance(chance: Double) = Math.random() * 100.0 <= chance

    fun boolean() = random.nextBoolean()

}