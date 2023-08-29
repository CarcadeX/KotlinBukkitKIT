package tech.carcadex.kotlinbukkitkit.commands.manager

import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.plugin.Plugin
import tech.carcadex.kotlinbukkitkit.commands.reflection.CommandParams
import tech.carcadex.kotlinbukkitkit.commands.reflection.Parser
import tech.carcadex.kotlinbukkitkit.commands.reflection.Parser.paramsFromAnnotations
import tech.carcadex.kotlinbukkitkit.commands.reflection.annotations.Command
import tech.carcadex.kotlinbukkitkit.commands.exceptions.TypeParseException
import tech.carcadex.kotlinbukkitkit.commands.register.CommandRegister
import tech.carcadex.kotlinbukkitkit.commands.service.MessagesService
import tech.carcadex.kotlinbukkitkit.commands.reflection.service.SubCommandsService
import tech.carcadex.kotlinbukkitkit.commands.service.TabCompleteService
import kotlin.reflect.KFunction
import kotlin.reflect.full.createType
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.jvm.javaType



class CommandManagerImpl(private val plugin: Plugin) : CommandManager {

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
        if(commandClass::class.objectInstance == null) {
            throw IllegalArgumentException("commandClass is not a object!")
        }
        val (name, subs) = SubCommandsService.registerRootCommand(commandClass)
        val subsNames = mutableListOf<String>()
        subs.forEach {
            subsNames.addAll(it.context.aliases)
        }
        val context = paramsFromAnnotations(commandClass::class)
        register(CommandExecutor { sender, _, _, args ->
            if (context.perm != null && !sender.hasPermission(context.perm)) {
                MessagesService.byTag("#no-perm")(sender)
                return@CommandExecutor true
            }
            SubCommandsService.handleCommand(name, sender, args);
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
                    MessagesService.byTag("#no-perm")(sender)
                    return@CommandExecutor true
                }
                try {
                    func.callBy(

                        Parser.paramMap(cmd, argsFromExecutor, argsSize, sender, argsParsers, func)

                    )
                } catch (ignored: TypeParseException) {
                } catch (e: IllegalArgumentException) {
                    MessagesService.byTag("#wrong-usage")(sender)
                }

                return@CommandExecutor true

            },
                cmd,
                cmd.completeTags.let {
                    TabCompleteService.completerReflection(it.toMutableList(),
                        func.parameters.map { it.type.javaType as Class<*> })
                }
                    ?: TabCompleter { _, _, _, _ -> mutableListOf() }
            )
        } else throw IllegalArgumentException("It must be a command!")
    }

    override fun tabComplete(tag: String, func: (CommandSender) -> List<String>) {
        TabCompleteService.register(tag, func)
    }


    override fun message(tag: String, func: (CommandSender) -> Unit) {
        MessagesService.register(tag, func)
    }
}