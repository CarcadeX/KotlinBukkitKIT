package tech.carcadex.kotlinbukkitkit.commands.service

import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

class MessagesService {
    private val messages = mutableMapOf<String, (CommandSender) -> Unit>()

    init {
        //REGISTER DEFAULT MESSAGES
        messages["#for-player-only"] = { it.sendMessage("${ChatColor.RED}This sender is not a player") }
        messages["#no-such-player"] = { it.sendMessage("${ChatColor.RED}No such player was found") }
        messages["#no-such-world"] = { it.sendMessage("${ChatColor.RED}No such world was found") }
        messages["#no-such-material"] = { it.sendMessage("${ChatColor.RED}No such material was found") }
        messages["#no-such-enum"] = { it.sendMessage("${ChatColor.RED}No such value for this enum was found") }
        messages["#not-a-number"] = { it.sendMessage("${ChatColor.RED}It is not a number") }
        messages["#wrong-usage"] = { it.sendMessage("${ChatColor.RED}Wrong usage of command") }
        messages["#unknown-error"] = { it.sendMessage("${ChatColor.RED}Something went wrong") }
        messages["#no-perm"] = { it.sendMessage("${ChatColor.RED}You have not permissions for this command!") }
        messages["#empty"] = { }
    }

    fun register(tag: String, func: (CommandSender) -> Unit) {
        messages[tag] = func
    }

    fun byTag(tag: String): (CommandSender) -> Unit = messages[tag] ?: throw NoSuchElementException("Could not found message $tag")
}