package com.example.web

fun main() {
    val lottoNumbers = generateLottoNumbers()
    println("로또 번호: $lottoNumbers")
}

fun generateLottoNumbers(): List<Int> {
    return (1..45)                    // 1부터 45까지의 숫자 범위
        .shuffled()                   // 숫자들을 무작위로 섞음
        .take(6)                      // 앞에서 6개를 선택
        .sorted()                     // 선택된 숫자들을 정렬
}