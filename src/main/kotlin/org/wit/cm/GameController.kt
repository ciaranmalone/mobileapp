package org.wit.cm

import ScoreModel
import ScoreboardMemStore
import mu.KotlinLogging
import org.wit.cm.views.ScoreBoardView


private val logger = KotlinLogging.logger {}

val playerScores = ScoreboardMemStore()
val PlayerScore:ScoreModel = ScoreModel()
val scoreBoardView = ScoreBoardView()

class GameController{

    fun init() {
        logger.info { "!Launching Game!" }
        println("SpinTop v0.1")
    }

    fun start() {
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

    private fun playGame() {
        if(scoreBoardView.addPlayerScore(PlayerScore)) {
            playerScores.create(PlayerScore)
        }
        else {
            playAgain(PlayerScore)
        }
    }

    private fun playAgain(playerScore:ScoreModel? = ScoreModel()) {
        if (playerScore != null) {
            if(playerScore.userName.isEmpty())
                playerScore.userName = System.getProperty("user.name")

            if(scoreBoardView.updatePlayerScore(playerScore)) {
                playerScores.update(playerScore)
            }
        }
    }

    private fun scoreBoard() {
        scoreBoardView.listScoreboards(playerScores)
    }

    private fun dummyData() {
        playerScores.create( ScoreModel("Garry", 1))
        playerScores.create(ScoreModel("Goose", 4))

    }

    private fun exitApp() {
        println("Exit")
    }
}