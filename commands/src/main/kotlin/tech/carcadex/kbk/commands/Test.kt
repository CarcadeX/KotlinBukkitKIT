package tech.carcadex.kbk.commands

import org.bukkit.plugin.Plugin
import tech.carcadex.kbk.commands.dsl.builder.simpleCommand

fun Plugin.cmd() {
    simpleCommand("test") {
        player.sendMessage("test")
    }
}
