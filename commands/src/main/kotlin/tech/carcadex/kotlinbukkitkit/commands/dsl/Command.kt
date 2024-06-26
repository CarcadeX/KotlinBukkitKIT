package tech.carcadex.kotlinbukkitkit.commands.dsl

import org.bukkit.command.CommandSender
import tech.carcadex.kotlinbukkitkit.commands.service.MessagesService

/**
 * Interface for SubCommand and Command
 * @author itzRedTea
 * @since 1.0.0
 */

interface Command {
    /**
     * Context of this command
     * @author itzRedTea
     * @since 1.0.0
     */
    val context: CommandContext
    /**
     * Invokes when command must be executed
     * @author itzRedTea
     * @since 1.0.0
     */
    fun execute(context: ExecutorContext)
    /**
     * Invokes when command must be executed
     * @param sender who executes this command
     * @param args arguments that sender typed in chat
     * (if this is subcommand args did not contains
     * name of sub command)
     * @author itzRedTea
     * @since 1.0.0
     */
    fun tabComplete(sender: CommandSender, args: Array<String>): List<String>


    fun hasNotPermission(sender: CommandSender): Boolean = context.permission != ""
            && (!sender.hasPermission(context.permission))
}

