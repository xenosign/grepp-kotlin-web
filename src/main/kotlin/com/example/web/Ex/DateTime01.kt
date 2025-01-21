package com.example.web.Ex

import kotlinx.datetime.*

fun main() {
    val currentDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    println(currentDateTime)
}