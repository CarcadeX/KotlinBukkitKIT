package tech.carcadex.kbk.commands.reflection.service

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.World
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import tech.carcadex.kbk.commands.exceptions.UnsupportedTypeException
import tech.carcadex.kbk.extensions.offlinePlayerOrNull
import java.lang.reflect.Type
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.javaType

object TypeService {
    data class TypeContext(val arg: String, val sender: CommandSender, var errorMessage: String? = null)

    private val types = mutableMapOf<KClass<*>, (TypeContext) -> Any?>()

    //REGISTER DEFAULT TYPES
    init {
        types[CommandSender::class] = { it.arg }
        types[String::class] = { it.arg }
        types[java.lang.String::class] = { it.arg }
        types[Int::class] = { it.errorMessage = "#not-a-number"; it.arg.toInt() }
        types[java.lang.Integer::class] = { it.errorMessage = "#not-a-number"; it.arg.toInt() }
        types[UInt::class] = { it.errorMessage = "#not-a-number"; it.arg.toUInt() }
        types[Long::class] = { it.errorMessage = "#not-a-number"; it.arg.toLong() }
        types[java.lang.Long::class] = { it.errorMessage = "#not-a-number"; it.arg.toInt() }
        types[ULong::class] = { it.errorMessage = "#not-a-number"; it.arg.toULong() }
        types[Short::class] = { it.errorMessage = "#not-a-number"; it.arg.toShort() }
        types[java.lang.Short::class] = { it.arg.toInt() }
        types[UShort::class] = { it.errorMessage = "#not-a-number"; it.arg.toUShort() }
        types[Float::class] = { it.errorMessage = "#not-a-number"; it.arg.toFloat() }
        types[java.lang.Float::class] = { it.errorMessage = "#not-a-number"; it.arg.toInt() }
        types[Double::class] = { it.errorMessage = "#not-a-number"; it.arg.toDouble() }
        types[java.lang.Double::class] = { it.errorMessage = "#not-a-number"; it.arg.toInt() }
        types[Byte::class] = { it.errorMessage = "#not-a-number"; it.arg.toByte() }
        types[java.lang.Byte::class] = { it.errorMessage = "#not-a-number"; it.arg.toInt() }
        types[UByte::class] = { it.errorMessage = "#not-a-number"; it.arg.toUByte() }
        types[Boolean::class] = { it.errorMessage = "#not-a-number"; it.arg.toBoolean() }
        types[java.lang.Boolean::class] = { it.errorMessage = "#not-a-number"; it.arg.toInt() }

        types[Player::class] = {
            Bukkit.getPlayer(it.arg).also { o -> if(o == null) it.errorMessage = "#no-such-player" }
        }
        types[OfflinePlayer::class] = {
            offlinePlayerOrNull(it.arg).also { o -> if(o == null) it.errorMessage = "#no-such-player" }
        }
        types[World::class] = { Bukkit.getWorld(it.arg).also { o -> if(o == null) it.errorMessage = "#no-such-world" } }

        types[Material::class] = { Material.matchMaterial(it.arg).also { o ->
            if(o == null) it.errorMessage = "#no-such-material" } }
//        types[CommandSender::class.qualifiedName!!] = { it }
//        types[String::class.qualifiedName!!] = { it }
//        types["java.lang.String"] = { it }
//        types[Int::class.qualifiedName!!] = { it.errorMessage = "#not-a-number"; it.arg.toInt() }
//        types["java.lang.Integer"] = { it.errorMessage = "#not-a-number"; it.arg.toInt() }
//        types[UInt::class.qualifiedName!!] = { it.errorMessage = "#not-a-number"; it.arg.toUInt() }
//        types[Long::class.qualifiedName!!] = { it.errorMessage = "#not-a-number"; it.arg.toLong() }
//        types["java.lang.Long"] = { it.errorMessage = "#not-a-number"; it.arg.toInt() }
//        types[ULong::class.qualifiedName!!] = { it.errorMessage = "#not-a-number"; it.arg.toULong() }
//        types[Short::class.qualifiedName!!] = { it.errorMessage = "#not-a-number"; it.arg.toShort() }
//        types["java.lang.Short"] = { it.arg.toInt() }
//        types[UShort::class.qualifiedName!!] = { it.errorMessage = "#not-a-number"; it.arg.toUShort() }
//        types[Float::class.qualifiedName!!] = { it.errorMessage = "#not-a-number"; it.arg.toFloat() }
//        types["java.lang.Float"] = { it.errorMessage = "#not-a-number"; it.arg.toInt() }
//        types[Double::class.qualifiedName!!] = { it.errorMessage = "#not-a-number"; it.arg.toDouble() }
//        types["java.lang.Double"] = { it.errorMessage = "#not-a-number"; it.arg.toInt() }
//        types[Byte::class.qualifiedName!!] = { it.errorMessage = "#not-a-number"; it.arg.toByte() }
//        types["java.lang.Byte"] = { it.errorMessage = "#not-a-number"; it.arg.toInt() }
//        types[UByte::class.qualifiedName!!] = { it.errorMessage = "#not-a-number"; it.arg.toUByte() }
//        types[Boolean::class.qualifiedName!!] = { it.errorMessage = "#not-a-number"; it.arg.toBoolean() }
//        types["java.lang.Boolean"] = { it.errorMessage = "#not-a-number"; it.arg.toInt() }
//
//        types[Player::class.qualifiedName!!] = {
//            Bukkit.getPlayer(it.arg).also { o -> if(o == null) it.errorMessage = "#no-such-player" }
//        }
//        types[OfflinePlayer::class.qualifiedName!!] = {
//            offlinePlayerOrNull(it.arg).also { o -> if(o == null) it.errorMessage = "#no-such-player" }
//        }
//        types[World::class.qualifiedName!!] = { Bukkit.getWorld(it.arg).also { o -> if(o == null) it.errorMessage = "#no-such-world" } }
//
//        types[Material::class.qualifiedName!!] = { Material.matchMaterial(it.arg).also { o ->
//            if(o == null) it.errorMessage = "#no-such-material" } }
    }

    fun registered(clazz: KClass<*>): Boolean = clazz in types
    fun registered(clazz: Class<*>): Boolean = registered(clazz.kotlin)
    fun registered(name: String): Boolean = registered(Class.forName(name))
    fun registered(type: Type): Boolean = registered(type as Class<*>)
    @OptIn(ExperimentalStdlibApi::class)
    fun registered(type: KType): Boolean = registered(type.javaType)

    fun register(clazz: KClass<*>, func: (TypeContext) -> Any) {
        types[clazz] = func
    }

    fun byClass(clazz: KClass<*>): (TypeContext) -> Any? = types[clazz] ?: throw UnsupportedTypeException(
            "Type ${clazz.qualifiedName} is not supported!")
    fun byClass(clazz: Class<*>): (TypeContext) -> Any? = types[clazz.kotlin] ?: throw UnsupportedTypeException(
        "Type ${clazz.canonicalName} is not supported!")
    fun byClassName(name: String) = byClass(Class.forName(name))
    fun byType(type: Type): (TypeContext) -> Any? = byClass(type as Class<*>)
    @OptIn(ExperimentalStdlibApi::class)
    fun byType(type: KType): (TypeContext) -> Any? = byType(type.javaType)
}