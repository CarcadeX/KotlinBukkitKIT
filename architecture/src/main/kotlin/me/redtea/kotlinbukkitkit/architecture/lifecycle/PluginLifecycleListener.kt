/*
ORIGINAL PACKAGE: package br.com.devsrsouza.kotlinbukkitapi.architecture
ORIGINAL REPOSITORY: https://github.com/DevSrSouza/KotlinBukkitAPI
AUTHOR: https://github.com/DevSrSouza

Thanks DevSrSouza for KotlinBukkitAPI
 */
package me.redtea.kotlinbukkitkit.architecture.lifecycle

public enum class LifecycleEvent { LOAD, ENABLE, DISABLE, CONFIG_RELOAD, ALL_CONFIG_RELOAD }

public typealias PluginLifecycleListener = (LifecycleEvent) -> Unit
