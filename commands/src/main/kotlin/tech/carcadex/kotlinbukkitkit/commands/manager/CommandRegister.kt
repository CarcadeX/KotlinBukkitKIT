package tech.carcadex.kotlinbukkitkit.commands.manager

import org.bukkit.Bukkit
import org.bukkit.command.*
import org.bukkit.plugin.Plugin
import java.util.*


class CommandRegister(
    aliases: Array<String>,
    desc: String?,
    usage: String?,
    protected val owner: CommandExecutor,
    protected val registeredWith: Any,
    val pl: Plugin
) : Command(aliases[0], desc!!, usage!!, Arrays.asList(*aliases)), PluginIdentifiableCommand {


    override fun execute(sender: CommandSender, label: String, args: Array<String>): Boolean {
        if (!testPermission(sender)) {
            return true
        }
        return if (owner.onCommand(sender, this, label, args)) {
            true
        } else {
            sender.sendMessage(usageMessage)
            false
        }
    }

    companion object {
        fun reg(plugin: Plugin, executor: CommandExecutor, aliases: Array<String>, desc: String?, usage: String?) {
            if(aliases.size < 1) throw IllegalArgumentException("Could not found name")
            try {
                val reg = CommandRegister(aliases, desc, usage, executor, Any(), plugin)
                val field = Bukkit.getServer().javaClass.getDeclaredField("commandMap")
                field.isAccessible = true
                val map = field[Bukkit.getServer()] as CommandMap
                map.register(plugin.description.name, reg)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun getPlugin(): Plugin = pl
}