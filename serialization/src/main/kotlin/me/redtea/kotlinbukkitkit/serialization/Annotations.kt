/*
ORIGINAL PACKAGE: package br.com.devsrsouza.kotlinbukkitapi.serialization
ORIGINAL REPOSITORY: https://github.com/DevSrSouza/KotlinBukkitAPI
AUTHOR: https://github.com/DevSrSouza

Thanks DevSrSouza for KotlinBukkitAPI
 */


package me.redtea.kotlinbukkitkit.serialization

import kotlinx.serialization.SerialInfo

/**
 * Annotation used to automatically translate color codes from the configuration texts
 * when using Kotlinx.serialization and KotlinBukkitAPI [SerializtionConfig] ([KotlinPlugin.config]).
 */
@SerialInfo
@Target(AnnotationTarget.PROPERTY)
public annotation class ChangeColor
