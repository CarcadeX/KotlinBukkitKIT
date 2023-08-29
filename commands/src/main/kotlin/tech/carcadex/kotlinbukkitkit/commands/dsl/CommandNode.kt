package tech.carcadex.kotlinbukkitkit.commands.dsl

import tech.carcadex.kotlinbukkitkit.commands.dsl.Command

interface CommandNode : Command {
    fun add(command: Command)
}