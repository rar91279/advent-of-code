package y2022.day2

import tools.readListOfStringFromInput

sealed class Hand(val points: Int) {
    abstract operator fun compareTo(hand: Hand): Int

    class ROCK : Hand(1) {
        override fun compareTo(hand: Hand) = when (hand) {
            is ROCK -> 0
            is SCISSORS -> 1
            is PAPER -> -1
        }
    }

    class PAPER : Hand(2) {
        override fun compareTo(hand: Hand) = when (hand) {
            is ROCK -> 1
            is SCISSORS -> -1
            is PAPER -> 0
        }
    }

    class SCISSORS : Hand(3) {
        override fun compareTo(hand: Hand): Int = when (hand) {
            is ROCK -> LOSE
            is SCISSORS -> DRAW
            is PAPER -> WIN
        }
    }

    companion object {
        val ROCK = ROCK()
        val PAPER = PAPER()
        val SCISSORS = SCISSORS()

        private const val DRAW = 0
        private const val WIN = 1
        private const val LOSE = -1
    }
}

fun main() {
    // Inputs
    val foreGame = listOf(
        "A Y", "B X", "C Z"
    )
    val game = readListOfStringFromInput("y2022/day2/strategy_guide1.txt")
    // Checks
    check(play(foreGame,::parseGuide1) == 15)
    check(play(foreGame,::parseGuide2) == 12)

    println("Game 1 -> Points ${play(game, ::parseGuide1)}")
    println("Game 2 -> Point ${play(game, ::parseGuide2)}")
}

fun play(strategy: List<String>, parseFunc: (String)->Pair<Hand, Hand>?) = strategy.mapNotNull(parseFunc).sumOf { calculatePointsOfRound(it) }
fun calculatePointsOfRound(round:Pair<Hand, Hand>): Int {
    val (opponentMove, yourMove) = round
    return yourMove.points + when {
        opponentMove > yourMove -> 0
        opponentMove == yourMove -> 3
        opponentMove < yourMove -> 6
        else -> -yourMove.points
    }
}

fun parseGuide1(round: String): Pair<Hand, Hand>? = when (round) {
    "A X" -> Hand.ROCK to Hand.ROCK
    "A Y" -> Hand.ROCK to Hand.PAPER
    "A Z" -> Hand.ROCK to Hand.SCISSORS

    "B X" -> Hand.PAPER to Hand.ROCK
    "B Y" -> Hand.PAPER to Hand.PAPER
    "B Z" -> Hand.PAPER to Hand.SCISSORS

    "C X" -> Hand.SCISSORS to Hand.ROCK
    "C Y" -> Hand.SCISSORS to Hand.PAPER
    "C Z" -> Hand.SCISSORS to Hand.SCISSORS
    else -> null
}
fun parseGuide2(round: String): Pair<Hand, Hand>? = when(round){
    "A X" -> Hand.ROCK to Hand.SCISSORS         // LOSE
    "A Y" -> Hand.ROCK to Hand.ROCK             // DRAW
    "A Z" -> Hand.ROCK to Hand.PAPER            // WIN

    "B X" -> Hand.PAPER to Hand.ROCK            // LOSE
    "B Y" -> Hand.PAPER to Hand.PAPER           // DRAW
    "B Z" -> Hand.PAPER to Hand.SCISSORS        // WIN

    "C X" -> Hand.SCISSORS to Hand.PAPER        // LOSE
    "C Y" -> Hand.SCISSORS to Hand.SCISSORS     // DRAW
    "C Z" -> Hand.SCISSORS to Hand.ROCK         // WIN
    else -> null
}

