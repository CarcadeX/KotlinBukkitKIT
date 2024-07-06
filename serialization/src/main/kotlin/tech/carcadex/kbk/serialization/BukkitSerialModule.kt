/*
ORIGINAL PACKAGE: package br.com.devsrsouza.kotlinbukkitapi.serialization
ORIGINAL REPOSITORY: https://github.com/DevSrSouza/KotlinBukkitAPI
AUTHOR: https://github.com/DevSrSouza

Thanks DevSrSouza for KotlinBukkitAPI
 */


package tech.carcadex.kbk.serialization

import tech.carcadex.kbk.serialization.serializers.BlockSerializer
import tech.carcadex.kbk.serialization.serializers.ChunkSerializer
import tech.carcadex.kbk.serialization.serializers.LocationSerializer
import tech.carcadex.kbk.serialization.serializers.MaterialDataSerializer
import tech.carcadex.kbk.serialization.serializers.MaterialSerializer
import tech.carcadex.kbk.serialization.serializers.WorldSerializer
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
