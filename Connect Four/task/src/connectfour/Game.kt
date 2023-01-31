package connectfour

import java.lang.Exception
import java.util.EmptyStackException

class Game(
    private val board: Board,
    val numberOfGames: Int
) {
    val glavnaLista: MutableList<MutableList<Field>> = mutableListOf(mutableListOf())

    init{
        emptyBoard()
    }

    fun turn(input: Int, currentPlayer: Player): Boolean{
        if (Field.Empty in glavnaLista[input - 1]) {
            for (i in (glavnaLista[input - 1].size - 1) downTo 0) {
                if (glavnaLista[input - 1][i] == Field.Empty) {
                    glavnaLista[input - 1][i] = Field.Selected(currentPlayer.symbol)
                    board.boardLayout(glavnaLista)
                    return true
                }
            }
        } else {
            println("Column $input is full")
            return false
        }
        return false
    }

    fun checkWinner(): GameStatus{
        var status: GameStatus
        status = WinCondition.verticalWin(glavnaLista)
        if(status != GameStatus.Playing){
            return status
        }

        status = WinCondition.horizontalWin(glavnaLista)
        if(status != GameStatus.Playing){
            return status
        }

        status = WinCondition.diagonalWin(glavnaLista)
        if(status != GameStatus.Playing){
            return status
        }

        val draw = WinCondition.checkDraw(glavnaLista)
        if(draw){
            return GameStatus.Draw
        }

        return GameStatus.Playing
    }

    fun emptyBoard(){
        val glavnaLista = mutableListOf<MutableList<Field>>()
        for (i in 0 until board.column) {
            val lista = mutableListOf<Field>()
            for (j in 0 until board.row) {
                lista.add(Field.Empty)
            }
            glavnaLista.add(lista)
        }
        this.glavnaLista.clear()
        this.glavnaLista.addAll(glavnaLista)
    }
}