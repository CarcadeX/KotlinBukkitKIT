package tech.carcadex.kotlinbukkitkit.commands

import org.bukkit.plugin.Plugin
import tech.carcadex.kotlinbukkitkit.commands.dsl.builder.simpleCommand

fun Plugin.cmd() {
    simpleCommand("test") {
        player.sendMessage("test")
    }
}
