interface ScoreboardStore {
    fun findAll(): List<ScoreModel>
    fun findOne(userName: String): ScoreModel?
    fun create(playerScore: ScoreModel)
    fun update(playerScore: ScoreModel)
    fun delete(playerScore: ScoreModel)
}