package connectfour

sealed class Field {
    object Empty: Field()
    data class Selected(val coin: String): Field()
}