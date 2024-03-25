package tech.carcadex.kotlinbukkitkit.commands.dsl

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.World
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
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

    fun double(index: Int): Double = try {
        argument(index)?.toDouble()
    } catch (e: NumberFormatException) {
        MessagesService.byTag("#not-a-number")(sender)
        throw TypeParseException()
    } ?: throw ArgumentNotFoundException(index)
    fun doubleOrNull(index: Int): Double? = argument(index)?.toDoubleOrNull()

    fun materialOrNull(index: Int): Material? = Material.matchMaterial(string(index))
    fun material(index: Int): Material =
        materialOrNull(index) ?: run {
            MessagesService.byTag("#no-such-material")(sender)
            throw TypeParseException()
        }
    fun worldOrNull(index: Int): World? = Bukkit.getWorld(argument(index) ?: "")
    fun world(index: Int): World =
        worldOrNull(index) ?: run {
            MessagesService.byTag("#no-such-world")(sender)
            throw TypeParseException()
        }

    fun playerOrNull(index: Int): Player? = Bukkit.getPlayer(argument(index) ?: "")
    fun player(index: Int): World =
        worldOrNull(index) ?: run {
            MessagesService.byTag("#no-such-player")(sender)
            throw TypeParseException()
        }

    fun offlinePlayer(index: Int): OfflinePlayer = Bukkit.getOfflinePlayer(argument(index) ?: "")
}