package tech.carcadex.kotlinbukkitkit.bungeecord

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.messaging.PluginMessageListener
import java.nio.ByteBuffer
import java.nio.charset.Charset


object BungeeCordController: PluginMessageListener {

    private val queue = mutableListOf<BungeeCordRequest>()
    private var initialized = false;
    private var plugin: Plugin? = null

    fun init(plugin: Plugin) {
        if(initialized) return
        this.plugin = plugin
        Bukkit.getServer().messenger.registerOutgoingPluginChannel(plugin, "BungeeCord")
        Bukkit.getServer().messenger.registerIncomingPluginChannel(plugin, "BungeeCord", this)
        initialized = true
    }

    fun Plugin.initBungeeCordController() {
        init(this)
    }

    override fun onPluginMessageReceived(channel: String, player: Player, message: ByteArray) {
        if (channel != "BungeeCord") return

        val buffer = ByteBuffer.wrap(message)
        val subChannel = buffer.readUTF()
        val request = queue.firstOrNull { it.subChannel == subChannel }
        if(request?.responseCallback != null) {
            val infoBuffer = buffer.slice()
            val info = ByteArray(infoBuffer.remaining())
            infoBuffer.get(info)
            request.responseCallback.invoke(info)
            queue.remove(request)
        }
    }

    fun sendBungeeCord(player: Player, message: ByteArray) {
        if(!initialized) {
            Bukkit.getLogger().severe("Could not execute sendBungeeCord method, because BungeeCordController" +
                    " has not been initialized!")
        }
        player.sendPluginMessage(plugin!!, "BungeeCord", message)
    }

    fun addToQueue(request: BungeeCordRequest) = queue.add(request)

    private fun ByteBuffer.readUTF() = String(ByteArray(short.toInt()).apply { get(this) }, Charset.forName("UTF-8"))
}
