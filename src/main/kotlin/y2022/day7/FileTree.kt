package y2022.day7

import tools.readListOfStringFromInput

sealed class Node(val name: String, val parentNode: Dir? = null) {
    abstract fun size(): Int

    class Dir(name: String, parentNode: Dir? = null) : Node(name, parentNode) {
        val childs: MutableList<Node> = mutableListOf()
        override fun size(): Int {
            return if (childs.isEmpty()) 0 else childs.sumOf { it.size() }
        }
    }

    class File(name: String, private val size: Int, parentNode: Dir) : Node(name, parentNode) {
        override fun size(): Int {
            return size
        }
    }
}

fun main() {
    val exampleInput = readListOfStringFromInput("y2022/day7/example.txt")
    check(findSizeOfRemovalCandidates1(exampleInput) ==95437)
    
    val input = readListOfStringFromInput("y2022/day7/input1.txt")
    println(findSizeOfRemovalCandidates1(input))

    val candidateToFreeSizeInExample = findBestCandidateToFreeSize(exampleInput)
    check(candidateToFreeSizeInExample.name == "d")
    
    val candidateToFreeSizeInTest = findBestCandidateToFreeSize(input)
    println(candidateToFreeSizeInTest.size())
    
}

fun findBestCandidateToFreeSize(input: List<String>): Node.Dir {
    val rootNode = parse(input)
    val sortedBy = traverseWithoutMaxSize(rootNode).sortedBy { it.size() }
    val currentlyUsed = sortedBy.last().size()
    return sortedBy.filter { 70_000_000 - currentlyUsed + it.size() >= 30_000_000 }.minBy { it.size() }
}

fun traverseWithoutMaxSize(current: Node.Dir): List<Node.Dir> {
    val response = mutableListOf<Node.Dir>(current)
    for (childDir in current.childs.filterIsInstance<Node.Dir>()) {
        response.addAll(traverseWithoutMaxSize(childDir))
    }
    return response;
}


fun findSizeOfRemovalCandidates1(input: List<String>): Int{
    val node = parse(input)    
    return traverse(node).sumOf { it.size() }
}
fun traverse(current: Node.Dir): List<Node.Dir> {
    val response = mutableListOf<Node.Dir>()
    for (childDir in current.childs.filterIsInstance<Node.Dir>()) {        
        response.addAll(traverse(childDir).filter { it.size() < 100_000 })
    }
    if(current.size() < 100_000) response.add(current)
    return response;
}

fun parse(list: List<String>): Node.Dir {
    var current: Node.Dir = Node.Dir("/")
    var index = 0
    do {
        val line = list[index]
        when{
            line.startsWith("$ cd /") -> current = navigateToRoot(current)
            line.startsWith("$ cd ..") -> current = navigateToParent(current)
            line.startsWith("$ cd ") -> current = navigateToChild(current, line)
            line.startsWith("$ ls") -> index = parseListOutput(current, list, index)
        }
    }while (++index <list.size)
    return navigateToRoot(current) 
}

fun parseListOutput(current: Node.Dir, list: List<String>, index: Int): Int {
    var localIndex = index + 1
    do {
        current.childs.add(list[localIndex].toNode(current))        
    }while (++localIndex < list.size && !list[localIndex].startsWith("$"))
    return localIndex - 1
}
fun navigateToChild(current: Node.Dir, line: String): Node.Dir {
    val childName = line.substring(5, line.length)
    return current.childs.filterIsInstance<Node.Dir>().find { it.name == childName }?:current
}
fun navigateToParent(current: Node.Dir): Node.Dir {
    return current.parentNode?.let { current.parentNode } ?: current
}
fun navigateToRoot(current: Node.Dir): Node.Dir {
    return if(current.parentNode == null) current
    else navigateToRoot(current.parentNode)
}

fun String.toNode(current: Node.Dir): Node {
    fun getSize(): Int = this.substringBefore(" ").toInt()
    val name = this.substringAfter(" ").trim()    
    return if (this.startsWith("dir")) {
        Node.Dir(name, current)
    } else {
        Node.File(name, getSize(), current)
    }
}
