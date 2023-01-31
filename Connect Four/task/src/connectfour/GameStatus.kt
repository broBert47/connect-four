package connectfour

sealed class GameStatus {
    object Playing: GameStatus()
    data class Winner(val symbol: Symbol): GameStatus()
    object Draw: GameStatus()
    object GameOver: GameStatus()
    object Aborted: GameStatus()
}