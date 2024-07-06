package tech.carcadex.kbk.commands.reflection

data class CommandParams(
    val name: String,
    val desc: String,
    val usage: String,
    val perm: String?,
    val aliases: List<String>,
    val completeTags: Array<String>,
    val isLastOptional: Boolean,
)