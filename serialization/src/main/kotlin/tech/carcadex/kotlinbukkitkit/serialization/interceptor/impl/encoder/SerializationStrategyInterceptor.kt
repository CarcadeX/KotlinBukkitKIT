/*
ORIGINAL PACKAGE: package br.com.devsrsouza.kotlinbukkitapi.serialization
ORIGINAL REPOSITORY: https://github.com/DevSrSouza/KotlinBukkitAPI
AUTHOR: https://github.com/DevSrSouza

Thanks DevSrSouza for KotlinBukkitAPI
 */

package tech.carcadex.kotlinbukkitkit.serialization.interceptor.impl.encoder

import tech.carcadex.kotlinbukkitkit.serialization.interceptor.SerializationEncodeInterceptor
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.encoding.Encoder

internal class SerializationStrategyInterceptor<T>(
    val interceptor: SerializationEncodeInterceptor,
    val delegate: SerializationStrategy<T>,
) : SerializationStrategy<T> by delegate {
    override fun serialize(encoder: Encoder, value: T) {
        delegate.serialize(EncoderInterceptor(interceptor, encoder), value)
    }
}
