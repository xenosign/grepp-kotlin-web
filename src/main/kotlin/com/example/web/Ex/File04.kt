package com.example.web.Ex

import java.io.File

fun main() {
    File("./test.txt").forEachLine {
        println(it);
    }
}