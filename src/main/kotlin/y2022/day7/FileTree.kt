package y2022.day7

import tools.readListOfStringFromInput

sealed class Node(val name: String, val level: String) {
    abstract fun size():Int
    override fun toString(): String {
        return """
                |${level}name: $name                
            """.trimIndent()
    }
    class Dir(name: String, level: String) : Node(name, level){
        val childs : MutableList<Node> = mutableListOf()
        override fun size(): Int {
            return if(childs.isEmpty()) 0 else childs.sumOf { it.size() }
        }
        override fun toString(): String {
            return """
                |${level}name: $name
                ${childs.forEach{it.toString()}}
            """.trimIndent()
        }

    }
    class File(name: String, level: String, private val size: Int) : Node(name, level){
        override fun size(): Int {
            return size
        }
    }
}

fun main() {
    val exampleInput = readListOfStringFromInput("y2022/day7/example.txt")

// Root
    val root = exampleInput[0].toNode()
    parse(parentNode = root, exampleInput.subList(1, exampleInput.size))
    println(root)
}


fun parse(parentNode: Node, sublist:List<String>):Node{
    if (parentNode is Node.Dir && sublist.isNotEmpty()){
        // Find all entries of same level and split into sub lists
        val indicies = sublist.filter { it.startsWith(parentNode.level + "-") }.map { sublist.indexOf(it) }
        indicies.windowed(2, 1, partialWindows = true).forEach { window ->
            println("Window $window")
            val node = sublist[window[0]].toNode()
            if(node is Node.Dir && window.size == 2)
            {
                val subSubList = sublist.subList(window[0]+1, window[1])
                parse(node, subSubList)
            }
            (parentNode).childs.add(node)
        }
    }
    return parentNode
}
fun String.toNode(): Node {
    fun getSize():Int = this.substringAfter("size=").substringBefore(")").toInt()
    val name = this.substringAfter("-").substringBefore("(").trim()
    val level = this.takeWhile { it == ' ' } + "  "
    return if (this.endsWith("(dir)")) {
        Node.Dir(name, level)
    } else {
        Node.File(name, level, getSize())
    }
}






