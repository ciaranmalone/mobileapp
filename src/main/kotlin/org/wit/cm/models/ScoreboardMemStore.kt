import mu.KotlinLogging

private val logger = KotlinLogging.logger {}
var lastUserName =""

class ScoreboardMemStore : ScoreboardStore {
    val playerScores = ArrayList<ScoreModel>()

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
}