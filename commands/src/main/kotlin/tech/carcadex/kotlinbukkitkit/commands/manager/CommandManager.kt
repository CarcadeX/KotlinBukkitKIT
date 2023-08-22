package tech.carcadex.kotlinbukkitkit.commands.manager

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.World
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import tech.carcadex.kotlinbukkitkit.commands.annotations.Command
import tech.carcadex.kotlinbukkitkit.commands.exceptions.UnsupportedTypeException
import tech.carcadex.kotlinbukkitkit.extensions.offlinePlayerOrNull
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.KType
import kotlin.reflect.full.callSuspendBy
import kotlin.reflect.full.findAnnotations
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.jvm.javaType

class CommandManager(val plugin: Plugin) {
    private val types = mutableMapOf<String, (String) -> Any?>()

    init {
        types[CommandSender::class.qualifiedName!!] = { it }
        types[String::class.qualifiedName!!] = { it }
        types["java.lang.String"] = { it }
        types[Int::class.qualifiedName!!] = { it.toInt() }
        types["java.lang.Integer"] = { it.toInt() }
        types[UInt::class.qualifiedName!!] = { it.toUInt() }
        types[Long::class.qualifiedName!!] = { it.toLong() }
        types["java.lang.Long"] = { it.toInt() }
        types[ULong::class.qualifiedName!!] = { it.toULong() }
        types[Short::class.qualifiedName!!] = { it.toShort() }
        types["java.lang.Short"] = { it.toInt() }
        types[UShort::class.qualifiedName!!] = { it.toUShort() }
        types[Float::class.qualifiedName!!] = { it.toFloat() }
        types["java.lang.Float"] = { it.toInt() }
        types[Double::class.qualifiedName!!] = { it.toDouble() }
        types["java.lang.Double"] = { it.toInt() }
        types[Byte::class.qualifiedName!!] = { it.toByte() }
        types["java.lang.Byte"] = { it.toInt() }
        types[UByte::class.qualifiedName!!] = { it.toUByte() }
        types[Boolean::class.qualifiedName!!] = { it.toBoolean() }
        types["java.lang.Boolean"] = { it.toInt() }

        types[Player::class.qualifiedName!!] = { Bukkit.getPlayer(it) }
        types[OfflinePlayer::class.qualifiedName!!] = { offlinePlayerOrNull(it) }
        types[World::class.qualifiedName!!] = { Bukkit.getWorld(it) }
        types[Material::class.qualifiedName!!] = { Material.matchMaterial(it) }
    }

    private fun register(commandExecutor: CommandExecutor, aliases: Array<String>,
                         desc: String = "", usage: String = "") {
        CommandRegister.reg(plugin, commandExecutor, aliases, desc, usage);
    }
    fun register(f: KFunction<Unit>) {
        if(f.hasAnnotation<Command>()) {
            val cmdName = f.findAnnotations<Command>().first().name
            val argsParsers = mutableListOf<(String) -> Any?>({
                return@mutableListOf types[CommandSender::class.qualifiedName!!]!!(it)
            })

            for (argType in f.parameters) {
                val clazz = Class.forName(argType.type.javaType.typeName)
                if(argType.type.javaType.typeName in this.types) {
                    argsParsers.add(this.types[argType.type.javaType.typeName]!!)
                } else if(!clazz.isEnum) throw UnsupportedTypeException("Type ${argType.type.javaType.typeName} is not supported") else {
                    argsParsers.add {
                        for(e in clazz.enumConstants) {
                            if((e as Enum<*>).name.equals(it, ignoreCase = true)) return@add e;
                        }
                        throw NoSuchElementException("No such value in enum")
                    }
                }
            }

            register(CommandExecutor { sender, command, label, args ->
                val finalArgs = mutableListOf<Any?>(sender)
                finalArgs.addAll(args.mapIndexed { i, it -> argsParsers[i](it) })
                val map = mutableMapOf<KParameter, Any?>()
                var i = 0;
                for(arg in finalArgs) {
                    map[f.parameters[i]] = arg
                    ++i
                }
                f.callBy(map)
                return@CommandExecutor true;
            }, arrayOf(cmdName))
        } else throw IllegalArgumentException("It must be a command!")
    }

    fun type(clazz: KClass<*>, converter: (String) -> Any) {
        types[clazz.qualifiedName!!] = converter
    }
}