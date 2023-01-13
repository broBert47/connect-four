package connectfour

import java.lang.Exception

fun main() {

    println("Connect Four")
    println("First player's name:")
    val player1 = readln()
    println("Second player's name:")
    val player2 = readln()

    //DIMENZIJE SE ODREĐUJU
    val (rows, columns) = dimensionSet()

    //ISPISUJU SE IMENA IGRAČA, DIMENZIJE I BROJ IGARA KOJE ĆE IGRATI
    val numberOfGames = numGames()
    if (numberOfGames == 1) {
        println(
            """
       $player1 VS $player2
       $rows X $columns board
       Single game
   """.trimIndent()
        )
    } else {
        println(
            """
       $player1 VS $player2
       $rows X $columns board
       Total $numberOfGames games
   """.trimIndent()
        )
    }


    var gameOver = false
    var gameCounter = 0
    var player1Score = 0
    var player2Score = 0
    do{
        gameCounter++
        if(numberOfGames > 1){
            println("Game #$gameCounter")
        }

        //RADI SE PRAZNA LISTA LISTA
        val board = emptyBoard(columns, rows)

        //PRINTA SE PRAZNA PLOČA
        boardLayout(board)
        println()


        // POČINJE IGRA
        var singleGame = false
        var input: String
        do {
            var noO = 0
            var noZ = 0

            for (i in board) {
                for (j in i) {
                    if (j == "o") {
                        noO++
                    } else if (j == "*") {
                        noZ++
                    }
                }
            }

            //PROVJERA UNOSA POTEZA
            val player1sign = "o"
            val player2sign = "*"
            var moveSign: String
            var dobarUnos: Boolean
            do {
                if(gameCounter % 2 == 1){
                    moveSign = if (noO == noZ) {
                        println("$player1's turn:")
                        player1sign
                    } else {
                        println("$player2's turn:")
                        player2sign
                    }
                } else {
                    moveSign = if (noO == noZ) {
                        println("$player2's turn:")
                        player2sign
                    } else {
                        println("$player1's turn:")
                        player1sign
                    }
                }


                input = readln()
                if (input == "end") {
                    gameOver = true
                    dobarUnos = true
                    singleGame = true
                } else {
                    dobarUnos = try {
                        input.toInt()
                        true
                    } catch (e: Exception) {
                        println("Incorrect column number")
                        false
                    }
                }
            } while (!dobarUnos)

            //RADI SE POTEZ
            if (!singleGame) {
                if (input.toInt() in 1..columns) {
                    var potez: Boolean
                    do {
                        if (" " in board[input.toInt() - 1]) {
                            for (i in (board[input.toInt() - 1].size - 1) downTo 0) {
                                if (board[input.toInt() - 1][i] == " ") {
                                    board[input.toInt() - 1][i] = moveSign
                                    boardLayout(board)
                                    println()
                                    break
                                }
                            }
                        } else {
                            println("Column ${input.toInt()} is full")
                        }
                        potez = true
                    } while (!potez)

                } else {
                    println("The column number is out of range (1 - $columns)")
                }
            }


            // PROVJERE POBJEDE I PRINTANJE POBJEDNIKA
            val vertical = verticalWin(board)
            val horizontal = horizontalWin(board)
            val diagonal = diagonalWin(board)

            if (vertical != Winner.PLAYING || horizontal != Winner.PLAYING || diagonal != Winner.PLAYING) {
                singleGame = true
            }

            if (vertical == Winner.CIRCLE || horizontal == Winner.CIRCLE || diagonal == Winner.CIRCLE) {
                player1Score += 2
                println("Player $player1 won")
            } else if (vertical == Winner.STAR || horizontal == Winner.STAR || diagonal == Winner.STAR) {
                player2Score += 2
                println("Player $player2 won")
            }

            //PROVJERA DA LI JE DRAW
            val valueCheck = board.any { sublist ->
                sublist.contains(" ")
            }

            if (valueCheck) {
                continue
            } else {
                player1Score++
                player2Score++
                println("It is a draw")
                singleGame = true
            }

        } while (!singleGame)

        // PRINTANJE TRENUTNOG REZULTATA
        if(numberOfGames > 1){
            println("""
            Score
            $player1: $player1Score $player2: $player2Score
        """.trimIndent())
        }

        //PROVJERA DA LI JE CIJELA IGRA GOTOVA
        if(gameCounter == numberOfGames){
            gameOver = true
        }
    }while(!gameOver)


    println("Game over!")

}

