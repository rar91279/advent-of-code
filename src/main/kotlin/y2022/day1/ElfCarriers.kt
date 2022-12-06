package y2022.day1

import tools.readTextFromInput

fun main() {
    val example = readTextFromInput("y2022/day1/example.txt")
    val testData = readTextFromInput("y2022/day1/test_input1.txt")
    val testData2 = readTextFromInput("y2022/day1/test_input2.txt")


    fun topNElfs(n:Int, data:String): Int {
        val lineSeparator = System.lineSeparator()
        return data.split("$lineSeparator$lineSeparator")
            .map { elf -> elf.lines().sumOf { it.toInt() } }
            .sortedDescending()
            .take(n)
            .sum()
    }

    fun partOne(string: String):Int {
        return topNElfs(1, string)
    }

    fun partTwo(string: String):Int {
        return topNElfs(3, string)
    }



    val exampleOne = partOne(example)
    println(exampleOne)
    check(exampleOne == 24000)

    val exampleTwo = partTwo(example)
    println(exampleTwo)
    check(exampleTwo == 45000)


    val testDataOne = topNElfs(1, testData)
    val testDataTwo = topNElfs(3, testData2)
    println(testDataOne)
    check(testDataOne == 68442)
    println(testDataTwo)

}