import java.io.File
import kotlin.system.exitProcess

private const val VFS_NAME = "vfs"

/**
 * Разбирает [input] на команду и аргументы, выполняет её (пока заглушка).
 * Возвращает false, если команда неизвестна.
 */
fun findCommand(input: String): Boolean {
    val parts = input.split("\\s+".toRegex())
    val command = parts[0]
    val args = parts.drop(1)
    return when (command) {
        "ls", "cd" -> {
            println("$command ${args.joinToString(" ")}")
            true
        }
        else -> {
            println("Unknown command: $command")
            false
        }
    }
}

/**
 * Выполняет стартовый скрипт построчно, имитируя диалог с пользователем.
 * Останавливается на первой ошибке или на команде exit.
 * Возвращает true, если скрипт выполнен полностью без ошибок.
 */
fun runScript(path: String): Boolean {
    val file = File(path)
    if (!file.exists()) {
        println("[ERROR] Файл не найден: $path")
        return false
    }
    for (line in file.readLines()) {
        val trimmed = line.trim()
        if (trimmed.isEmpty()) continue

        println("$VFS_NAME> $trimmed")
        if (trimmed == "exit") return true

        if (!findCommand(trimmed)) {
            println("[ERROR] Invalid command: $trimmed")
            return false
        }
    }
    return true
}

/** Результат разбора параметров командной строки. */
data class CliArgs(val vfsPath: String?, val scriptPath: String?)

/** Разбирает args на пути к VFS и стартовому скрипту. */
fun parseArgs(args: Array<String>): CliArgs {
    var vfsPath: String? = null
    var scriptPath: String? = null
    for (i in args.indices) {
        if (args[i] == "--vfs" && i + 1 < args.size) vfsPath = args[i + 1]
        if (args[i] == "--script" && i + 1 < args.size) scriptPath = args[i + 1]
    }
    return CliArgs(vfsPath, scriptPath)
}

/** Печатает отладочные значения заданных параметров запуска. */
fun printDebugInfo(cliArgs: CliArgs) {
    println("[DEBUG] VFS path: ${cliArgs.vfsPath ?: "не задан"}")
    println("[DEBUG] Script path: ${cliArgs.scriptPath ?: "не задан"}")
}

/** Интерактивный цикл: читает команды с клавиатуры до exit. */
fun startRepl() {
    while (true) {
        print("$VFS_NAME> ")
        val input = readlnOrNull()?.trim()
        when {
            input == "exit" -> return
            input.isNullOrEmpty() -> continue
            else -> findCommand(input)
        }
    }
}

fun main(args: Array<String>) {
    val cliArgs = parseArgs(args)
    printDebugInfo(cliArgs)

    if (cliArgs.scriptPath != null && !runScript(cliArgs.scriptPath)) {
        exitProcess(1)
    }

    startRepl()
}