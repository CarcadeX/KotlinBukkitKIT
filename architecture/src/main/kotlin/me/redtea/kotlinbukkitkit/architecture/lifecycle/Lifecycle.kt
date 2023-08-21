/*
ORIGINAL PACKAGE: package br.com.devsrsouza.kotlinbukkitapi.architecture
ORIGINAL REPOSITORY: https://github.com/DevSrSouza/KotlinBukkitAPI
AUTHOR: https://github.com/DevSrSouza

Thanks DevSrSouza for KotlinBukkitAPI
 */
package me.redtea.kotlinbukkitkit.architecture.lifecycle

/**
 * Holds a lifecycle listener class and its priority
 */
public data class Lifecycle(
    val priority: Int,
    val listener: PluginLifecycleListener,
) : Comparable<Lifecycle> {

    override fun compareTo(
        other: Lifecycle,
    ): Int = other.priority.compareTo(priority)
}
