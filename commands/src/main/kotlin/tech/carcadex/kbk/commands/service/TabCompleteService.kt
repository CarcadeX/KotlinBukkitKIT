package tech.carcadex.kbk.commands.service

import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class TabCompleteService {
    private val completes = mutableMapOf<String, (CommandSender) -> List<String>>()
    init {
        //REGISTER DEFAULT COMPLETES
        completes["#players"] = { Bukkit.getOnlinePlayers().map { it.name } }
        completes["#offlinePlayers"] = { Bukkit.getOfflinePlayers().map { it.name ?: "" } }
        completes["#worlds"] = { Bukkit.getWorlds().map { it.name } }
        completes["#empty"] = { emptyList() }
    }

    fun register(tag: String, func: (CommandSender) -> List<String>) {
        completes[tag] = func
    }

    fun completerReflection(list: MutableList<String>, params: List<Class<*>>): TabCompleter {
        val complete: MutableList<(CommandSender) -> List<String>> = ArrayList(params.size)
        for(i in params.indices) complete.add { mutableListOf() }
        for((i, tag) in list.withIndex()) {
            complete[i] = reflectionCompleteParser(tag, params[i+if(params[0] == CommandSender::class.java) 1 else 0])
        }
        return createTabCompleter(complete)
    }

    fun completer(list: List<String>): TabCompleter {
        return createTabCompleter(parseTags(list))
    }

    fun parseTags(list: List<String>): List<(CommandSender) -> List<String>> = list.map { defaultCompleteParse(it) }

    private fun createTabCompleter(completes: List<(CommandSender) -> List<String>>): TabCompleter =
        TabCompleter { sender, _, _, args ->
            if(args.size <= completes.size)
                completes[args.size-1](sender)
            else mutableListOf()
        }



    private fun reflectionCompleteParser(tagLine: String, paramClass: Class<*>): (CommandSender) -> List<String> =
        when(tagLine.split(":")[0]) {
            "#enum" -> { _ -> enumReflectionParse(paramClass) }
            else -> defaultCompleteParse(tagLine)
        }


    private fun defaultCompleteParse(tagLine: String): (CommandSender) -> List<String> =
        if(tagLine in completes) completes[tagLine]!! else
        when(tagLine.split(":")[0]) {
            "#range" -> { _ -> rangeCompleteParse(tagLine) }
            else -> { _ -> emptyList() }
        }



    private fun rangeCompleteParse(tagLine: String): List<String> {
        val diapason = tagLine.replace("#range:", "").split("-");
        return (diapason[0].toInt()..diapason[1].toInt()).map { it.toString() }
    }

    private fun enumReflectionParse(enumClass: Class<*>): List<String> {
        if(enumClass.isEnum) { return enumClass.enumConstants.map { (it as Enum<*>).name }
        } else throw IllegalArgumentException("#enum complete but param ${enumClass.canonicalName} is not enum")
    }
}