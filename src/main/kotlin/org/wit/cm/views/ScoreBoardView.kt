package org.wit.cm.views

import ScoreModel
import ScoreboardMemStore

class ScoreBoardView {

    fun menu() : Int {
        var option: Int
        var input: String?

        println("Main Menu")
        println(" 1. Play!")
        println(" 2. Play Again!")
        println(" 3. List Scoreboard")
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

    fun listScoreboards(playerScores: ScoreboardMemStore) {
        println("--ALL SCORE--")
        playerScores.logAll()
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

        playerScore.score = if (input.toIntOrNull() != null && !input.isEmpty())
            input.toInt()
        else
            0

        return playerScore.score != null
    }

    fun updatePlayerScore(playerScore: ScoreModel) : Boolean {

        println("Back Again?")

        if(playerScore != null) {

            print("${playerScore.userName} Enter yourScore: ")
            var input = readLine()!!

            playerScore.score = if (input.toIntOrNull() != null && input.isNotEmpty())
                input.toInt()
            else
                0

            return true
        }

        return false
    }
}