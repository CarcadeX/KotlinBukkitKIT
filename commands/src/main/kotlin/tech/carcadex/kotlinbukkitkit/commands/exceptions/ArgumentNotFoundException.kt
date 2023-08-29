package tech.carcadex.kotlinbukkitkit.commands.exceptions

class ArgumentNotFoundException(index: Int) : NoSuchElementException("Argument with index $index not found! Args array out of bounds") {
}