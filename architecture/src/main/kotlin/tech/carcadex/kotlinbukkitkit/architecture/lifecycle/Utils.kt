/*
ORIGINAL PACKAGE: package br.com.devsrsouza.kotlinbukkitapi.architecture
ORIGINAL REPOSITORY: https://github.com/DevSrSouza/KotlinBukkitAPI
AUTHOR: https://github.com/DevSrSouza

Thanks DevSrSouza for KotlinBukkitAPI
 */
package tech.carcadex.kotlinbukkitkit.architecture.lifecycle

import tech.carcadex.kotlinbukkitkit.architecture.KotlinPlugin

public inline fun <reified T : PluginLifecycleListener> KotlinPlugin.getOrInsertGenericLifecycle(
    priority: Int,
    factory: () -> T,
): T {
    return lifecycleListeners
        .map { it.listener }
        .filterIsInstance<T>()
        .firstOrNull()
        ?: factory().also { registerKotlinPluginLifecycle(priority, it) }
}
