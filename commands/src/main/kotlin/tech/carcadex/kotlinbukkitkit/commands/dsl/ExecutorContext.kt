package tech.carcadex.kotlinbukkitkit.commands.dsl

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.World
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import tech.carcadex.kotlinbukkitkit.commands.exceptions.ArgumentNotFoundException
import tech.carcadex.kotlinbukkitkit.commands.exceptions.NotPlayerException
import tech.carcadex.kotlinbukkitkit.commands.exceptions.TypeParseException
import tech.carcadex.kotlinbukkitkit.commands.service.MessagesService

class ExecutorContext(val sender: CommandSender, val args: Array<String>) {
    val player: Player
        get() {
            if (sender !is Player) {
                MessagesService.byTag("#for-player-only")(sender)
                throw NotPlayerException()
            }
            return sender
        }

    fun argument(index: Int): String? = if (args.size <= index) null else args[index]
}