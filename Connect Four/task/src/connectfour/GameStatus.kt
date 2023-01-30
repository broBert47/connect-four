package connectfour

sealed class GameStatus {
    object Playing: GameStatus()
    data class Winner(val symbol: Symbol): GameStatus()
}