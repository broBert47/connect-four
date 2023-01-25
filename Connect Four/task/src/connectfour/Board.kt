package connectfour

class Board(
    val row: Int,
    val column: Int
) {
    fun printDimension(){
       println("$row X $column")
    }


    companion object{
        fun initialize(): Board{
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

            return Board(row = rows , column = columns)
        }

        private fun printUvod() {
            println(
                """
        Set the board dimensions (Rows x Columns)
        Press Enter for default (6 x 7)
    """.trimIndent()
            )
        }
    }
}