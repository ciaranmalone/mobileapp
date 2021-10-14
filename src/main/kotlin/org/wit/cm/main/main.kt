package org.wit.cm

import org.wit.cm.views.ScoreBoardView
import ScoreModel
import mu.KotlinLogging
import ScoreboardMemStore

private val logger = KotlinLogging.logger {}

val playerScores = ScoreboardMemStore()
val PlayerScore:ScoreModel = ScoreModel()
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
    if(scoreBoardView.addPlayerScore(PlayerScore)) {
        playerScores.create(PlayerScore)
    }
    else {
        playAgain(PlayerScore)
    }
}

fun playAgain(playerScore:ScoreModel? = ScoreModel()) {

    if (playerScore != null) {
        if(playerScore.userName.isEmpty())
            playerScore.userName = System.getProperty("user.name")

        if(scoreBoardView.updatePlayerScore(playerScore)) {
            playerScores.update(playerScore)
        }
    }
}

fun search(userName: String): ScoreModel? {
    return playerScores.findOne(userName)
}

fun scoreBoard() {
    scoreBoardView.listScoreboards(playerScores)
}

fun dummyData() {
    playerScores.create( ScoreModel("Garry", 1))
    playerScores.create(ScoreModel("Goose", 4))

}

fun exitApp() {
    println("Exit")
}