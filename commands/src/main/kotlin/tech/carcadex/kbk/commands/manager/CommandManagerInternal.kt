package tech.carcadex.kbk.commands.manager

import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.plugin.Plugin
import tech.carcadex.kbk.commands.reflection.CommandParams
import tech.carcadex.kbk.commands.reflection.Parser
import tech.carcadex.kbk.commands.reflection.Parser.paramsFromAnnotations
import tech.carcadex.kbk.commands.reflection.annotations.Command
import tech.carcadex.kbk.commands.exceptions.CommandExecuteException
import tech.carcadex.kbk.commands.register.CommandRegister
import tech.carcadex.kbk.commands.service.MessagesService
import tech.carcadex.kbk.commands.reflection.service.SubCommandsService
import tech.carcadex.kbk.commands.service.TabCompleteService
import kotlin.reflect.KFunction
import kotlin.reflect.full.createType
import kotlin.reflect.full.findAnnotations
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.jvm.javaType

class CommandManagerInternal(private val plugin: Plugin) : CommandManager {
    val messagesService = MessagesService()
    val tabCompleteService = TabCompleteService()
    private val subCommandsService = SubCommandsService(messagesService, tabCompleteService)
    private fun register(
        commandExecutor: CommandExecutor, commandParams: CommandParams, tabCompleter: TabCompleter =
            TabCompleter { _, _, _, _ -> mutableListOf() }
    ) {
        CommandRegister.register(
            plugin,
            commandExecutor,
            commandParams.aliases.toTypedArray(),
            commandParams.desc,
            commandParams.usage,
            tabCompleter
        );
    }

    override fun register(commandClass: Any) {
        if((commandClass::class.findAnnotations(Command::class).isEmpty())) {
            throw IllegalArgumentException("commandClass has not contains Command annotation!")
        }
        val (name, subs) = subCommandsService.registerRootCommand(commandClass)
        val subsNames = mutableListOf<String>()
        subs.forEach {
            subsNames.addAll(it.context.aliases)
        }
        val context = paramsFromAnnotations(commandClass::class)
        register(CommandExecutor { sender, _, _, args ->
            if (context.perm != null && !sender.hasPermission(context.perm)) {
                messagesService.byTag("#no-perm")(sender)
                return@CommandExecutor true
            }
            subCommandsService.handleCommand(name, sender, args);
            true
        },
            context,
            TabCompleter { sender, cmd, alias, args ->
                if (args.size == 1) {
                    return@TabCompleter subsNames
                } else if (args.size > 1) {
                    val sub = args[0];
                    if (subs.any { it.name.equals(sub, ignoreCase = true) }) {
                        val argsForSub = args.copyOfRange(1, args.size)
                        return@TabCompleter subs.filter { it.name.equals(sub, ignoreCase = true) }.first().tabCompleter
                            .onTabComplete(sender, cmd, alias, argsForSub)
                    } else return@TabCompleter emptyList()
                } else return@TabCompleter emptyList()
            })
    }

    override fun register(func: KFunction<Unit>) {
        if (func.hasAnnotation<Command>()) {
            val cmd = paramsFromAnnotations(func)
            var argsSize = func.parameters.size
            if (func.parameters.isNotEmpty() && (
                        func.parameters[0].type == (CommandSender::class.createType()) ||
                                func.parameters[0].type.isSubtypeOf(CommandSender::class.createType())
                        )
            ) {
                --argsSize
            }
            val argsParsers = Parser.parseParam(func.parameters)


            register(CommandExecutor { sender, _, _, argsFromExecutor ->
                if (cmd.perm != null && !sender.hasPermission(cmd.perm)) {
                    messagesService.byTag("#no-perm")(sender)
                    return@CommandExecutor true
                }
                try {
                    func.callBy(
                        Parser.paramMap(cmd, argsFromExecutor, argsSize, sender, argsParsers, func)
                    )
                } catch (e: CommandExecuteException) {
                    messagesService.byTag(e.messageTag)(sender)
                } catch (e: IllegalArgumentException) {
                    messagesService.byTag("#wrong-usage")(sender)
                } catch (e: Throwable) {
                    messagesService.byTag("#unknown-error")(sender)
                }

                return@CommandExecutor true

            },
                cmd,
                cmd.completeTags.let {
                    tabCompleteService.completerReflection(it.toMutableList(),
                        func.parameters.map { it.type.javaType as Class<*> })
                }
            )
        } else throw IllegalArgumentException("It must be a command!")
    }

    override fun tabComplete(tag: String, func: (CommandSender) -> List<String>) {
        tabCompleteService.register(tag, func)
    }


    override fun message(tag: String, func: (CommandSender) -> Unit) {
        messagesService.register(tag, func)
    }

    override fun readMessage(tag: String): (CommandSender) -> Unit = messagesService.byTag(tag)
}