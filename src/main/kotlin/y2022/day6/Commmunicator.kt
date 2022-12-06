package y2022.day6

import tools.readTextFromInput

private val line1 = readTextFromInput("y2022/day6/input1")

fun main() {
    check(findSignalMarker("bvwbjplbgvbhsrlpgdmjqwftvncz") == 5)
    check(findSignalMarker("nppdvjthqldpwncqszvftbrmjlhg") == 6)
    check(findSignalMarker("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg") == 10)
    check(findSignalMarker("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw") == 11)

    println("SignalMarker = ${findSignalMarker(line1)}")

    check(findSignalMarker("mjqjpqmgbljsphdztnvjfqwrcgsmlb", 14) == 19)
    check(findSignalMarker("bvwbjplbgvbhsrlpgdmjqwftvncz", 14) == 23)
    check(findSignalMarker("nppdvjthqldpwncqszvftbrmjlhg", 14) == 23)
    check(findSignalMarker("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", 14) == 29)
    check(findSignalMarker("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", 14) == 26)

    println("Message marker = ${findSignalMarker(line1, 14)}" )
}

fun findSignalMarker(signalSeq: String, windowSize: Int = 4): Int {
    val takeWhile = signalSeq.windowed(windowSize, 1, false).takeWhile { it.toSet().size < windowSize }
    return takeWhile.size + windowSize
}
