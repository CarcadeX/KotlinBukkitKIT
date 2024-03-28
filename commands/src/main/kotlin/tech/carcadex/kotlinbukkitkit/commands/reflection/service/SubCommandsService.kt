package tech.carcadex.kotlinbukkitkit.commands.reflection.service

import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import tech.carcadex.kotlinbukkitkit.commands.exceptions.NotPlayerException
import tech.carcadex.kotlinbukkitkit.commands.service.MessagesService
import tech.carcadex.kotlinbukkitkit.commands.service.TabCompleteService
import tech.carcadex.kotlinbukkitkit.commands.reflection.CommandParams
import tech.carcadex.kotlinbukkitkit.commands.reflection.Parser
import tech.carcadex.kotlinbukkitkit.commands.reflection.annotations.Command
import tech.carcadex.kotlinbukkitkit.commands.reflection.annotations.Default
import tech.carcadex.kotlinbukkitkit.commands.exceptions.TypeParseException
import kotlin.reflect.KFunction
import kotlin.reflect.full.*
import kotlin.reflect.jvm.javaType

object SubCommandsService {
    data class SubCommand(val name: String, val context: CommandParams,
                          val executor: (sender: CommandSender, args: Array<String>) -> Boolean, val tabCompleter: TabCompleter)


    private val subs = mutableMapOf<String, List<SubCommand>>()
    private val defaults = mutableMapOf<String, SubCommand>()

    fun registerRootCommand(command: Any): Pair<String, List<SubCommand>> {
        val clazz = command::class
        if(!clazz.hasAnnotation<Command>()) throw IllegalArgumentException("$command has not Command annotation")
        val name = clazz.findAnnotation<Command>()!!.name.lowercase()

        val res = mutableListOf<SubCommand>()
        for(func in clazz.functions) {
            if(func.hasAnnotation<tech.carcadex.kotlinbukkitkit.commands.reflection.annotations.SubCommand>()) {
                res.add(parseSubCommand(func, command))
            } else if(func.hasAnnotation<Default>()) {
                defaults[name] = parseSubCommand(func, command)
            }
        }
        subs[name] = res
        return Pair(name, res)
    }

    fun handleCommand(rootCommand: String, sender: CommandSender, args: Array<String>) {
        if(rootCommand.lowercase() in subs) {
            if(args.isNotEmpty()) for(sub in subs[rootCommand.lowercase()]!!) {
                if(sub.name == args[0]) {
                    if (sub.context.perm != null && sub.context.perm != "" && !sender.hasPermission(sub.context.perm)) {
                        MessagesService.byTag("#no-perm")(sender)
                        return
                    }
                    sub.executor(sender, args.copyOfRange(1, args.size))
                    return
                }
            }
            defaults[rootCommand]?.executor?.let { it(sender, args) }
        }

    }

    private fun parseSubCommand(func: KFunction<*>, rootCommand: Any): SubCommand {

        val name = if(func.hasAnnotation<Default>()) "default" else func.findAnnotations(tech.carcadex.kotlinbukkitkit.commands.reflection.annotations.SubCommand::class).first().name
        val context = Parser.paramsFromAnnotations(func)
        val argsParsers = Parser.parseParam(func.parameters.toTypedArray().copyOfRange(1, func.parameters.size).toList())
        var argsSize = func.parameters.size
        if (func.parameters.isNotEmpty() && (
                    func.parameters[0].type == (CommandSender::class.createType()) ||
                            func.parameters[0].type.isSubtypeOf(CommandSender::class.createType())
                    )) {
            --argsSize
        }
        val executor: (sender: CommandSender, args: Array<String>) -> Boolean = { sender, argsFromExecutor ->
            if (context.perm != null && !sender.hasPermission(context.perm)) {
                MessagesService.byTag("#no-perm")(sender)
                true
            } else {
                try {
                    func.callBy(Parser.paramMap(context, argsFromExecutor, argsSize, sender, argsParsers, func, true, rootCommand))
                    true
                } catch (ignored: TypeParseException) {
                    false
                } catch (ignored: NotPlayerException) {
                    false
                } catch (e: IllegalArgumentException) {
                    MessagesService.byTag("#wrong-usage")(sender)
                    false
                }

            }
        }

        return SubCommand(name, context, executor, context.completeTags.let {
            TabCompleteService.completerReflection(it.toMutableList(),
                func.parameters.map { it.type.javaType as Class<*> })
        }
            ?: TabCompleter { _, _, _, _ -> mutableListOf() })
    }
}