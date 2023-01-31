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
        player1 = Player(readln(), Symbol.CIRCLE, 0)
        println("Second player's name:")
        player2 = Player(readln(), Symbol.STAR, 0)

        // SETUPIRAMO TKO PRVI IGRA
        currentPlayer = player1

        // TRAŽIMO INPUT I ODREĐUJEMO DIMENZIJE PLOČE
        board = Board.initialize()

        // RADIMO OBJEKT ZA INSTANCIRANJE PRAZNE LISTE LISTA FIELDOVA

        //PITAMO KOLIKO IGARA SE IGRA I TO PRINTAMO
        val numberOfGames = numGames()

        // INSTANCIRAMO KLASU GAME I PREDAJEMO LISTU + PLOČU
        game = Game(board, numberOfGames)


        // PRINTAMO IMENA IGRAČA, DIMENZIJU PLOČE I BROJ IGARA
        println("${player1.name} VS ${player2.name}")
        board.printDimension()
        if (numberOfGames == 1) {
            println("Single game")
        } else {
            println("Total $numberOfGames games")
        }

        //POKREĆEMO FUNKCIJU KOJOM IGRAMO IGRU/IGRE
        playingGame()

    }

    fun playingGame() {
        var gameCounter = 0
        var status: GameStatus = GameStatus.Playing

        do {
            gameCounter++
            if (game.numberOfGames > 1) {
                println("Game #$gameCounter")
            }

            var singleGame = false

            game.emptyBoard()

            //CRTAMO NOVU PRAZNU PLOČU
            board.boardLayout(game.glavnaLista)

            //ODREĐUJE SE KO JE PRVI NA REDU
            currentPlayer = if (gameCounter % 2 == 1) {
                player1
            } else {
                player2
            }


            do {

                //PRINTA SE IME IGRAČA KOJI JE NA REDU
                println("${currentPlayer.name}'s turn:")

                //TRAŽI SE UNOS KOLONE U KOJU IDE ZNAK
                val input: String = readln()

                var dobarUnos: Boolean = false

                // AKO JE IGRAČ UNIO END, ZAVRŠAVA IGRA
                // ROVJERAVA SE DA LI JE IGRAČ UNIO BROJ
                if (input == "end") {
                    status = GameStatus.Aborted
                    singleGame = true
                    dobarUnos = false
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
                if (status !is GameStatus.GameOver && status !is GameStatus.Aborted) {
                    status = game.checkWinner()
                    if (status != GameStatus.Playing) {
                        singleGame = true
                    }
                }

            } while (!singleGame)
            singleGame = false

            if (status is GameStatus.Winner) {
                if (status.symbol == player1.symbol) {
                    player1.score += 2
                    println("Player ${player1.name} won")
                } else {
                    player2.score += 2
                    println("Player ${player2.name} won")
                }
            } else if (status is GameStatus.Draw) {
                player1.score++
                player2.score++
            }



            if (status !is GameStatus.Aborted && gameCounter == game.numberOfGames) {
                status = GameStatus.GameOver
            }

            if (status !is GameStatus.GameOver && gameCounter != game.numberOfGames) {
                println(
                    """
                    Score
                    ${player1.name}: ${player1.score} ${player2.name}: ${player2.score}
                    """.trimIndent()
                )
            }

        } while (status != GameStatus.GameOver && status != GameStatus.Aborted)

        if (status == GameStatus.Aborted) {
            println("Game over!")
        } else {
            if (player1.score > player2.score) {
                println("Player ${player1.name} won")
            } else {
                println("Player ${player2.name} won")
            }

            println(
                """
            Score
            ${player1.name}: ${player1.score} ${player2.name}: ${player2.score}
            Game over!
        """.trimIndent()
            )
        }
    }


    fun swapPlayer() {
        currentPlayer = if (currentPlayer == player1) {
            player2
        } else {
            player1
        }
    }

    fun numGames(): Int {
        var games = 0
        var gamesSet = false
        do {
            println(
                """
        Do you want to play single or multiple games?
        For a single game, input 1 or press Enter
        Input a number of games:
    """.trimIndent()
            )

            val input = readln()
            if (input.isEmpty()) {
                games = 1
                gamesSet = true
            } else {
                try {
                    input.toInt()
                } catch (_: Exception) {
                }

                val regex = Regex("\\d")
                if (regex.matches(input)) {
                    if (input.toInt() > 0) {
                        games = input.toInt()
                        gamesSet = true
                    } else {
                        println("Invalid input")
                    }
                } else {
                    println("Invalid input")
                }
            }

        } while (!gamesSet)

        return games


    }

}