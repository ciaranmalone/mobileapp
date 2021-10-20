package org.wit.cm.models

import ScoreModel
import ScoreboardStore
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import mu.KotlinLogging
import org.wit.cm.helper.*
import java.text.DecimalFormat
import java.util.*

private val logger = KotlinLogging.logger {}

const val JSON_FILE = "ScoreBoard.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<ArrayList<ScoreModel>>() {}.type

val df = DecimalFormat("#.####")

class ScoreboardJSONStore : ScoreboardStore {

    var playerScores = mutableListOf<ScoreModel>()

    init {
        if(exists(JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): List<ScoreModel> {
        return playerScores
    }

    override fun findOne(userName: String): ScoreModel? {
        return playerScores.find { p -> p.userName == userName }
    }

    override fun create(playerScore: ScoreModel) {
        if(playerScore.userName.isEmpty()) {
            playerScore.userName = System.getProperty("user.name")
        }
        playerScore.UUID = UUID.randomUUID().toString();

        playerScores.add(playerScore)
        serialize()
    }

    override fun update(playerScore: ScoreModel) {
        var foundPlayerScore = findOne(System.getProperty("user.name"))
        if (foundPlayerScore != null) {
            foundPlayerScore.score = playerScore.score
        }
    }

    override fun delete(id: String) {
        this.playerScores.remove(this.playerScores.find { it.UUID == id })
    }

    internal fun logAll(admin: Boolean =false) {
        playerScores.sortBy { it.score }

        if (admin)
            printScoreboardAdmin(playerScores)
        else
            printScoreboard(playerScores)
    }

    internal fun logTopTen() {
        this.playerScores.sortBy { it.score }
        printScoreboard(this.playerScores.take(10))
    }

    internal fun showPlayer(name:String) {
        printScoreboard(this.playerScores.filter { it.userName == name })
    }

    internal fun wordType(word:String) {
        printScoreboard(this.playerScores.filter { it.typedWord == word })
    }

    internal fun displayName(word:String) {
        printScoreboard(this.playerScores.filter { it.displayName == word })
    }

    private fun printScoreboard(playerScorePrint: List<ScoreModel>) {
        if (playerScorePrint.isEmpty()) {
            println("\n --Nothing to see here-- ")
            return;
        }

        for ((index, value) in playerScorePrint.withIndex()){
            println("#${index+1} ${value.displayName}  Score: ${df.format(value.score/1000.0)}s -> ${value.typedWord}")
        }
    }

    private fun printScoreboardAdmin(playerScorePrint: List<ScoreModel>) {
        if (playerScorePrint.isEmpty()) {
            println("\n --Nothing to see here-- ")
            return;
        }

        for ((index, value) in playerScorePrint.withIndex()){
            println("#${index+1} $value ")
        }
    }

    internal  fun logFrom(start: Int) {
        playerScores.sortBy { it.score }
        for (start in start until playerScores.size) {
                println("#${start+1} ${playerScores[start].displayName}  Score: ${df.format(playerScores[start].score/1000.0)}s ${playerScores[start].displayName}")
        }
    }

    internal fun getRank(score:Long, userName:String): Int {
        playerScores.sortBy { it.score }
        return playerScores.indexOfLast {it.score == score && it.userName == userName} +1

    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(playerScores, listType)
        write(JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(JSON_FILE)
        playerScores = Gson().fromJson(jsonString, listType)
    }

    internal fun find(id:String): ScoreModel? {
        return this.playerScores.find { it.UUID == id }
    }

    internal fun printPlayerData(playerScore: ScoreModel) {
        println(println("${playerScore.userName} : ${playerScore.displayName}  Score: ${df.format(playerScore.score/1000.0)}s -> ${playerScore.typedWord}"))
    }
}