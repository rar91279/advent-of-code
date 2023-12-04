package y2023.day1

import tools.readListOfStringFromInput
import java.lang.Character.isDigit

object Calibration {
    val example = readListOfStringFromInput("y2023/day1/example.txt")
    val example2 = readListOfStringFromInput("y2023/day1/example2.txt")
    val testData = readListOfStringFromInput("y2023/day1/input.txt")

    @JvmStatic
    fun main(args: Array<String>) {
        val resp = testData.asSequence().map {
            findDigitPaar(it)
        }.sum()
        println(resp)

        println("Second Calibration sum = ${testData.sumOf { findWithSpelledDigits(it) }}")
    }

    private fun findDigitPaar(it: String): Int {
        var from = 0
        var to = it.length - 1
        while (!isDigit(it[from]) && from < to) {
            from++
        }
        while (!isDigit(it[to]) && to >= from) {
            to--
        }
        val s = "${it[from]}${it[to]}"
        // println("$it -> $s")
        return s.toInt()
    }

    val map = mapOf("one" to 1, "two" to 2, "three" to 3, "four" to 4, "five" to 5, "six" to 6, "seven" to 7, "eight" to 8, "nine" to 9)
    fun findWithSpelledDigits(row: String): Int {
        var tmp = row
        while (!isDigit(tmp.first()) && map.keys.find { tmp.startsWith(it) }.isNullOrBlank())
        {
            tmp = tmp.substring(1)
        }
        while (!isDigit(tmp.last()) && map.keys.find { tmp.endsWith(it) }.isNullOrBlank())
        {
            tmp= tmp.substring(0, tmp.length-1)
        }

        val firstDigit = if(isDigit(tmp.first())) tmp.first() else map[map.keys.find {tmp.startsWith(it)}]
        val secondDigit = if(isDigit(tmp.last())) tmp.last() else map[map.keys.find {tmp.endsWith(it)}]
        val s = "$firstDigit$secondDigit"
        // println("$it -> $s")
        return s.toInt()
    }

}