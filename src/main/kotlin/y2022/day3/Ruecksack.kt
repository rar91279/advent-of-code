package y2022.day3

import tools.readListOfStringFromInput
import java.util.stream.Collectors.toSet

fun main() {
    val exampleInput = readListOfStringFromInput("y2022/day3/example.txt")
    check(calculatePriorities(exampleInput) == 157)
    
    val input = readListOfStringFromInput("y2022/day3/input.txt")
    println(calculatePriorities(input))
    println(calculatePriorities2(input))
}

fun calculatePriorities2(stringList: List<String>): Int {
    return stringList.chunked(3).sumOf { partList ->
        partList[0].filter { partList[1].contains(it) }.filter {partList[2].contains(it)}.toSet().sumOf { it.priority() }
    }
}

fun calculatePriorities(exampleInput: List<String>): Int {
    return exampleInput.sumOf { ruecksack ->
        val start = 0;
        val end = ruecksack.length
        val firstPart = ruecksack.substring(start, end / 2)
        val secondPart = ruecksack.substring(end / 2, end)
        firstPart.filter { secondPart.contains(it) }.toSet().sumOf { it.priority() }
    }
}

fun Char.priority():Int {
    val res =  if(this.isLowerCase()){
        this - 'a' + 1
    }else {
        this - 'A' + 27
    }
    return res;
}
