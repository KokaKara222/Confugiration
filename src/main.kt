import java.io.File
import kotlin.system.exitProcess

private const val VFS_NAME = "vfs"

/**
 * Разбирает [input] на команду и аргументы, выполняет ей (на данном этапе пока заглушка)
 * Возвращает false, если команда неизвестна
 */
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

/**
 * Выполняет стартовый скрипт построчно, имитируя диалог с пользователем.
 * Останавливается на первой ошибке или на команде exit
 * Возвращает true, если скрипт выполнен полностью без ошибок
 */
fun runSсript(path: String): Boolean{
    val file = File(path)
    if (!file.exists()){
        println("[ERROR] Файл не найден: $path")
        return false
    }
    for (line in file.readLines()){
        val trimmed = line.trim()
        if (trimmed.isEmpty()) continue

        println("$VFS_NAME> $trimmed")
        if (trimmed == "exit") return true

        if(!findCommand(trimmed)){
            println("[ERROR] Invalid command: $trimmed")
            return false
        }
    }
    return true
}

fun main(args: Array<String>){
    var vfsPart: String? = null
    var scrPart: String? = null

    for (i in args.indices){
        if (args[i] == "--vfs" && i+1 < args.size){
            vfsPart = args[i+1]
        }
        if (args[i] == "--script" && i+1 < args.size){
            scrPart = args[i+1]
        }
    }

    println("[DEBUG] VFS path: ${vfsPart ?: "не задан"}")
    println("[DEBUG] Script path: ${scrPart ?: "не задан"}")

    if (scrPart != null && !runSсript(scrPart)){
        exitProcess(1)
    }
    while (true){
        print("$VFS_NAME> ")
        val input = readlnOrNull()?.trim()
        if (input == "exit") break
        else if (input.isNullOrEmpty())continue

        findCommand(input)
    }
}