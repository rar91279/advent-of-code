package tools

import java.io.File

fun readListOfStringFromInput(filename:String) = File("src/main/resources/$filename").readLines()

fun readTextFromInput(filename:String) = File("src/main/resources/$filename").readText()