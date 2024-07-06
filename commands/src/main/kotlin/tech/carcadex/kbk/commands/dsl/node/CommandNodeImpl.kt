package tech.carcadex.kbk.commands.dsl.node

import org.bukkit.command.CommandSender
import tech.carcadex.kbk.commands.dsl.Command
import tech.carcadex.kbk.commands.dsl.CommandContext
import tech.carcadex.kbk.commands.dsl.CommandNode
import tech.carcadex.kbk.commands.dsl.ExecutorContext
import tech.carcadex.kbk.commands.exceptions.CommandExecuteException
import tech.carcadex.kbk.commands.service.MessagesService

class CommandNodeImpl(
    override val context: CommandContext,
    private val subs: MutableMap<String, Command> = mutableMapOf(),
    private val default: Command? = null
) : CommandNode {

    override fun execute(context: ExecutorContext) {
        if(hasNotPermission(context.sender)) return
        if(context.args.isNotEmpty() && context.args[0].lowercase() in subs) subs[context.args[0].lowercase()]?.execute(
            ExecutorContext(context.sender, context.args.copyOfRange(1, context.args.size)))
        else {
            if(default != null) {
                default!!.execute(ExecutorContext(context.sender, context.args))
            } else throw CommandExecuteException("#wrong-usage")
        }
    }

    override fun tabComplete(sender: CommandSender, args: Array<String>): List<String> {
        if(hasNotPermission(sender)) return emptyList()
        return if(args.isNotEmpty() && args[0].lowercase() in subs) subs[args[0].lowercase()]?.tabComplete(sender, args.copyOfRange(1, args.size)) ?: emptyList()
        else subs.keys.toList()
    }

    override fun add(command: Command) {
        subs[command.context.name] = command
    }
}