/*
ORIGINAL PACKAGE: package br.com.devsrsouza.kotlinbukkitapi.architecture
ORIGINAL REPOSITORY: https://github.com/DevSrSouza/KotlinBukkitAPI
AUTHOR: https://github.com/DevSrSouza

Thanks DevSrSouza for KotlinBukkitAPI
 */
package me.redtea.kotlinbukkitkit.architecture.extensions

import org.bukkit.plugin.Plugin

// TODO: make it a experimental API that will be replaced with Context Receivers in the future
public interface WithPlugin<T : Plugin> { public val plugin: T }
