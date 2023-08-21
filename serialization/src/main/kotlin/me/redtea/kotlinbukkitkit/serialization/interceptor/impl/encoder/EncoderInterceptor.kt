/*
ORIGINAL PACKAGE: package br.com.devsrsouza.kotlinbukkitapi.serialization
ORIGINAL REPOSITORY: https://github.com/DevSrSouza/KotlinBukkitAPI
AUTHOR: https://github.com/DevSrSouza

Thanks DevSrSouza for KotlinBukkitAPI
 */

package me.redtea.kotlinbukkitkit.serialization.interceptor.impl.encoder

import me.redtea.kotlinbukkitkit.serialization.interceptor.SerializationEncodeInterceptor
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.CompositeEncoder
import kotlinx.serialization.encoding.Encoder

internal class EncoderInterceptor(
    val interceptor: SerializationEncodeInterceptor,
    val delegate: Encoder,
) : Encoder by delegate {

    override fun <T> encodeSerializableValue(serializer: SerializationStrategy<T>, value: T) {
        serializer.serialize(this, value)
    }

    override fun beginStructure(descriptor: SerialDescriptor): CompositeEncoder {
        return CompositeEncoderInterceptor(interceptor, delegate.beginStructure(descriptor))
    }

    override fun beginCollection(descriptor: SerialDescriptor, collectionSize: Int): CompositeEncoder {
        return CompositeEncoderInterceptor(interceptor, delegate.beginCollection(descriptor, collectionSize))
    }
}
