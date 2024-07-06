package tech.carcadex.kbk.commands.dsl

import tech.carcadex.kbk.commands.dsl.Command

interface CommandNode : Command {
    fun add(command: Command)
}