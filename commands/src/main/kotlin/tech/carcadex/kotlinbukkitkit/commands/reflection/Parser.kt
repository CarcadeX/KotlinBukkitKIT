package tech.carcadex.kotlinbukkitkit.commands.reflection

import org.bukkit.command.CommandSender
import tech.carcadex.kotlinbukkitkit.commands.exceptions.CommandExecuteException
import tech.carcadex.kotlinbukkitkit.commands.exceptions.UnsupportedTypeException
import tech.carcadex.kotlinbukkitkit.commands.reflection.annotations.*
import tech.carcadex.kotlinbukkitkit.commands.service.MessagesService
import tech.carcadex.kotlinbukkitkit.commands.reflection.service.TypeService
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.full.createType
import kotlin.reflect.full.findAnnotations
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.jvm.javaType

object Parser {
    fun paramsFromAnnotations(clazz: KClass<*>): CommandParams {
        val name = clazz.findAnnotations<Command>().first().name
        val desc = clazz.findAnnotations<Description>().getOrNull(0)?.desc ?: ""
        val usage = clazz.findAnnotations<Usage>().getOrNull(0)?.usage ?: ""
        val perm = clazz.findAnnotations<Permission>().getOrNull(0)?.perm
        val cmdAliases: Array<String> = clazz.findAnnotations<Aliases>().getOrNull(0)?.aliases ?: emptyArray()
        val completeTags: Array<String>? = null
        val isLastOptional = false
        var argsSize = 1
        val aliases = arrayListOf(name)
        aliases.addAll(cmdAliases)
        return CommandParams(name, desc, usage, perm, aliases, completeTags ?: emptyArray(), isLastOptional)
    }
    fun paramsFromAnnotations(func: KFunction<*>): CommandParams {

        val name = if(func.hasAnnotation<Command>())func.findAnnotations<Command>().first().name else
            if(func.hasAnnotation<SubCommand>()) func.findAnnotations<SubCommand>().first().name else "default"
        val desc = func.findAnnotations<Description>().getOrNull(0)?.desc ?: ""
        val usage = func.findAnnotations<Usage>().getOrNull(0)?.usage ?: ""
        val perm = func.findAnnotations<Permission>().getOrNull(0)?.perm
        val cmdAliases: Array<String> = func.findAnnotations<Aliases>().getOrNull(0)?.aliases ?: emptyArray()
        val completeTags: Array<String>? = func.findAnnotations<TabComplete>().getOrNull(0)?.complete
        val isLastOptional = func.parameters[func.parameters.size - 1].hasAnnotation<Optional>()
        var argsSize = func.parameters.size
        if (func.parameters.isNotEmpty() && func.parameters[0].type.javaType.typeName == CommandSender::class.qualifiedName!!) {
            --argsSize
        }
        val aliases = arrayListOf(name)
        aliases.addAll(cmdAliases)
        return CommandParams(name, desc, usage, perm, aliases, completeTags ?: emptyArray(), isLastOptional)
    }

    fun parseParam(params: List<KParameter>): List<(String, CommandSender) -> Any?> {
        val argsParsers = mutableListOf<(String, CommandSender) -> Any?>()
        val enums = mutableMapOf<Int, (String, CommandSender) -> Enum<*>>()
        for ((argIndex, argType) in params.withIndex()) {
            val clazz = Class.forName(replacePrimitives(argType.type.javaType.typeName))
            if (TypeService.registered(argType.type)) {
                argsParsers.add { s, sender ->
                    val context = TypeService.TypeContext(s, sender)
                    try {
                        TypeService.byType(argType.type)(context)
                    } catch (e: Throwable) {
                        throw CommandExecuteException(context.errorMessage ?: "#empty")
                    }
                }
            } else if (!clazz.isEnum) throw UnsupportedTypeException("Type ${argType.type.javaType.typeName} is not supported")
            else {
                enums[argIndex] = { it, _ ->
                    var res: Enum<*>? = null;
                    for (e in clazz.enumConstants) {
                        if ((e as Enum<*>).name.equals(it, ignoreCase = true)) res = e;
                    }
                    res ?: throw CommandExecuteException("#no-such-enum")
                }
                argsParsers.add { _, _ -> {} }
            }
        }
        enums.forEach {
            argsParsers[it.key] = it.value
        }
        return argsParsers;
    }

    fun replacePrimitives(s: String): String = when(s) {
        "int" -> "java.lang.Integer"
        "short" -> "java.lang.Short"
        "long" -> "java.lang.Long"
        "double" -> "java.lang.Double"
        "byte" -> "java.lang.Byte"
        "boolean" -> "java.lang.Boolean"
        else -> s
    }

    fun paramMap(
        cmd: CommandParams,
        argsFromExecutor: Array<String>,
        argsSize: Int,
        sender: CommandSender,
        argsParsers: List<(String, CommandSender) -> Any?>,
        func: KFunction<*>,
        instanceAtStart: Boolean = false,
        instance: Any = Any()
    ): MutableMap<KParameter, Any?> {

        val finalArgs = mutableListOf<Any?>()
        if(instanceAtStart) finalArgs.add(instance)
        if(func.parameters.isNotEmpty() && (
            func.parameters[0 + if(instanceAtStart) 1 else 0].type == (CommandSender::class.createType()) ||
            func.parameters[0 + if(instanceAtStart) 1 else 0].type.isSubtypeOf(CommandSender::class.createType())
                )
            )
            finalArgs.add(sender)
        finalArgs.addAll(argsFromExecutor.mapIndexed { i, it ->
            if (it == null) return@mapIndexed null
            argsParsers[i + 1](it, sender)
        })

        val map = mutableMapOf<KParameter, Any?>()
        for ((i, arg) in finalArgs.withIndex()) map[func.parameters[i]] = arg
        return map
    }
}
