package tech.carcadex.kotlinbukkitkit.commands.dsl.leaf

import org.bukkit.command.CommandSender
import tech.carcadex.kotlinbukkitkit.commands.dsl.Command
import tech.carcadex.kotlinbukkitkit.commands.dsl.CommandContext
import tech.carcadex.kotlinbukkitkit.commands.dsl.ExecutorContext
import tech.carcadex.kotlinbukkitkit.commands.exceptions.ArgumentNotFoundException
import tech.carcadex.kotlinbukkitkit.commands.service.MessagesService
import tech.carcadex.kotlinbukkitkit.commands.service.TabCompleteService

class CommandLeaf(
    override val context: CommandContext,
    private val executor: (ExecutorContext.() -> Unit),
    completeTags: List<String> = emptyList()
) : Command {
    private val completer = TabCompleteService.parseTags(completeTags)
    override fun execute(context: ExecutorContext) {
        if(hasNotPermission(context.sender)) return
        try {
            executor(context)
        } catch (e: ArgumentNotFoundException) {
            MessagesService.byTag("#wrong-usage")(context.sender)
        }

    }

    override fun tabComplete(sender: CommandSender, args: Array<String>): List<String> {
        if(args.isEmpty() || completer.size < args.size || hasNotPermission(sender)) return emptyList()
        return completer[args.size-1](sender)
    }
}