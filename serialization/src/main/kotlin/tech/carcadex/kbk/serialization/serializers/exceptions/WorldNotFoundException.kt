package tech.carcadex.kbk.serialization.serializers.exceptions

public class WorldNotFoundException(public val world: String) : Exception("Could not deserialize the World=$world because was not found on the Server.")