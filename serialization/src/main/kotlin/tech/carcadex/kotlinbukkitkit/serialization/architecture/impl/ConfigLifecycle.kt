/*
ORIGINAL PACKAGE: package br.com.devsrsouza.kotlinbukkitapi.serialization
ORIGINAL REPOSITORY: https://github.com/DevSrSouza/KotlinBukkitAPI
AUTHOR: https://github.com/DevSrSouza

Thanks DevSrSouza for KotlinBukkitAPI
 */

package tech.carcadex.kotlinbukkitkit.serialization.architecture.impl

import tech.carcadex.kotlinbukkitkit.architecture.KotlinPlugin
import tech.carcadex.kotlinbukkitkit.architecture.extensions.WithPlugin
import tech.carcadex.kotlinbukkitkit.architecture.lifecycle.LifecycleEvent
import tech.carcadex.kotlinbukkitkit.architecture.lifecycle.LifecycleListener
import tech.carcadex.kotlinbukkitkit.architecture.lifecycle.PluginLifecycleListener
import tech.carcadex.kotlinbukkitkit.architecture.lifecycle.getOrInsertGenericLifecycle
import tech.carcadex.kotlinbukkitkit.serialization.SerializationConfig
import tech.carcadex.kotlinbukkitkit.serialization.architecture.getConfig
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty
import kotlin.reflect.KType

internal fun KotlinPlugin.getOrInsertConfigLifecycle(): ConfigLifecycle {
    // MAX_VALUE = the last to execute onDisable and the first to loaded
    return getOrInsertGenericLifecycle(Int.MAX_VALUE) {
        ConfigLifecycle(this)
    }
}

internal class ConfigLifecycle(
    override val plugin: KotlinPlugin,
) : PluginLifecycleListener, WithPlugin<KotlinPlugin> {
    // String = Descriptor name
    internal val serializationConfigurations = hashMapOf<String, tech.carcadex.kotlinbukkitkit.serialization.SerializationConfig<Any>>()

    internal val onEnableLoadSerializationConfigurations = mutableListOf<tech.carcadex.kotlinbukkitkit.serialization.SerializationConfig<*>>()
    internal val onDisableSaveSerializationConfigurations = mutableListOf<tech.carcadex.kotlinbukkitkit.serialization.SerializationConfig<*>>()

    override fun invoke(event: LifecycleEvent) {
        when (event) {
            LifecycleEvent.ENABLE -> onPluginEnable()
            LifecycleEvent.DISABLE -> onPluginDisable()
            LifecycleEvent.ALL_CONFIG_RELOAD -> onConfigReload()
            else -> {}
        }
    }

    fun onPluginEnable() {
        for (config in onEnableLoadSerializationConfigurations)
            config.load()
    }

    fun onPluginDisable() {
        for (config in onDisableSaveSerializationConfigurations)
            config.save()
    }

    fun onConfigReload() {
        for (config in serializationConfigurations.values)
            config.reload()
    }
}

internal fun KotlinPlugin.registerConfiguration(
    config: tech.carcadex.kotlinbukkitkit.serialization.SerializationConfig<Any>,
    loadOnEnable: Boolean,
    saveOnDisable: Boolean,
) {
    val lifecycle = getOrInsertConfigLifecycle()

    lifecycle.serializationConfigurations.put(config.serializer.descriptor.serialName, config)

    if (loadOnEnable) {
        val configLifecycle = getOrInsertConfigLifecycle()
        configLifecycle.onEnableLoadSerializationConfigurations.add(config)
    } else {
        config.load()
    }

    if (saveOnDisable) {
        val configLifecycle = getOrInsertConfigLifecycle()
        configLifecycle.onDisableSaveSerializationConfigurations.add(config)
    }
}

public class ConfigDelegate<T, R>(
    public val type: KType,
    public val deep: T.() -> R,
) : ReadOnlyProperty<LifecycleListener<*>, R> {
    private var configCache: tech.carcadex.kotlinbukkitkit.serialization.SerializationConfig<*>? = null

    override fun getValue(
        thisRef: LifecycleListener<*>,
        property: KProperty<*>,
    ): R {
        if (configCache == null) {
            val config = thisRef.getConfig(type)

            configCache = config
            // TODO: listen when the config reloads and update the cache value
        }

        return deep(configCache!!.config as T)
    }
}
