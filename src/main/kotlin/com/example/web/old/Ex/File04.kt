package com.example.web.old.Ex

import java.io.File

fun main() {
    File("./test.txt").forEachLine {
        println(it);
    }
}