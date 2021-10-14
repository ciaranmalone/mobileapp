package org.wit.cm.models

import ScoreModel
import ScoreboardStore
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import mu.KotlinLogging
import org.wit.cm.helper.*
import java.util.*

private val logger = KotlinLogging.logger {}

val JSON_FILE = "ScoreBoard.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<ArrayList<ScoreModel>>()

private fun generateRandomId(): Long {
    return Random().nextLong()
}

class ScoreboardJSONStore: ScoreboardStore {

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
        var foundPlayerScore: ScoreModel? = playerScores.find {p -> p.userName == userName}
        return  foundPlayerScore
    }

    override fun create(playerScore: ScoreModel) {
        if(playerScore.userName.isEmpty()) {
            playerScore.userName = System.getProperty("user.name")
        }
        playerScores.add(playerScore)
    }

    override fun update(playerScore: ScoreModel) {
        var foundPlayerScore = findOne(System.getProperty("user.name"))
        if (foundPlayerScore != null) {
            foundPlayerScore.score = playerScore.score
        }
    }

    internal fun logAll() {
        playerScores.forEach {logger.info("${it}")}
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(playerScores, listType)
        write(JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(JSON_FILE)
        playerScores = Gson().fromJson(jsonString, listType)
    }
}