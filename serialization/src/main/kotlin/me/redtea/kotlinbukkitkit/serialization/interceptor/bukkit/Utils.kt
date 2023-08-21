/*
ORIGINAL PACKAGE: package br.com.devsrsouza.kotlinbukkitapi.serialization
ORIGINAL REPOSITORY: https://github.com/DevSrSouza/KotlinBukkitAPI
AUTHOR: https://github.com/DevSrSouza

Thanks DevSrSouza for KotlinBukkitAPI
 */

package me.redtea.kotlinbukkitkit.serialization.interceptor.bukkit

import kotlinx.serialization.descriptors.SerialDescriptor

internal inline fun <reified A : Annotation> SerialDescriptor.findElementAnnotation(
    elementIndex: Int,
): A? {
    return getElementAnnotations(elementIndex).find { it is A } as A?
}

internal inline fun <reified A : Annotation> SerialDescriptor.findEntityAnnotation(): A? {
    return annotations.find { it is A } as A?
}
