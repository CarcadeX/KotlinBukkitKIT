package tech.carcadex.kbk.commands.dsl.builder

import org.bukkit.plugin.Plugin
import tech.carcadex.kbk.commands.dsl.ExecutorContext
import tech.carcadex.kbk.commands.register.CommandRegister


fun Plugin.command(name: String, invoke: CommandBuilder.() -> Unit) {
    command(name, "", listOf(), invoke)
}

fun Plugin.command(name: String, permission: String, invoke: CommandBuilder.() -> Unit) {
    command(name, permission, listOf(), invoke)
}

fun Plugin.command(name: String, tabComplete: Collection<String>, invoke: CommandBuilder.() -> Unit) {
    command(name, "", tabComplete, invoke)
}

fun CommandBuilder.subCommand(name: String, invoke: CommandBuilder.() -> Unit) {
    subCommand(name, "", listOf(), invoke)
}

fun CommandBuilder.subCommand(name: String, permission: String, invoke: CommandBuilder.() -> Unit) {
    subCommand(name, permission, listOf(), invoke)
}

fun CommandBuilder.subCommand(name: String, tabComplete: Collection<String>, invoke: CommandBuilder.() -> Unit) {
    subCommand(name, "", tabComplete, invoke)
}

fun Plugin.simpleCommand(name: String, executor: ExecutorContext.() -> Unit) {
    simpleCommand(name, "", listOf(), executor)
}

fun Plugin.simpleCommand(name: String, permission: String, executor: ExecutorContext.() -> Unit) {
    simpleCommand(name, permission, listOf(), executor)
}

fun Plugin.simpleCommand(name: String, tabComplete: Collection<String>, executor: ExecutorContext.() -> Unit) {
    simpleCommand(name, "", tabComplete, executor)
}
