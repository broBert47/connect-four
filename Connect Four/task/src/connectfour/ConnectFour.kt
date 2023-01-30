package connectfour

import java.lang.Exception
import kotlin.math.sin

class ConnectFour {

    private val player1: Player
    private val player2: Player
    private var currentPlayer: Player
    private val board: Board
    private val game: Game

    init {
        //PRINTAMO UVOD, TRAŽIMO UNOS IMENA IGRAČA I PRIDODAJEMO IM SIMBOL ZA IGRU
        println("Connect Four")
        println("First player's name:")
        player1 = Player(readln(), Symbol.CIRCLE)
        println("Second player's name:")
        player2 = Player(readln(), Symbol.STAR)

        // SETUPIRAMO TKO PRVI IGRA
        currentPlayer = player1

        // TRAŽIMO INPUT I ODREĐUJEMO DIMENZIJE PLOČE
        board = Board.initialize()

        // PRINTAMO IMENA IGRAČA I DIMENZIJU PLOČE
        println("${player1.name} VS ${player2.name}")
        board.printDimension()

        // RADIMO OBJEKT ZA INSTANCIRANJE PRAZNE LISTE LISTA FIELDOVA
        val glavnaLista = board.emptyBoard()

        // INSTANCIRAMO KLASU GAME I PREDAJEMO LISTU + PLOČU
        game = Game(glavnaLista, board)

        //CRTAMO PLOČU
        board.boardLayout(glavnaLista)

        //POKREĆEMO FUNKCIJU KOJOM IGRAMO JEDNU IGRU
        playingGame()
    }

    fun playingGame(){
        var gameCounter = 0
        do{

            //PRINTA SE IME IGRAČA KOJI JE NA REDU
            println("${currentPlayer.name}'s turn:")

            //TRAŽI SE UNOS KOLONE U KOJU IDE ZNAK
            val input: String = readln()

            var dobarUnos: Boolean = false

            // AKO JE IGRAČ UNIO END, ZAVRŠAVA IGRA
            // ROVJERAVA SE DA LI JE IGRAČ UNIO BROJ
            if(input == "end"){
                gameCounter++
            } else {
                dobarUnos = try {
                    input.toInt()
                    true
                } catch (e: Exception) {
                    println("Incorrect column number")
                    false
                }
            }


            //IGRA SE JEDAN POTEZ
            if (dobarUnos) {
                if (input.toInt() in 1..board.column) {
                    val potez = game.turn(input.toInt(), currentPlayer)
                    if (potez) swapPlayer()
                } else {
                    println("The column number is out of range (1 - ${board.column})")
                }
            }

            val status = game.checkWinner()
            if(status != GameStatus.Playing){
                gameCounter++
            }

        }while(gameCounter == 0)


        println("Game over!")
    }

    fun swapPlayer(){
        currentPlayer = if(currentPlayer == player1) {
            player2
        } else {
            player1
        }
    }

}