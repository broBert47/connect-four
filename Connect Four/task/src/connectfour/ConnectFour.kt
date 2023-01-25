package connectfour

class ConnectFour {

    private val player1: Player
    private val player2: Player
    private val board: Board

    init {
        println("Connect Four")
        println("First player's name:")
        player1 = Player(readln())
        println("Second player's name:")
        player2 = Player(readln())

        board = Board.initialize()

        println("${player1.name} VS ${player2.name}")
        board.printDimension()
    }

}