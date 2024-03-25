package tech.carcadex.kotlinbukkitkit.commands

import tech.carcadex.kotlinbukkitkit.architecture.KotlinPlugin
import tech.carcadex.kotlinbukkitkit.commands.dsl.builder.simpleCommand

fun KotlinPlugin.onEnable() {
    simpleCommand(
        name = "test",
        permission = "test.use",
        tabComplete = listOf("subcmd1", "subcmd2")
    ) {
        sender.sendMessage("test")
        val arg1 = argument(0) //getting 1 argument of command as nullable string
        //also you can use stringOrNull
        val arg2 = string(1) //getting 1 argument of command as string
        val arg3 = integer(2) //getting 2 argument of command as integer
        //also you can use integerOrNull
        val arg4 = double(3) //getting 3 argument of command as integer
        //also you can use doubleOrNull
    }
}