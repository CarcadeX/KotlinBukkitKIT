/*
ORIGINAL PACKAGE: package br.com.devsrsouza.kotlinbukkitapi.serialization
ORIGINAL REPOSITORY: https://github.com/DevSrSouza/KotlinBukkitAPI
AUTHOR: https://github.com/DevSrSouza

Thanks DevSrSouza for KotlinBukkitAPI
 */

package tech.carcadex.kbk.serialization.interceptor.impl.decoder

import tech.carcadex.kbk.serialization.interceptor.SerializationDecodeInterceptor
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder

internal class DecoderInterceptor(
    val interceptor: SerializationDecodeInterceptor,
    val delegate: Decoder,
) : Decoder by delegate {
    override fun <T> decodeSerializableValue(deserializer: DeserializationStrategy<T>): T {
        return deserializer.deserialize(this)
    }

    override fun beginStructure(descriptor: SerialDescriptor): CompositeDecoder {
        return CompositeDecoderInterceptor(interceptor, delegate.beginStructure(descriptor))
    }
}
