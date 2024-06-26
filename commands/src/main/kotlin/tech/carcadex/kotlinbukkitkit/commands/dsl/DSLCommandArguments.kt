package tech.carcadex.kotlinbukkitkit.commands.dsl

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.World
import org.bukkit.entity.Player
import tech.carcadex.kotlinbukkitkit.commands.exceptions.ArgumentNotFoundException
import tech.carcadex.kotlinbukkitkit.commands.exceptions.CommandExecuteException

fun ExecutorContext.string(index: Int): String = argument(index) ?: throw ArgumentNotFoundException(index)
fun ExecutorContext.stringOrNull(index: Int): String? = argument(index)
fun ExecutorContext.integer(index: Int): Int = try {
    argument(index)?.toInt()
} catch (e: NumberFormatException) {
    throw CommandExecuteException("#not-a-number")
} ?: throw ArgumentNotFoundException(index)
fun ExecutorContext.integerOrNull(index: Int): Int? = argument(index)?.toIntOrNull()

fun ExecutorContext.double(index: Int): Double = try {
    argument(index)?.toDouble()
} catch (e: NumberFormatException) {
    throw CommandExecuteException("#not-a-number")
} ?: throw ArgumentNotFoundException(index)
fun ExecutorContext.doubleOrNull(index: Int): Double? = argument(index)?.toDoubleOrNull()

fun ExecutorContext.materialOrNull(index: Int): Material? = Material.matchMaterial(string(index))
fun ExecutorContext.material(index: Int): Material =
    materialOrNull(index) ?: run {
        throw CommandExecuteException("#no-such-material")
    }
fun ExecutorContext.worldOrNull(index: Int): World? = Bukkit.getWorld(argument(index) ?: "")
fun ExecutorContext.world(index: Int): World =
    worldOrNull(index) ?: run {
        throw CommandExecuteException("#no-such-world")
    }

fun ExecutorContext.playerOrNull(index: Int): Player? = Bukkit.getPlayer(argument(index) ?: "")
fun ExecutorContext.player(index: Int): Player =
    playerOrNull(index) ?: run {
        throw CommandExecuteException("#no-such-player")
    }

fun ExecutorContext.offlinePlayer(index: Int): OfflinePlayer = Bukkit.getOfflinePlayer(argument(index) ?: "")