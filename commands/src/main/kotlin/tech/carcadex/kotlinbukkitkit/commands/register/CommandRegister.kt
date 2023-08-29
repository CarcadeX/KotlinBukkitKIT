package tech.carcadex.kotlinbukkitkit.commands.register

import org.bukkit.Bukkit
import org.bukkit.command.*
import org.bukkit.plugin.Plugin


class CommandRegister(
    aliases: Array<String>,
    desc: String?,
    usage: String?,
    var executor: CommandExecutor,
    var tabCompleter: TabCompleter,
    private val pl: Plugin
) : Command(aliases[0], desc!!, usage!!, listOf(*aliases)), PluginIdentifiableCommand {


    override fun execute(sender: CommandSender, label: String, args: Array<String>): Boolean {
        if (!testPermission(sender)) {
            return true
        }
        return if (executor.onCommand(sender, this, label, args)) {
            true
        } else {
            sender.sendMessage(usageMessage)
            false
        }
    }

    companion object {
        fun register(plugin: Plugin, executor: CommandExecutor, aliases: Array<String>,
                     desc: String?, usage: String?, tabCompleter: TabCompleter): CommandRegister {
            if(aliases.size < 1) throw IllegalArgumentException("Could not found name")
            val reg = CommandRegister(aliases, desc, usage, executor, tabCompleter, plugin)
            val field = Bukkit.getServer().javaClass.getDeclaredField("commandMap")
            field.isAccessible = true
            val map = field[Bukkit.getServer()] as CommandMap
            map.register(plugin.description.name, reg)
            return reg
        }
    }

    override fun getPlugin(): Plugin = pl

    override fun tabComplete(sender: CommandSender, alias: String, args: Array<out String>): MutableList<String> {
        return tabCompleter.onTabComplete(sender, this, alias, args)!!
    }
}