package tech.carcadex.kotlinbukkitkit.commands.dsl.leaf

import org.bukkit.command.CommandSender
import tech.carcadex.kotlinbukkitkit.commands.dsl.Command
import tech.carcadex.kotlinbukkitkit.commands.dsl.CommandContext
import tech.carcadex.kotlinbukkitkit.commands.dsl.ExecutorContext
import tech.carcadex.kotlinbukkitkit.commands.exceptions.ArgumentNotFoundException
import tech.carcadex.kotlinbukkitkit.commands.exceptions.CommandExecuteException

class CommandLeaf(
    override val context: CommandContext,
    private val executor: (ExecutorContext.() -> Unit),
    private val completer: List<(CommandSender) -> List<String>>
) : Command {
    override fun execute(context: ExecutorContext) {
        if(hasNotPermission(context.sender))
            throw CommandExecuteException("#no-perm")
        try {
            executor(context)
        } catch (e: ArgumentNotFoundException) {
            throw CommandExecuteException("#wrong-usage")
        }

    }

    override fun tabComplete(sender: CommandSender, args: Array<String>): List<String> {
        if(args.isEmpty() || completer.size < args.size || hasNotPermission(sender)) return emptyList()
        return completer[args.size-1](sender)
    }
}