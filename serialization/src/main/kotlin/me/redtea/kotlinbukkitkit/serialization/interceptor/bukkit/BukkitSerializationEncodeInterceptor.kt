/*
ORIGINAL PACKAGE: package br.com.devsrsouza.kotlinbukkitapi.serialization
ORIGINAL REPOSITORY: https://github.com/DevSrSouza/KotlinBukkitAPI
AUTHOR: https://github.com/DevSrSouza

Thanks DevSrSouza for KotlinBukkitAPI
 */

package me.redtea.kotlinbukkitkit.serialization.interceptor.bukkit

import me.redtea.kotlinbukkitkit.extensions.reverseTranslateColor
import me.redtea.kotlinbukkitkit.serialization.ChangeColor
import me.redtea.kotlinbukkitkit.serialization.interceptor.ClearSerializationEncodeInterceptor
import me.redtea.kotlinbukkitkit.serialization.interceptor.SerializationEncodeInterceptor
import kotlinx.serialization.descriptors.SerialDescriptor

public object BukkitSerializationEncodeInterceptor : SerializationEncodeInterceptor by ClearSerializationEncodeInterceptor() {

    override fun encodeString(descriptor: SerialDescriptor, index: Int, value: String): String {
        return useChangeColor(descriptor, index, value)
    }

    private fun useChangeColor(descriptor: SerialDescriptor, index: Int, value: String): String {
        descriptor.findElementAnnotation<me.redtea.kotlinbukkitkit.serialization.ChangeColor>(index) ?: return value

        return value.reverseTranslateColor()
    }
}
