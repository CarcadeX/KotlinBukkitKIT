package tech.carcadex.kotlinbukkitkit.messages.factory;

import tech.carcadex.kotlinbukkitkit.messages.container.Messages;
import tech.carcadex.kotlinbukkitkit.messages.model.Message;

import java.util.List;

public interface MessageFactory {
    Message message(List<String> unparsed);
    Message message(String msg);
    Message legacyMessage(List<String> unparsed);
    Message legacyMessage(String msg);

    default Message nullMessage() {
        return Messages.NULL_MESSAGE;
    }
}