fun dimensionSet(): Pair<Int, Int>{
    var rows = 0
    var columns = 0
    var dimensionSet = false

    // NA TEMELJU INPUTA SE ODREĐUJU TOČNE DIMENZIJE
    do {
        printUvod()

        var dimensions = readln()

        if (dimensions.isEmpty()) {
            rows = 6
            columns = 7
            dimensionSet = true
        } else {
            dimensions = dimensions.replace("\\s".toRegex(), "")
            val regex = Regex("\\d+[X|x]\\d+")

            if (regex.matches(dimensions)) {
                if ("X" in dimensions) {
                    rows = dimensions.substringBefore("X").toInt()
                    columns = dimensions.substringAfter("X").toInt()
                } else if ("x" in dimensions) {
                    rows = dimensions.substringBefore("x").toInt()
                    columns = dimensions.substringAfter("x").toInt()
                }

                if (rows < 5 || rows > 9) {
                    println("Board rows should be from 5 to 9")
                } else if (columns < 5 || columns > 9) {
                    println("Board columns should be from 5 to 9")
                } else {
                    dimensionSet = true
                }
            } else {
                println("Invalid input")
            }
        }
    } while (!dimensionSet)

    return Pair<Int, Int>(rows, columns)
}

fun printUvod() {
    println(
        """
        Set the board dimensions (Rows x Columns)
        Press Enter for default (6 x 7)
    """.trimIndent()
    )
}

fun numGames(): Int {
    var games = 1
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

fun emptyBoard(columns: Int, rows: Int): MutableList<MutableList<String>>{
    val glavnaLista = mutableListOf<MutableList<String>>()
    for (i in 0 until columns) {
        val lista = mutableListOf<String>()
        for (j in 0 until rows) {
            lista.add(" ")
        }
        glavnaLista.add(lista)
    }
    return glavnaLista
}

fun boardLayout(list: MutableList<MutableList<String>>) {
    for (kolone in 1..list.size) {
        print(" $kolone")
    }

    println()

    for (red in 0 until list[0].size) {
        for (kolone in 0 until list.size) {
            print("║" + list[kolone][red])
        }
        print("║")
        println()
    }

    for (kolone in 0..list.size) {
        when (kolone) {
            0 -> print("╚")
            list.size -> print("═╝")
            else -> print("═╩")
        }
    }
}

enum class Winner {
    PLAYING, CIRCLE, STAR
}

fun verticalWin(list: MutableList<MutableList<String>>): Winner {

    var counter = 0
    var previous = ""
    for (kolona in list) {
        for (red in kolona) {
            if (red == "o") {
                if (previous == "" || previous == "o") {
                    counter++
                } else {
                    counter = 1
                }
                previous = "o"
            } else if (red == "*") {
                if (previous == "" || previous == "*") {
                    counter++
                } else {
                    counter = 1
                }
                previous = "*"
            }

            if (counter == 4) {
                if (previous == "o") {
                    return Winner.CIRCLE
                } else if (previous == "*") {
                    return Winner.STAR
                }

            }
        }
        previous = ""
        counter = 0
    }
    return Winner.PLAYING

}

fun horizontalWin(list: MutableList<MutableList<String>>): Winner {
    var counter = 0
    var previous = ""
    for (red in 0 until list[0].size) {
        for (kolona in list.indices) {
            if (list[kolona][red] == "o") {
                if (previous == "" || previous == "o") {
                    counter++
                } else {
                    counter = 1
                }
                previous = "o"
            } else if (list[kolona][red] == "*") {
                if (previous == "" || previous == "*") {
                    counter++
                } else {
                    counter = 1
                }
                previous = "*"
            }

            if (counter == 4) {
                if (previous == "o") {
                    return Winner.CIRCLE
                } else if (previous == "*") {
                    return Winner.STAR
                }
            }
        }
        previous = ""
        counter = 0
    }
    return Winner.PLAYING
}

fun diagonalWin(list: MutableList<MutableList<String>>): Winner {
    for (kolona in 0 until list.size - 3) {
        for (red in 0 until list[kolona].size - 3) {
            try {
                if (list[kolona][red] == "o" &&
                    list[kolona][red] == list[kolona + 1][red + 1] &&
                    list[kolona][red] == list[kolona + 2][red + 2] &&
                    list[kolona][red] == list[kolona + 3][red + 3]
                ) {
                    return Winner.CIRCLE
                } else if (list[kolona][red] == "*" &&
                    list[kolona][red] == list[kolona + 1][red + 1] &&
                    list[kolona][red] == list[kolona + 2][red + 2] &&
                    list[kolona][red] == list[kolona + 3][red + 3]
                ) {
                    return Winner.STAR
                }
            } catch (e: Exception) {
            }
        }
    }

    for (kolona in 0 until list.size - 3) {
        for (red in 0 until list[kolona].size) {
            try {
                if (list[kolona][red] == "o" &&
                    list[kolona][red] == list[kolona + 1][red - 1] &&
                    list[kolona][red] == list[kolona + 2][red - 2] &&
                    list[kolona][red] == list[kolona + 3][red - 3]
                ) {
                    return Winner.CIRCLE
                } else if (list[kolona][red] == "*" &&
                    list[kolona][red] == list[kolona + 1][red - 1] &&
                    list[kolona][red] == list[kolona + 2][red - 2] &&
                    list[kolona][red] == list[kolona + 3][red - 3]
                ) {
                    return Winner.STAR
                }
            } catch (e: Exception) {
            }

        }
    }
    return Winner.PLAYING
}




