interface ScoreboardStore {
    fun findAll(): List<ScoreModel>
    fun find(userName: String): ScoreModel?
    fun create(playerScore: ScoreModel)
    fun update(playerScore: ScoreModel)
    fun delete(id:String)
    fun printPlayerData(playerScore: ScoreModel)
}