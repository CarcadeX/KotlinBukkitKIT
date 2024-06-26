package tech.carcadex.kotlinbukkitkit.commands.manager

import org.bukkit.command.CommandSender
import org.bukkit.plugin.Plugin
import kotlin.reflect.KFunction


interface CommandManager {
    fun register(commandClass: Any)
    fun register(func: KFunction<Unit>)
    fun tabComplete(tag: String, func: (CommandSender) -> List<String>)
    fun message(tag: String, func: (CommandSender) -> Unit)
    fun readMessage(tag: String): (CommandSender) -> Unit

    companion object {
        private val registeredManagers = mutableMapOf<Plugin, CommandManager>()
        @JvmStatic
        fun get(plugin: Plugin): CommandManager =
            registeredManagers[plugin] ?: run {
                registeredManagers[plugin] = CommandManagerInternal(plugin)
                get(plugin)
            }
    }
}

fun Plugin.commandManager(): CommandManager = CommandManager.get(this)
internal fun Plugin.commandManagerInternal(): CommandManagerInternal = CommandManager.get(this) as CommandManagerInternal