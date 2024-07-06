package tech.carcadex.kbk.messages.verifier;

import java.util.Optional;

public interface MessageVerifier {
    Optional<Object> fromDefault(String key);
}
