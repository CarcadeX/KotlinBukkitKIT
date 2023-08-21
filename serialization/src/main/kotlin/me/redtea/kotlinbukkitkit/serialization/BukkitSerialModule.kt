/*
ORIGINAL PACKAGE: package br.com.devsrsouza.kotlinbukkitapi.serialization
ORIGINAL REPOSITORY: https://github.com/DevSrSouza/KotlinBukkitAPI
AUTHOR: https://github.com/DevSrSouza

Thanks DevSrSouza for KotlinBukkitAPI
 */


package me.redtea.kotlinbukkitkit.serialization

import me.redtea.kotlinbukkitkit.serialization.serializers.BlockSerializer
import me.redtea.kotlinbukkitkit.serialization.serializers.ChunkSerializer
import me.redtea.kotlinbukkitkit.serialization.serializers.LocationSerializer
import me.redtea.kotlinbukkitkit.serialization.serializers.MaterialDataSerializer
import me.redtea.kotlinbukkitkit.serialization.serializers.MaterialSerializer
import me.redtea.kotlinbukkitkit.serialization.serializers.WorldSerializer
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual

/**
 * All KotlinBukkitAPI already built Serializers for Kotlinx.serialization.
 *
 * Serializers for: Block, Chunk, Location, MaterialData, Material, World.
 */
public fun BukkitSerialModule(): SerializersModule = SerializersModule {
    contextual(BlockSerializer)
    contextual(ChunkSerializer)
    contextual(LocationSerializer)
    contextual(MaterialDataSerializer)
    contextual(MaterialSerializer)
    contextual(WorldSerializer)
}
