/*
ORIGINAL PACKAGE: package br.com.devsrsouza.kotlinbukkitapi.serialization
ORIGINAL REPOSITORY: https://github.com/DevSrSouza/KotlinBukkitAPI
AUTHOR: https://github.com/DevSrSouza

Thanks DevSrSouza for KotlinBukkitAPI
 */

package me.redtea.kotlinbukkitkit.serialization.architecture

import me.redtea.kotlinbukkitkit.architecture.KotlinPlugin
import me.redtea.kotlinbukkitkit.architecture.lifecycle.LifecycleListener
import me.redtea.kotlinbukkitkit.serialization.KotlinConfigEvent
import me.redtea.kotlinbukkitkit.serialization.SerializationConfig
import me.redtea.kotlinbukkitkit.serialization.architecture.impl.ConfigDelegate
import me.redtea.kotlinbukkitkit.serialization.architecture.impl.getOrInsertConfigLifecycle
import me.redtea.kotlinbukkitkit.serialization.architecture.impl.registerConfiguration
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.StringFormat
import kotlinx.serialization.serializer
import java.io.File
import kotlin.reflect.KType
import kotlin.reflect.typeOf

/**
 * Loads the file with the given [serializer].
 *
 * If the file not exist, one will be created with the [defaultModel] serialize into it.
 *
 * If [KotlinPlugin.reloadConfig] get called will reload the Config.
 *
 * KotlinBukkitAPI provide Kotlinx.Serialization Contextual Serializers for a couple Bukkit objects,
 * this is provided by the BukkitSerialModule(), if do you have a custom SerialModule you should add
 * the BukkitSerialModule by `yourSerialModule + BukkitSerialModule()`. The provided Bukkit types are:
 * Block, Chunk, Location, Material, MaterialData, World.
 *
 * Custom annotations:
 * - `@ChangeColor` in a String property translate the color codes from the Configuration, saves in `&` and loads in ``.
 *
 * @param file: The file name in your [dataFolder] (like config.yml).
 * @param loadOnEnable: If true, loads your configuration just when the server enable,
 * otherwise, load at the call of this function. This could be usage if your configuration
 * uses a parser that Parser a Location or a World that is not loaded yet.
 * @param saveOnDisable: If true, saves the current [SerializationConfig.model] to the configuration file.
 */
public fun <T : Any> KotlinPlugin.config(
    file: String,
    defaultModel: T,
    serializer: KSerializer<T>,
    type: StringFormat,
    loadOnEnable: Boolean = false,
    saveOnDisable: Boolean = false,
    alwaysRestoreDefaults: Boolean = true,
): me.redtea.kotlinbukkitkit.serialization.SerializationConfig<T> {
    val configFile = File(dataFolder, file)

    return SerializationConfig(
        defaultModel,
        configFile,
        serializer,
        type,
        alwaysRestoreDefaults,
        eventObservable = {
            if (it == KotlinConfigEvent.RELOAD) {
                someConfigReloaded()
            }
        },
    ).also {
        registerConfiguration(it as me.redtea.kotlinbukkitkit.serialization.SerializationConfig<Any>, loadOnEnable, saveOnDisable)
    }
}

/**
 * Gets the config for the given [KType]
 */
public fun LifecycleListener<*>.getConfig(type: KType): me.redtea.kotlinbukkitkit.serialization.SerializationConfig<*> {
    try {
        val serialName = serializer(type).descriptor.serialName
        val config = plugin.getOrInsertConfigLifecycle().serializationConfigurations[serialName]

        requireNotNull(config) { "Could not find this type registred as a Config." }

        return config
    } catch (e: SerializationException) {
        throw IllegalArgumentException("The config given type is not a serializable one.")
    }
}

/**
 * Config delegate that caches the config reference.
 */

public fun <T : Any> LifecycleListener<*>.config(type: KType): ConfigDelegate<T, T> {
    return config(type, { this })
}

@OptIn(ExperimentalStdlibApi::class)
public inline fun <reified T : Any> LifecycleListener<*>.config(): ConfigDelegate<T, T> = config<T>(typeOf<T>())

public fun <T : Any, R> LifecycleListener<*>.config(
    type: KType,
    deep: T.() -> R,
): ConfigDelegate<T, R> {
    return ConfigDelegate(type, deep)
}

@OptIn(ExperimentalStdlibApi::class)
public inline fun <reified T : Any, R> LifecycleListener<*>.config(
    noinline deep: T.() -> R,
): ConfigDelegate<T, R> = config(typeOf<T>(), deep)
