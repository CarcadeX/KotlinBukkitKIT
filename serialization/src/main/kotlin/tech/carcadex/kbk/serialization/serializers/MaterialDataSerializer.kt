/*
ORIGINAL PACKAGE: package br.com.devsrsouza.kotlinbukkitapi.serialization
ORIGINAL REPOSITORY: https://github.com/DevSrSouza/KotlinBukkitAPI
AUTHOR: https://github.com/DevSrSouza

Thanks DevSrSouza for KotlinBukkitAPI
 */

package tech.carcadex.kbk.serialization.serializers

import tech.carcadex.kbk.extensions.asMaterialData
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bukkit.Material
import org.bukkit.material.MaterialData

@Serializer(forClass = MaterialData::class)
public object MaterialDataSerializer : KSerializer<MaterialData> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        "org.bukkit.material.MaterialData",
        PrimitiveKind.STRING,
    )

    override fun deserialize(decoder: Decoder): MaterialData {
        return fromString(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, value: MaterialData) {
        encoder.encodeString(toString(value))
    }

    private fun toString(material: MaterialData): String {
        return "${material.itemType}:${material.data}"
    }

    private fun fromString(content: String): MaterialData {
        val slices = content.split(":")

        val material = slices[0]
        val data = slices.getOrNull(1)?.toIntOrNull() ?: 0

        return Material.getMaterial(material.toUpperCase())!!.asMaterialData(data.toByte())
    }
}
