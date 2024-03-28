package tech.carcadex.kotlinbukkitkit.commands

import tech.carcadex.kotlinbukkitkit.architecture.KotlinPlugin
import tech.carcadex.kotlinbukkitkit.commands.dsl.builder.simpleCommand

fun KotlinPlugin.start() {
    simpleCommand("test", permission = "test.use") {
        val arg1 = string(0)
        val arg2 = integer(1)
        sender.sendMessage(arg1)
    }
}