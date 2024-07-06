package tech.carcadex.kbk.serialization.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bukkit.inventory.ItemStack
import org.bukkit.util.io.BukkitObjectInputStream
import org.bukkit.util.io.BukkitObjectOutputStream
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream


public object ItemStackSerializer : KSerializer<ItemStack> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("org.bukkit.inventory.ItemStack", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): ItemStack {
        ByteArrayInputStream(Base64Coder.decode(decoder.decodeString()))
            .use { inputStream ->
                BukkitObjectInputStream(inputStream)
                    .use { dataInput -> return dataInput.readObject() as ItemStack }
            }
    }

    override fun serialize(encoder: Encoder, value: ItemStack) {
        ByteArrayOutputStream().use { outputStream ->
            BukkitObjectOutputStream(outputStream).use { dataOutput ->
                dataOutput.writeObject(value)
                encoder.encodeString(String(Base64Coder.encode(outputStream.toByteArray())))
            }
        }
    }
}

