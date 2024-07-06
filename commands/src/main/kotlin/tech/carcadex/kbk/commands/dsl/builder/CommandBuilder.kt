package tech.carcadex.kbk.commands.dsl.builder

import org.bukkit.command.CommandSender
import org.bukkit.plugin.Plugin
import tech.carcadex.kbk.commands.dsl.Command
import tech.carcadex.kbk.commands.dsl.CommandContext
import tech.carcadex.kbk.commands.dsl.ExecutorContext
import tech.carcadex.kbk.commands.dsl.leaf.CommandLeaf
import tech.carcadex.kbk.commands.dsl.node.CommandNodeImpl
import tech.carcadex.kbk.commands.exceptions.CommandExecuteException
import tech.carcadex.kbk.commands.exceptions.LeafCommandHasNotExecutorException
import tech.carcadex.kbk.commands.manager.CommandManagerInternal
import tech.carcadex.kbk.commands.manager.commandManager
import tech.carcadex.kbk.commands.manager.commandManagerInternal
import tech.carcadex.kbk.commands.register.CommandRegister
import tech.carcadex.kbk.commands.service.TabCompleteService

class CommandBuilder(
    val context: CommandContext,
    val plugin: Plugin,
    var tabComplete: List<(CommandSender) -> List<String>> = emptyList(),
    val subs: MutableMap<String, Command> = mutableMapOf(),
    var executor: (ExecutorContext.() -> Unit)? = null
) {
    var aliases by context::aliases
    var usage by context::usage
    var description by context::description
    var permission by context::permission
}

fun CommandBuilder.subCommand(name: String, permission: String, tabComplete: Collection<String>, invoke: CommandBuilder.() -> Unit) {
    subs[name.lowercase()] = plugin.createCommand(name, permission, tabComplete, invoke)
}

fun CommandBuilder.executor(executor: ExecutorContext.() -> Unit) {
    this.executor = executor
}


private fun Plugin.createCommand(name: String, permission: String, tabComplete: Collection<String>, invoke: CommandBuilder.() -> Unit): Command {
    val builder = CommandBuilder(CommandContext(name), this)
    builder.permission = permission
    builder.tabComplete = commandManagerInternal().tabCompleteService.parseTags(tabComplete.toList())
    builder.invoke()
    return createCommand(builder)
}
private fun createCommand(builder: CommandBuilder): Command {
    return if (builder.subs.isEmpty()) {
        if (builder.executor == null) throw LeafCommandHasNotExecutorException(builder.context.name)
        CommandLeaf(builder.context, builder.executor!!, (builder.tabComplete.toList()))
    } else {
        CommandNodeImpl(builder.context, builder.subs,
            if(builder.executor != null) CommandLeaf(builder.context, builder.executor!!, builder.tabComplete) else null)
    }
}

fun Plugin.simpleCommand(name: String, permission: String, tabComplete: Collection<String>, executor: ExecutorContext.() -> Unit) {
    registerCommand(createCommand(CommandBuilder(CommandContext(name),
        executor = executor, plugin = this).apply {
        this.permission = permission
        this.tabComplete = commandManagerInternal().tabCompleteService.parseTags(tabComplete.toList())
    }))
}

fun Plugin.command(name: String, permission: String, tabComplete: Collection<String>, invoke: CommandBuilder.() -> Unit) {
    registerCommand(createCommand(name, permission, tabComplete, invoke))
}

private fun Plugin.registerCommand(command: Command) {
    CommandRegister.register(this, { sender, _, _, args -> try {
        command.execute(ExecutorContext(sender, args))
    } catch (e: CommandExecuteException) {
        commandManager().readMessage(e.messageTag)(sender)
    }; true },
        command.context.aliases, command.context.description, command.context.usage, { sender, _, _, args ->
            command.tabComplete(sender, args)
        }
    )
}