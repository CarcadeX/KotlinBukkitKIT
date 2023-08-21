/*
ORIGINAL PACKAGE: package br.com.devsrsouza.kotlinbukkitapi.serialization
ORIGINAL REPOSITORY: https://github.com/DevSrSouza/KotlinBukkitAPI
AUTHOR: https://github.com/DevSrSouza

Thanks DevSrSouza for KotlinBukkitAPI
 */

package tech.carcadex.kotlinbukkitkit.serialization.interceptor.bukkit

import tech.carcadex.kotlinbukkitkit.extensions.reverseTranslateColor
import tech.carcadex.kotlinbukkitkit.serialization.ChangeColor
import tech.carcadex.kotlinbukkitkit.serialization.interceptor.ClearSerializationEncodeInterceptor
import tech.carcadex.kotlinbukkitkit.serialization.interceptor.SerializationEncodeInterceptor
import kotlinx.serialization.descriptors.SerialDescriptor

public object BukkitSerializationEncodeInterceptor : SerializationEncodeInterceptor by ClearSerializationEncodeInterceptor() {

    override fun encodeString(descriptor: SerialDescriptor, index: Int, value: String): String {
        return useChangeColor(descriptor, index, value)
    }

    private fun useChangeColor(descriptor: SerialDescriptor, index: Int, value: String): String {
        descriptor.findElementAnnotation<tech.carcadex.kotlinbukkitkit.serialization.ChangeColor>(index) ?: return value

        return value.reverseTranslateColor()
    }
}
