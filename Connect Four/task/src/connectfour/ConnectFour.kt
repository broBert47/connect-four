package connectfour

import kotlin.math.sin

class ConnectFour {

    private val player1: Player
    private val player2: Player
    private val board: Board
    private val glavnaLista: MutableList<MutableList<Field>>
    private var currentPlayer: Player

    init {
        println("Connect Four")
        println("First player's name:")
        player1 = Player(readln(), Symbol.CIRCLE)
        println("Second player's name:")
        player2 = Player(readln(), Symbol.STAR)

        currentPlayer = player1

        board = Board.initialize()

        println("${player1.name} VS ${player2.name}")
        board.printDimension()

        glavnaLista = board.emptyBoard()
        board.boardLayout(glavnaLista)
        playingGame()
    }

    fun playingGame(){
        var singleGame = false
        do{

            println("${currentPlayer.name}'s turn:")
            val input: String = readln()
            if(input == "end"){
                singleGame = true
            }

            if (!singleGame) {
                if (input.toInt() in 1..board.column) {
                    var potez: Boolean
                    do {
                        if (Field.Empty in glavnaLista[input.toInt() - 1]) {
                            for (i in (glavnaLista[input.toInt() - 1].size - 1) downTo 0) {
                                if (glavnaLista[input.toInt() - 1][i] == Field.Empty) {
                                    glavnaLista[input.toInt() - 1][i] = Field.Selected(currentPlayer.symbol)
                                    board.boardLayout(glavnaLista)
                                    playerTurn()
                                    break
                                }
                            }
                        } else {
                            println("Column ${input.toInt()} is full")
                        }
                        potez = true
                    } while (!potez)

                } else {
                    println("The column number is out of range (1 - ${board.column})")
                }
            }


        }while(!singleGame)
    }

    fun playerTurn(){
        currentPlayer = if(currentPlayer == player1) {
            player2
        } else {
            player1
        }
    }

}