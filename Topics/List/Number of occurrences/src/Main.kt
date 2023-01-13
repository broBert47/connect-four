fun solution(strings: List<String>, str: String): Int {
    var num = 0
    for(i in strings) {
        if(i == str) {
            num++
        }
    }

    return num
}