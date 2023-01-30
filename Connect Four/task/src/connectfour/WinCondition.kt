package connectfour

import java.lang.Exception

object WinCondition {

    fun verticalWin(list: MutableList<MutableList<Field>>): GameStatus {

        var counter = 0
        var previous: Field = Field.Empty
        for (kolona in list) {
            for (red in kolona) {
                if (red is Field.Selected) {
                    if (red.symbol == Symbol.CIRCLE) {
                        if (previous == Field.Empty || (previous as? Field.Selected)?.symbol == Symbol.CIRCLE) {
                            counter++
                        } else {
                            counter = 1
                        }
                        previous = red
                    } else if (red.symbol == Symbol.STAR) {
                        if (previous == Field.Empty || (previous as? Field.Selected)?.symbol == Symbol.STAR) {
                            counter++
                        } else {
                            counter = 1
                        }
                        previous = red
                    }
                }


                if (counter == 4) {
                    if ((previous as? Field.Selected)?.symbol == Symbol.CIRCLE) {
                        return GameStatus.Winner(Symbol.CIRCLE)
                    } else if ((previous as? Field.Selected)?.symbol == Symbol.STAR) {
                        return GameStatus.Winner(Symbol.STAR)
                    }

                }
            }
            previous = Field.Empty
            counter = 0
        }
        return GameStatus.Playing

    }

    fun horizontalWin(list: MutableList<MutableList<Field>>): GameStatus {
        var counter = 0
        var previous: Field = Field.Empty
        for (red in 0 until list[0].size) {
            for (kolona in list.indices) {
                val position = list[kolona][red]
                if (position is Field.Selected) {
                    if (position.symbol == Symbol.CIRCLE) {
                        if (previous == Field.Empty || (previous as? Field.Selected)?.symbol == Symbol.CIRCLE) {
                            counter++
                        } else {
                            counter = 1
                        }
                        previous = position
                    } else if (position.symbol == Symbol.STAR) {
                        if (previous == Field.Empty || (previous as? Field.Selected)?.symbol == Symbol.STAR) {
                            counter++
                        } else {
                            counter = 1
                        }
                        previous = position
                    }
                }


                if (counter == 4) {
                    if ((previous as? Field.Selected)?.symbol == Symbol.CIRCLE) {
                        return GameStatus.Winner(Symbol.CIRCLE)
                    } else if ((previous as? Field.Selected)?.symbol == Symbol.STAR) {
                        return GameStatus.Winner(Symbol.STAR)
                    }

                }
            }
            previous = Field.Empty
            counter = 0
        }
        return GameStatus.Playing
    }

    fun diagonalWin(list: MutableList<MutableList<Field>>): GameStatus {
        for (kolona in 0 until list.size - 3) {
            for (red in 0 until list[kolona].size - 3) {
                try {
                    val a = list[kolona][red]
                    if (a is Field.Selected &&
                        a == list[kolona + 1][red + 1] &&
                        a == list[kolona + 2][red + 2] &&
                        a == list[kolona + 3][red + 3]
                    ) {
                        return GameStatus.Winner(a.symbol)
                    }
                } catch (e: Exception) {
                }
            }
        }

        for (kolona in 0 until list.size - 3) {
            for (red in 0 until list[kolona].size) {
                try {
                    val a = list[kolona][red]
                    if (a is Field.Selected &&
                        list[kolona][red] == list[kolona + 1][red - 1] &&
                        list[kolona][red] == list[kolona + 2][red - 2] &&
                        list[kolona][red] == list[kolona + 3][red - 3]
                    ) {
                        return GameStatus.Winner(a.symbol)
                    }
                } catch (e: Exception) {
                }

            }
        }
        return GameStatus.Playing
    }
}