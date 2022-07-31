package com.pvpbeach.kotori

import com.google.gson.GsonBuilder
import com.google.gson.LongSerializationPolicy
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.nio.file.Path
import java.util.UUID

annotation class Store
object KotoriVelocityData
{
    @Store
    val whitelistedIps = mutableListOf<String>()

    @Store
    val whitelistedIds = mutableListOf<UUID>()

    val gson = GsonBuilder()
        .setPrettyPrinting()
        .setLongSerializationPolicy(LongSerializationPolicy.STRING)
        .create()

    fun save(plugin: KotoriVelocityPlugin)
    {
        val file = getFile(plugin.dataDirectory)
        val writer = FileWriter(file)

        gson.toJson(this, writer)
        writer.close()
    }

    fun load(plugin: KotoriVelocityPlugin)
    {
        val file = getFile(plugin.dataDirectory)
        val reader = FileReader(file)

        val data = gson.fromJson(reader, KotoriVelocityData::class.java)
        val clazz = data::class.java

        for (field in clazz.declaredFields)
        {
            if (!field.isAnnotationPresent(Store::class.java))
            {
                continue
            }

            field[this] = field[data]
        }
    }

    fun getFile(directory: Path): File
    {
        val file = File(
            directory.toFile(), "data.json"
        )

        if (file.parentFile == null || !file.parentFile.exists())
        {
            file.mkdirs()
        }

        if (!file.exists())
        {
            file.createNewFile()
        }

        return file
    }
}