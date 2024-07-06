/*
ORIGINAL PACKAGE: package br.com.devsrsouza.kotlinbukkitapi.serialization
ORIGINAL REPOSITORY: https://github.com/DevSrSouza/KotlinBukkitAPI
AUTHOR: https://github.com/DevSrSouza

Thanks DevSrSouza for KotlinBukkitAPI
 */

package tech.carcadex.kbk.serialization

import tech.carcadex.kbk.serialization.interceptor.bukkit.BukkitSerializationDecodeInterceptor
import tech.carcadex.kbk.serialization.interceptor.bukkit.BukkitSerializationEncodeInterceptor
import tech.carcadex.kbk.serialization.interceptor.impl.StringFormatInterceptor
import kotlinx.serialization.KSerializer
import kotlinx.serialization.StringFormat
import java.io.File

public typealias KotlinConfigEventObservable = (tech.carcadex.kbk.serialization.KotlinConfigEvent) -> Unit

public enum class KotlinConfigEvent { SAVE, RELOAD }

/**
 * A helper class to work with Kotlinx.serialization for Bukkit plugins config.
 *
 * Additional features: Using a interceptor we are able to add new annotations to the Kotlinx.serialization.
 *
 * `@ChangeColor` in a String property translate the color codes from the Configuration, saves in `&` and loads in ``.
 */
public class SerializationConfig<T : Any>(
    public val defaultModel: T,
    public val file: File,
    public val serializer: KSerializer<T>,
    stringFormat: StringFormat,
    public val alwaysRestoreDefaults: Boolean,
    public val eventObservable: tech.carcadex.kbk.serialization.KotlinConfigEventObservable? = null,
) {
    public lateinit var config: T private set

    public val stringFormat: StringFormat = StringFormatInterceptor(
        stringFormat,
        BukkitSerializationEncodeInterceptor,
        BukkitSerializationDecodeInterceptor,
    )

    public fun load() {
        createFileIfNotExist()

        loadFromFile()
    }

    /**
     * Save the current values of [model] in the configuration file.
     */
    public fun save(): tech.carcadex.kbk.serialization.SerializationConfig<T> = apply {
        saveToFile(config)

        eventObservable?.invoke(tech.carcadex.kbk.serialization.KotlinConfigEvent.SAVE)
    }

    /**
     * Reloads the current values from the configuration to the [model].
     */
    public fun reload(): tech.carcadex.kbk.serialization.SerializationConfig<T> = apply {
        loadFromFile()

        eventObservable?.invoke(tech.carcadex.kbk.serialization.KotlinConfigEvent.RELOAD)
    }

    private fun loadFromFile() {
        config = stringFormat.decodeFromString(serializer, file.readText())

        if (alwaysRestoreDefaults) {
            saveToFile(config)
        }
    }

    private fun stringifyModel(value: T) = stringFormat.encodeToString(serializer, value)

    private fun saveToFile(value: T) {
        val content = stringifyModel(value)
        file.writeText(content)
    }

    private fun createFileIfNotExist() {
        if (!file.exists()) {
            file.parentFile.mkdirs()
            file.createNewFile()

            saveToFile(defaultModel)
        }
    }
}
