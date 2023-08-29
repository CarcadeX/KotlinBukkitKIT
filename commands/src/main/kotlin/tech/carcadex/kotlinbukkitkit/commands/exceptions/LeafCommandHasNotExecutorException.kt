package tech.carcadex.kotlinbukkitkit.commands.exceptions

class LeafCommandHasNotExecutorException(commandName: String)
    : IllegalArgumentException("Command $commandName has not subcommands or default executor!") {
}