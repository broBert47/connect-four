fun main() {
    val report = readLine()!!
    val regex = Regex(". wrong answers?")

    println(regex.matches(report))
}