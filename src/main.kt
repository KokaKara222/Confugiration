private const val vfs_name = "vfs"

fun findCommand(input: String): Boolean{
    val parts = input.split("\\s+".toRegex())
    val command = parts[0]
    val args = parts.drop(1)
    return when (command){
        "ls", "cd" -> { println("$command ${args.joinToString (" ")}")
        true}
        else -> {println("Unknown command: $command")
            false
        }
    }
}

fun main(){
    while (true){
        print("$vfs_name> ")
        val input = readlnOrNull()?.trim()

        if (input == "exit") break
        else if (input.isNullOrEmpty())continue

        findCommand(input)
    }
}