package y2023.day2

import tools.readListOfStringFromInput

class GameState(
        val id: Int,
        var red: Int = 0,
        var blue: Int = 0,
        var green: Int = 0
){
    override fun toString(): String {
        return "GameState(id=$id, red=$red, blue=$blue, green=$green)"
    }
    fun power() = red * blue * green
}

object CubeGame {
    @JvmStatic
    fun main(args: Array<String>) {
        val example = readListOfStringFromInput("y2023/day2/example.txt")
        val input = readListOfStringFromInput("y2023/day2/input.txt")
        val games = input.map{parseStat(it)}
        // PART
        println("Part 1: Sum of ID's ${games.filter { it.red <= 12 && it.green <= 13 && it.blue <= 14 }.sumOf { it.id }}")
        println("Part 2: Sum of powers ${games.sumOf { it.power() }}")
    }

    // Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
    private fun parseStat(gameString: String): GameState {
        val gameId = gameString.takeWhile { it != ':'  }
        val state = GameState(gameId.substringAfterLast(" ").toInt())
        gameString.substring(gameId.length +1).splitToSequence(";", ",").forEach {
            val split = it.trim().split(" ")
            when(split[1])
            {
                "red" -> state.red = maxOf(state.red, split[0].toInt())
                "blue" -> state.blue = maxOf(state.blue, split[0].toInt())
                "green" -> state.green = maxOf(state.green, split[0].toInt())
            }
        }
        return state
    }
}