package tech.carcadex.kotlinbukkitkit.commands.dsl.builder

import org.bukkit.plugin.Plugin
import tech.carcadex.kotlinbukkitkit.commands.dsl.Command
import tech.carcadex.kotlinbukkitkit.commands.dsl.CommandContext
import tech.carcadex.kotlinbukkitkit.commands.dsl.ExecutorContext
import tech.carcadex.kotlinbukkitkit.commands.dsl.leaf.CommandLeaf
import tech.carcadex.kotlinbukkitkit.commands.dsl.node.CommandNodeImpl
import tech.carcadex.kotlinbukkitkit.commands.exceptions.LeafCommandHasNotExecutorException
import tech.carcadex.kotlinbukkitkit.commands.register.CommandRegister

class CommandBuilder(
    val context: CommandContext,
    var tabComplete: Collection<String> = emptyList(),
    val subs: MutableMap<String, Command> = mutableMapOf(),
    var executor: (ExecutorContext.() -> Unit)? = null
) {
    var aliases by context::aliases
    var usage by context::usage
    var description by context::description
    var permission by context::permission
}

fun CommandBuilder.subCommand(name: String, permission: String, tabComplete: Collection<String>, invoke: CommandBuilder.() -> Unit) {
    subs[name.lowercase()] = createCommand(name, permission, tabComplete, invoke)
}

fun CommandBuilder.executor(executor: ExecutorContext.() -> Unit) {
    this.executor = executor
}


private fun createCommand(name: String, permission: String, tabComplete: Collection<String>, invoke: CommandBuilder.() -> Unit): Command {
    val builder = CommandBuilder(CommandContext(name))
    builder.permission = permission
    builder.tabComplete = tabComplete
    builder.invoke()
    return createCommand(builder)
}
private fun createCommand(builder: CommandBuilder): Command {
    return if (builder.subs.isEmpty()) {
        if (builder.executor == null) throw LeafCommandHasNotExecutorException(builder.context.name)
        CommandLeaf(builder.context, builder.executor!!, builder.tabComplete.toList())
    } else {
        CommandNodeImpl(builder.context, builder.subs)
    }
}

fun Plugin.simpleCommand(name: String, permission: String, tabComplete: Collection<String>, executor: ExecutorContext.() -> Unit) {
    val command = createCommand(CommandBuilder(CommandContext(name),
        executor = executor).apply {
            this.permission = permission
            this.tabComplete = tabComplete
    })
    CommandRegister.register(this, { sender, _, _, args -> command.execute(ExecutorContext(sender, args)); true },
        command.context.aliases, command.context.description, command.context.usage, { sender, _, _, args ->
            command.tabComplete(sender, args)
        }
    )
}

fun Plugin.command(name: String, permission: String, tabComplete: Collection<String>, invoke: CommandBuilder.() -> Unit) {
    val command = createCommand(name, permission, tabComplete, invoke)
    CommandRegister.register(this, { sender, _, _, args -> command.execute(ExecutorContext(sender, args)); true },
        command.context.aliases, command.context.description, command.context.usage, { sender, _, _, args ->
            command.tabComplete(sender, args)
        }
    )
}