package y2022.day6

import tools.readTextFromInput

private val line1 = readTextFromInput("y2022/day6/input1")

fun main() {
    check(findSignalMarker("bvwbjplbgvbhsrlpgdmjqwftvncz") == 5)
    check(findSignalMarker("nppdvjthqldpwncqszvftbrmjlhg") == 6)
    check(findSignalMarker("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg") == 10)
    check(findSignalMarker("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw") == 11)

    println("SignalMarker = ${findSignalMarker(line1)}")

    check(findMessageMarker("mjqjpqmgbljsphdztnvjfqwrcgsmlb") == 19)
    check(findMessageMarker("bvwbjplbgvbhsrlpgdmjqwftvncz") == 23)
    check(findMessageMarker("nppdvjthqldpwncqszvftbrmjlhg") == 23)
    check(findMessageMarker("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg") == 29)
    check(findMessageMarker("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw") == 26)

    println("Message marker = ${findMessageMarker(line1)}")
}

fun findSignalMarker(signalSeq: String) = findIndex(signalSeq, 4)
fun findMessageMarker(signalSeq: String) = findIndex(signalSeq, 14)


fun findIndex(signalSeq: String, windowSize: Int): Int {
    val takeWhile = signalSeq.windowed(windowSize, 1, false).takeWhile { it.toSet().size < windowSize }
    return takeWhile.size + windowSize
}
