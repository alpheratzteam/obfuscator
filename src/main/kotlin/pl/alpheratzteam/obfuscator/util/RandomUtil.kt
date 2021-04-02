package pl.alpheratzteam.obfuscator.util

import java.util.concurrent.ThreadLocalRandom

/**
 * @author Unix
 * @since 17.12.2020
 */

object RandomUtil {

    private val random = ThreadLocalRandom.current()

    /**
     * Randomizes a number.
     * @param max as max int range.
     * @return [Int] randomized int value.
     */
    fun int(max: Int) = random.nextInt(max)

    /**
     * Randomizes a number.
     * @return [Int] randomized int value.
     */
    fun int() = random.nextInt()

    /**
     * Randomizes a number.
     * @return [Double] randomized double value.
     */
    fun double() = random.nextDouble()

    /**
     * Randomizes a number.
     * @param min as min value to randomize.
     * @param max as max value to randomize.
     * @return [Int] randomized int value.
     */
    fun int(min: Int, max: Int) = random.nextInt(min, max)

    /**
     * Randomizes a number.
     * @param [min] as min value to randomize.
     * @param [max] as max value to randomize.
     * @return [Double] randomized double value.
     */
    fun double(min: Double, max: Double) = random.nextDouble(min, max)

    /**
     * Randomizes the chance.
     * @param chance as chance to check.
     * @return [Boolean], if `true` chance is right.
     */
    fun chance(chance: Double) = Math.random() * 100.0 <= chance

    /**
     * Randomizes a boolean.
     * @return [Boolean] randomized boolean value.
     */
    fun boolean() = random.nextBoolean()

}