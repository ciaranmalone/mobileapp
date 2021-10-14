package org.wit.cm

import org.wit.cm.views.ScoreBoardView
import ScoreModel
import mu.KotlinLogging
import ScoreboardMemStore

private val logger = KotlinLogging.logger {}

val playerScores = ScoreboardMemStore()
var playerScore = ScoreModel()
val scoreBoardView = ScoreBoardView()

fun main(args: Array<String>){
    logger.info { "!Launching Game!" }
    println("SpinTop v0.1")
    var input: Int

    do {
        input = scoreBoardView.menu()
        when(input) {
            1 -> playGame()
            2 -> playAgain()
            3 -> scoreBoard()
            -1 -> exitApp()
            -99 -> dummyData()
            else -> println("INVALID")
        }
        println()
    } while (input != -1)

    logger.info { "SHUTTING DOWN" }
}



fun playGame() {
    println("-- Time To Play --")

    var aPlayerScore:ScoreModel = ScoreModel()

    if(scoreBoardView.addPlayerScore(aPlayerScore)) {
        playerScores.create(aPlayerScore)
    }
    else {
        playAgain(aPlayerScore)
    }
}

fun playAgain(playerScore:ScoreModel) {
    println("Back Again?")

    var tempPlayerScore:ScoreModel = playerScore

    if(tempPlayerScore == null) {
        tempPlayerScore.userName = System.getProperty("user.name")
    }

    if (scoreBoardView.updatePlayerScore(tempPlayerScore)) {

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
    var foundUserScore: ScoreModel? = playerScores.findOne ( userName )
        return foundUserScore
}

fun scoreBoard() {
    playerScores.findAll()
}

fun dummyData() {
    playerScores.create( ScoreModel("Garry", 1))
    playerScores.create(ScoreModel("Goose", 4))

}

fun exitApp() {
    println("Exit")
}