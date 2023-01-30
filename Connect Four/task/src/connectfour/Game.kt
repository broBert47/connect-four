package connectfour

class Game(
    val glavnaLista: MutableList<MutableList<Field>>,
    private val board: Board
) {

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

        return WinCondition.diagonalWin(glavnaLista)
    }



}