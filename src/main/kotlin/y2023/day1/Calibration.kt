package y2023.day1

import tools.readListOfStringFromInput
import tools.readTextFromInput
import java.lang.Character.isDigit

object Calibration {
    val example = readListOfStringFromInput("y2023/day1/example.txt")
    val testData = readListOfStringFromInput("y2023/day1/input.txt")

    @JvmStatic
    fun main(args: Array<String>) {
        val resp = testData.asSequence().map {
            findDigitPaar(it)
        }.sum()
        println(resp)
    }

    private fun findDigitPaar(it: String): Int {
        var from = 0
        var to = it.length - 1
        while(!isDigit(it[from]) && from < to){
            from++
        }
        while (!isDigit(it[to]) && to >= from){
            to--
        }
        val s = "${it[from]}${it[to]}"
        // println("$it -> $s")
        return s.toInt()
    }

}