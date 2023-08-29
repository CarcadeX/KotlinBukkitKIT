package tech.carcadex.kotlinbukkitkit.commands.manager

import org.bukkit.command.CommandSender
import org.bukkit.plugin.Plugin
import kotlin.reflect.KFunction


interface CommandManager {
    fun register(commandClass: Any)
    fun register(func: KFunction<Unit>)
    fun tabComplete(tag: String, func: (CommandSender) -> List<String>)
    fun message(tag: String, func: (CommandSender) -> Unit)

    companion object {
        @JvmStatic
        fun get(plugin: Plugin): CommandManager = CommandManagerImpl(plugin)
    }
}

fun Plugin.commandManager(): CommandManager = CommandManagerImpl(this)