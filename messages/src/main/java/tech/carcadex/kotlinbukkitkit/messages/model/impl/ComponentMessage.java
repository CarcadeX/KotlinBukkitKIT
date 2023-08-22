package tech.carcadex.kotlinbukkitkit.messages.model.impl;

import tech.carcadex.kotlinbukkitkit.messages.model.AbstractMessage;
import tech.carcadex.kotlinbukkitkit.messages.model.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class ComponentMessage extends AbstractMessage {
    private static final MiniMessage mm = MiniMessage.builder().build();;
    private final Plugin plugin;

    public ComponentMessage(List<String> unparsed, Plugin plugin) {
        super(unparsed, plugin);
        this.plugin = plugin;
    }

    @Override
    protected Component parse(String unparsed) {
        return mm.deserialize(unparsed);
    }

    @Override
    public @NotNull Message replaceAll(@NotNull String from, @NotNull String to) {
        return new ComponentMessage(unparsed.stream().map(it -> it.replaceAll(from, to)).collect(Collectors.toList()), plugin);
    }
}
