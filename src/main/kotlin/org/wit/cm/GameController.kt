package org.wit.cm

import ScoreModel
import mu.KotlinLogging
import org.wit.cm.models.ScoreboardJSONStore
import org.wit.cm.views.ScoreBoardView
import java.text.DecimalFormat
import kotlin.random.Random
import kotlin.system.measureTimeMillis


private val logger = KotlinLogging.logger {}
val playerScores =  ScoreboardJSONStore()
val scoreBoardView = ScoreBoardView()

val words = listOf<String>("banana", "apple", "carrot", "grape")
val df = DecimalFormat("#.####")


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
                2 -> scoreBoard()
                3 -> adminPanel()
                -1 -> exitApp()
                -99 -> dummyData()
                else -> println("INVALID")
            }
            println()
        } while (input != -1)

        logger.info { "SHUTTING DOWN" }
    }

    private fun playGame() {
        val playerScore:ScoreModel = ScoreModel()

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

        println("Time taken: ${df.format(timeINMillis/1000.0)}s")
        println("Enter NAME: (3 LETTERS OR LESS)")

        var displayname =""
        var incorrect = true
        while (incorrect) {

            displayname = readLine()!!
            if (displayname.length <= 3){
                incorrect = false
            } else
            {
                println("NAME MUST BE 3 LETTERS OR LESS")
            }
        }

        playerScore.displayName = displayname.toUpperCase()
        playerScore.userName = name
        playerScore.score = timeINMillis
        playerScore.typedWord = word

        playerScores.create(playerScore)
        playerScores.getRank(timeINMillis, name)

        println("You Ranked ${playerScores.getRank(timeINMillis, name)} place")

        playerScores.logTopTen()
    }

    private fun adminPanel() {
        scoreBoardView.listAdminOptions()
    }

    private fun scoreBoard() {
        scoreBoardView.listScoreboards(playerScores)
    }

    private fun dummyData() {
        playerScores.create( ScoreModel("3213", "GGG", "Garry",  "carrot",2223))
        playerScores.create(ScoreModel("1234", "HHH", "Goose", "grape",1222))
        playerScores.create( ScoreModel("2222", "LOL", "Garry",  "carrot",2222))
        playerScores.create(ScoreModel("3333", "GUY", "Goose", "grape",1332))
    }

    private fun exitApp() {
        println("Exit")
    }
}