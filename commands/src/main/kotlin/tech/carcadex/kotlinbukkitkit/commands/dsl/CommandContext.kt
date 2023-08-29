package tech.carcadex.kotlinbukkitkit.commands.dsl

/**
 * Class that contains command data
 * @param name name of command
 * @param aliases array of aliases of this command (includes name of command)
 * @param usage usage message of command
 * @param description description message of command
 * @param permission permission that requires to use this command (set empty string if
 * command did not requires any permissions)
 *
 * @author itzRedTea
 * @since 1.0.0
 */
class CommandContext(val name: String,
                          var aliases: Array<String> = arrayOf(name),
                          var usage: String = "",
                          var description: String = "",
                          var permission: String = "")