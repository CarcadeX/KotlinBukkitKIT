package me.redtea.test

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import tech.carcadex.kotlinbukkitkit.genref.plugin.annotations.Plugin
import tech.carcadex.kotlinbukkitkit.architecture.KotlinPlugin;
import tech.carcadex.kotlinbukkitkit.commands.annotations.*
import tech.carcadex.kotlinbukkitkit.commands.manager.ReflectionCommandManager

typealias Sender = CommandSender


@Plugin
fun KotlinPlugin.start() {
    val cmdManager = ReflectionCommandManager(this)
    cmdManager.tabComplete("#test") { listOf("AA", "BB")}
    cmdManager.register(::simpleCommand)
    cmdManager.register(::withargs)
    cmdManager.register(ExCommand)
}

@Command("simple")
fun simpleCommand(sender: Sender) {
    sender.sendMessage("Command works normally")
}

@Command("withargs")
@TabComplete(["#range:1-100", "#test"])
fun withargs(sender: Sender, arg1: Int, @Optional arg2: String?) {
    sender.sendMessage("Args: 1: $arg1 2: ${arg2 ?: "NO ARG"}")
}

@Command("excommand")
object ExCommand {
    @Default
    fun default(sender: Sender) {
        sender.sendMessage("default")
    }

    @SubCommand("sub1")
    @TabComplete(["#range:1-10"])
    fun sub1(sender: Player, i: Int) {
        sender.sendMessage("sub1: $i")
    }

    @SubCommand("sub2")
    @TabComplete(["#test", "#test"])
    fun sub2(sender: Sender, s: String, s2: String) {
        sender.sendMessage("sub2")
    }
}