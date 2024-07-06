/*
ORIGINAL PACKAGE: package br.com.devsrsouza.kotlinbukkitapi.serialization
ORIGINAL REPOSITORY: https://github.com/DevSrSouza/KotlinBukkitAPI
AUTHOR: https://github.com/DevSrSouza

Thanks DevSrSouza for KotlinBukkitAPI
 */

package tech.carcadex.kbk.serialization.interceptor.impl.decoder

import tech.carcadex.kbk.serialization.interceptor.SerializationDecodeInterceptor
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.encoding.Decoder

internal class DeserializationStrategyInterceptor<T>(
    val interceptor: SerializationDecodeInterceptor,
    val delegate: DeserializationStrategy<T>,
) : DeserializationStrategy<T> by delegate {
    override fun deserialize(decoder: Decoder): T {
        return delegate.deserialize(DecoderInterceptor(interceptor, decoder))
    }
}
