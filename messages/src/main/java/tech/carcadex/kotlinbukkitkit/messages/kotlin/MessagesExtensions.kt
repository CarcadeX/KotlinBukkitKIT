package tech.carcadex.kotlinbukkitkit.messages.kotlin

import tech.carcadex.kotlinbukkitkit.messages.container.Messages
import tech.carcadex.kotlinbukkitkit.messages.model.Message
import tech.carcadex.kotlinbukkitkit.messages.verifier.MessageVerifier
import org.bukkit.command.CommandSender
import org.bukkit.plugin.Plugin
import java.io.File

fun CommandSender.send(message: Message) = message.send(this)

fun messages(init: MessagesBuilder.() -> Unit): Messages {
    val builder = MessagesBuilder()
    builder.init()
    return builder.build()
}

class MessagesBuilder(var legacy: Boolean = false,
                      var plugin: Plugin? = null,
                      var fileName: String? = null,
                      var verifier: MessageVerifier? = null,
                      var file: File? = null) {
    fun build(): Messages {
        if(plugin == null) throw RuntimeException("Missed plugin")
        return if(file == null) {
            (if(legacy) Messages.legacy(fileName ?: "messages.yml", plugin!!) else Messages.of(fileName ?: "messages.yml", plugin!!)).also { m -> verifier?.let { m.verifier(it) } }
        } else (if(legacy) Messages.legacy(file!!, plugin!!) else Messages.of(file!!, plugin!!)).also { m -> verifier?.let { m.verifier(it) } }
    }
}

fun Plugin.loadMessages(): Messages = Messages.of(this)
fun Plugin.loadMessages(filename: String): Messages = Messages.of(filename, this)