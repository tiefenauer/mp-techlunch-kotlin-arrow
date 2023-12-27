package org.example

fun main() {
    println("Calculating the meaning of life")
    val meaningOfLife = (NumberParserWithExceptions.toInt("42.0") * 2) / 2
    println("the meaning of life is: $meaningOfLife")
}