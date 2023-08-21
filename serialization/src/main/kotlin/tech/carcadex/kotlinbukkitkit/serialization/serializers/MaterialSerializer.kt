/*
ORIGINAL PACKAGE: package br.com.devsrsouza.kotlinbukkitapi.serialization
ORIGINAL REPOSITORY: https://github.com/DevSrSouza/KotlinBukkitAPI
AUTHOR: https://github.com/DevSrSouza

Thanks DevSrSouza for KotlinBukkitAPI
 */

package tech.carcadex.kotlinbukkitkit.serialization.serializers

import tech.carcadex.kotlinbukkitkit.serialization.serializers.exceptions.MaterialNotFoundException
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bukkit.Material

public object MaterialSerializer : KSerializer<Material> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("org.bukkit.Material", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Material {
        val materialName = decoder.decodeString()

        return Material.values().find { it.name.equals(materialName, true) }
            ?: throw MaterialNotFoundException(materialName)
    }

    override fun serialize(encoder: Encoder, value: Material) {
        encoder.encodeString(value.name.toLowerCase())
    }
}
