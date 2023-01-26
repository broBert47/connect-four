package connectfour

sealed class Field {
    object Empty: Field()
    data class Selected(val symbol: Symbol): Field()
}