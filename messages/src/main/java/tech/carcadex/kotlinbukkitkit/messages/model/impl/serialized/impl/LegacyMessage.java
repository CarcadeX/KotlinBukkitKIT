package tech.carcadex.kotlinbukkitkit.messages.model.impl.serialized.impl;

import tech.carcadex.kotlinbukkitkit.messages.model.impl.serialized.SerializedMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class LegacyMessage extends SerializedMessage {
    public LegacyMessage(List<String> unparsed, Plugin plugin) {
        super(unparsed, LegacyComponentSerializer.builder().build(), plugin);
    }
}
