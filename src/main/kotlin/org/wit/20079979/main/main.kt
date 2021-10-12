package org.wit.`20079979`

import ScoreModel
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

var playerScores = ArrayList<ScoreModel>()
var playerScore = ScoreModel()


fun main(args: Array<String>){
    logger.info { "!Launching Game!" }
    println("SpinTop v0.1")

    var input: Int

    do {
        input = menu()
        when(input) {
            1 -> playGame()
            2 -> playAgain()
            3 -> scoreBoard()
            -1 -> exitApp()
            else -> println("INVALID")
        }
        println()
    } while (input != -1)

    logger.info { "SHUTTING DOWN" }

}

fun menu() : Int {

    var option : Int

    var input: String? = null

    println("Main Menu")
    println(" 1. Play!")
    println(" 2. Play Again!")
    println(" 3. List Scoreboard")
    println("-1. Exit")
    println()
    println("Enter an integer : ")
    input = readLine()!!
    option = if (input.toIntOrNull() != null && !input.isEmpty())
        input.toInt()
    else
        -9
    return option
}

fun playGame() {
    println("-- Time To Play --")

    if(playerScore.userName == System.getProperty("user.name")) {
        playAgain()
    }
    else {
        playerScore.userName = System.getProperty("user.name")
        print("${playerScore.userName} Enter yourScore: ")
        var input = readLine()!!

        playerScore.score = if (input.toIntOrNull() != null && !input.isEmpty())
            input.toInt()
        else
            0

        playerScores.add(playerScore)
    }
}

fun playAgain() {
    println("Back Again?")

    val oldPlayerScore = search(playerScore.userName)

    if (oldPlayerScore != null) {

        oldPlayerScore.userName = System.getProperty("user.name")
        print("${oldPlayerScore.userName} Enter yourScore: ")
        var input = readLine()!!

        oldPlayerScore.score = if (input.toIntOrNull() != null && !input.isEmpty())
            input.toInt()
        else
            0
    }
}

fun search(userName: String) : ScoreModel? {
    var foundUserScore: ScoreModel? = playerScores.find { p -> p.userName == userName }
        return foundUserScore
}

fun scoreBoard() {
    playerScores.forEach {logger.info("${it}")}
}

fun exitApp() {
    println("Exit")
}