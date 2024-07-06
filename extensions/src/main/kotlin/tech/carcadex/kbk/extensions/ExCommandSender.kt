package tech.carcadex.kbk.extensions

import org.bukkit.command.CommandSender

public fun CommandSender.send(message: String) {
    sendMessage(message)
}

public fun CommandSender.hasNotPerm(permission: String) = (!hasPermission(permission))