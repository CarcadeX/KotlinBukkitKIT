package tech.carcadex.kbk.commands.dsl

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import tech.carcadex.kbk.commands.exceptions.NotPlayerException

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