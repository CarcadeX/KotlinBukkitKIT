/*
ORIGINAL PACKAGE: package br.com.devsrsouza.kotlinbukkitapi.serialization
ORIGINAL REPOSITORY: https://github.com/DevSrSouza/KotlinBukkitAPI
AUTHOR: https://github.com/DevSrSouza

Thanks DevSrSouza for KotlinBukkitAPI
 */

package me.redtea.kotlinbukkitkit.serialization.interceptor.impl

import me.redtea.kotlinbukkitkit.serialization.interceptor.SerializationDecodeInterceptor
import me.redtea.kotlinbukkitkit.serialization.interceptor.SerializationEncodeInterceptor
import me.redtea.kotlinbukkitkit.serialization.interceptor.impl.decoder.DeserializationStrategyInterceptor
import me.redtea.kotlinbukkitkit.serialization.interceptor.impl.encoder.SerializationStrategyInterceptor
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.StringFormat
import kotlinx.serialization.decodeFromString

internal class StringFormatInterceptor(
    private val delegate: StringFormat,
    private val encodeInterceptor: SerializationEncodeInterceptor,
    private val decodeInterceptor: SerializationDecodeInterceptor,
) : StringFormat by delegate {

    override fun <T> decodeFromString(deserializer: DeserializationStrategy<T>, raw: String): T {
        return delegate.decodeFromString(DeserializationStrategyInterceptor(decodeInterceptor, deserializer), raw)
    }

    override fun <T> encodeToString(serializer: SerializationStrategy<T>, value: T): String {
        return delegate.encodeToString(SerializationStrategyInterceptor(encodeInterceptor, serializer), value)
    }
}
