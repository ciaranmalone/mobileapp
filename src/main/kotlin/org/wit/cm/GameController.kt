package org.wit.cm

import ScoreModel
import mu.KotlinLogging
import org.wit.cm.models.ScoreboardJSONStore
import org.wit.cm.views.ScoreBoardView
import kotlin.random.Random
import kotlin.system.measureTimeMillis


private val logger = KotlinLogging.logger {}

val playerScores =  ScoreboardJSONStore()
val PlayerScore:ScoreModel = ScoreModel()
val scoreBoardView = ScoreBoardView()

val words = listOf<String>("banana", "apple", "carrot", "grape")

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

        println("-- Time To Play --")
        val word = words[Random.nextInt(words.size)]
        val name = System.getProperty("user.name")?: "Player"

        println("$name type the word")
        println(">>> $word <<<")

        val timeINMillis = measureTimeMillis {
            var input =""
            var incorrect = true
            while (incorrect) {
                input = readLine()!!

                if (input != word) {
                    println("$input is not $word")
                    println("Try Again:")
                } else {
                    incorrect = false
                }
            }
        }

        println("Time taken: $timeINMillis")

        PlayerScore.userName = name
        PlayerScore.score = timeINMillis
        playerScores.create(PlayerScore)

        playerScores.getRank(timeINMillis, name)

        println("You Ranked ${playerScores.getRank(timeINMillis, name)} place")

        playerScores.logAll()

//        if(scoreBoardView.addPlayerScore(PlayerScore)) {
//            playerScores.create(PlayerScore)
//        }
//        else {
//            playAgain(PlayerScore)
//        }
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