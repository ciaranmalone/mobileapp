package org.wit.cm.views
import org.wit.cm.models.ScoreboardJSONStore

val playerScores =  ScoreboardJSONStore()
class ScoreBoardView {

    fun menu() : Int {
        var option: Int
        var input: String?

        println("Main Menu")
        println(" 1. Play!")
        println(" 2. List Scoreboard")
        println(" 3. Admin Panel")
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

    private fun scoreMenu(): Int {
        var option: Int
        var input: String?

        println("")
        println("Score Menu Options: ")
        println(" 1. Search by user name")
        println(" 2. Search by word type")
        println(" 3. Search by display name")
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
            3 -> showDisplayNameScore()
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

    @OptIn(ExperimentalStdlibApi::class)
    private fun showDisplayNameScore() {
        println("")
        println("type Display Name: ")

        val input = readLine()!!

        println("\n $input's scores ")
        playerScores.displayName(input.uppercase())
    }



    fun listAdminOptions() {
        println("--ALL PLAYERS INFO--")
        playerScores.logAll(true)

        do {
            var input = viewAdminOptions()
            when(input) {
                1 -> updatePlayerScore()
                2 -> deletePlayer()
                3 -> listFullData()
                -1 -> print("back to menu")
                else -> println("INVALID")
            }
            println()
        } while (input != -1)

    }

    private fun viewAdminOptions(): Int {
        var option: Int
        var input: String?

        println("")
        println("Score Menu Options: ")
        println(" 1. Update Player")
        println(" 2. Delete Player")
        println(" 3. List Full Info")
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

    private fun listFullData() {
        playerScores.logAll(true)
    }

    private fun deletePlayer() {
        println("")
        println("type Player UUID: ")

        val input = readLine()!!

        playerScores.delete(input)

        println("User deleted")
    }

    private fun updatePlayerScore()  {
        println("")
        println("type Player UUID: ")

        val input = readLine()!!

        var playerScore = playerScores.find(input)

        if (playerScore != null) {
            println()
            println("Player found:")
            playerScores.printPlayerData(playerScore)

            println(" Change ${playerScore.userName} to: ")
            playerScore.userName = readLine()!!

            println(" Change ${playerScore.displayName} to: ")
            playerScore.displayName = readLine()!!

            println(" Change ${playerScore.typedWord} to: ")
            playerScore.typedWord = readLine()!!

            println(" Change ${playerScore.score} to: ")

            playerScore.score = readLine()!!.toLong()

            println()
            playerScores.printPlayerData(playerScore)

            playerScores.update(playerScore)
            println("Player updated")
        } else {
            println("Player does not exist")
        }
    }
}