package org.wit.cm.views

import ScoreModel
import ScoreboardMemStore
import org.wit.cm.models.ScoreboardJSONStore
import org.wit.cm.scoreBoardView
val playerScores =  ScoreboardJSONStore()
class ScoreBoardView {

    fun menu() : Int {
        var option: Int
        var input: String?

        println("Main Menu")
        println(" 1. Play!")
        println(" 2. List Scoreboard")
        println("-1. Exit")
        println()
        println("Enter an integer : ")
        input = readLine()!!
        option = if (input.toIntOrNull() != null && input.isNotEmpty())
            input.toInt()
        else
            -9
        return option
    }

    fun listScoreboards(playerScores: ScoreboardJSONStore) {
        println("--ALL SCORE--")
        playerScores.logAll()

        do {
         var input = scoreMenu()
        when(input) {
            1 -> showPlayerScore()
            2 -> showWordTypeScore()
            -1 -> print("back to menu")
            else -> println("INVALID")
        }
        println()
    } while (input != -1)

    }

    private fun showPlayerScore(){
        println("")
        println("type PlayerName: ")

        val input = readLine()!!

        println("\n $input's scores ")
        playerScores.showPlayer(input)
    }

    private fun showWordTypeScore(){
        println("")
        println("type word type: ")

        val input = readLine()!!

        println("\n $input's scores ")
        playerScores.wordType(input)
    }

    private fun scoreMenu(): Int {
        var option: Int
        var input: String?

        println("")
        println("Score Menu Options: ")
        println(" 1. Search for Player")
        println(" 2. Show my Score")
        println("-1. Exit")
        println()
        println("Enter an integer : ")

        input = readLine()!!

        option = if (input.toIntOrNull() != null && input.isNotEmpty())
            input.toInt()
        else
            -9
        return option
    }

    fun showPlayerScore(playerScore: ScoreModel) {
        if (playerScore != null)
            println("Score Detail: [$playerScore]")
        else
            print("Score not Found")
    }

    fun addPlayerScore(playerScore: ScoreModel): Boolean {
        println("-- Time To Play --")

        if(playerScore.userName == System.getProperty("user.name")) {
            return false
        }

        playerScore.userName = System.getProperty("user.name")
        print("${playerScore.userName} Enter yourScore: ")
        var input = readLine()!!

        return playerScore.score != null
    }

    fun updatePlayerScore(playerScore: ScoreModel) : Boolean {
        println("Back Again?")
        if(playerScore != null) {

            print("${playerScore.userName} Enter yourScore: ")
            var input = readLine()!!

            playerScore.score = if (input.toIntOrNull() != null && input.isNotEmpty())
                input.toLong()
            else
                0

            return true
        }

        return false
    }
}