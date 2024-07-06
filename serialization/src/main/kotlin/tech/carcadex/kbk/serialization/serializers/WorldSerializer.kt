/*
ORIGINAL PACKAGE: package br.com.devsrsouza.kotlinbukkitapi.serialization
ORIGINAL REPOSITORY: https://github.com/DevSrSouza/KotlinBukkitAPI
AUTHOR: https://github.com/DevSrSouza

Thanks DevSrSouza for KotlinBukkitAPI
 */


package tech.carcadex.kbk.serialization.serializers

import tech.carcadex.kbk.serialization.serializers.exceptions.WorldNotFoundException
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bukkit.Bukkit
import org.bukkit.World

public object WorldSerializer : KSerializer<World> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("org.bukkit.World", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): World {
        val worldName = decoder.decodeString()

        return Bukkit.getWorld(worldName) ?: throw WorldNotFoundException(worldName)
    }

    override fun serialize(encoder: Encoder, value: World) {
        encoder.encodeString(value.name)
    }
}
