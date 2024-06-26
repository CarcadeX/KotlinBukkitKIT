package tech.carcadex.kotlinbukkitkit.commands.dsl

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import tech.carcadex.kotlinbukkitkit.commands.exceptions.NotPlayerException

class ExecutorContext(val sender: CommandSender, val args: Array<String>) {
    val player: Player
        get() {
            if (sender !is Player) {
                throw NotPlayerException()
            }
            return sender
        }

    fun argument(index: Int): String? = if (args.size <= index) null else args[index]
}