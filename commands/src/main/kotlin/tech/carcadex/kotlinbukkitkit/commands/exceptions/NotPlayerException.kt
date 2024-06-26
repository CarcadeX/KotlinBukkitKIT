package tech.carcadex.kotlinbukkitkit.commands.exceptions

class NotPlayerException() : CommandExecuteException(messageTag = "#for-player-only") {
}