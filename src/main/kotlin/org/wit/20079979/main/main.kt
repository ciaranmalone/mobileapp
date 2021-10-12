package org.wit.`20079979`

import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

var userName = ""
var score = 0

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

    if(userName == System.getProperty("user.name"))
        playAgain()
    else
        userName = System.getProperty("user.name")

    print("$userName Enter yourScore: ")
    var input = readLine()!!

    score = if(input.toIntOrNull() != null && !input.isEmpty())
        input.toInt()
    else
        0

//    if existing user
}

fun playAgain() {
    println("Back for more?")
}

fun scoreBoard() {
    println("Winner is you")
}

fun exitApp() {
    println("Exit")
}