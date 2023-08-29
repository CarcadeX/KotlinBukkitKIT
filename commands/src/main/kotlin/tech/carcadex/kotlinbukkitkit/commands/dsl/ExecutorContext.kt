package tech.carcadex.kotlinbukkitkit.commands.dsl

import org.bukkit.command.CommandSender
import tech.carcadex.kotlinbukkitkit.commands.exceptions.ArgumentNotFoundException
import tech.carcadex.kotlinbukkitkit.commands.exceptions.TypeParseException
import tech.carcadex.kotlinbukkitkit.commands.service.MessagesService

class ExecutorContext(var sender: CommandSender, val args: Array<String>) {
    fun argument(index: Int): String? = if (args.size <= index) null else args[index]
    fun string(index: Int): String = argument(index) ?: throw ArgumentNotFoundException(index)
    fun stringOrNull(index: Int): String? = argument(index)
    fun integer(index: Int): Int = try {
        argument(index)?.toInt()
    } catch (e: NumberFormatException) {
        MessagesService.byTag("#not-a-number")(sender)
        throw TypeParseException()
    } ?: throw ArgumentNotFoundException(index)
    fun integerOrNull(index: Int): Int? = argument(index)?.toIntOrNull()
}