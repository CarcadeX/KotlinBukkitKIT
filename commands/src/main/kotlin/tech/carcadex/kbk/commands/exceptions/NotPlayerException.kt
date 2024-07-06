package tech.carcadex.kbk.commands.exceptions

class NotPlayerException() : CommandExecuteException(messageTag = "#for-player-only") {
}