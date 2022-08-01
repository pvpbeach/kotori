package com.pvpbeach.kotori

import com.google.gson.GsonBuilder
import com.google.gson.LongSerializationPolicy
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.nio.file.Path
import java.util.UUID

annotation class Store
annotation class Named(val value: String)

data class KotoriVelocityDataStructure(
    @Named(":whitelistedIps")
    val whitelistedIps: MutableList<String>,

    @Named(":whitelistedIds")
    val whitelistedIds: MutableList<UUID>
)

object KotoriVelocityData
{
    @Store
    var whitelistedIps = mutableListOf<String>()

    @Store
    var whitelistedIds = mutableListOf<UUID>()

    private val gson = GsonBuilder()
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

        val data = gson.fromJson(reader, KotoriVelocityDataStructure::class.java)
        val clazz = data::class.java

        for (field in clazz.declaredFields)
        {
            val name = if (field.isAnnotationPresent(Named::class.java))
            {
                field.getAnnotation(Named::class.java).value
            } else
            {
                field.name
            }.replace(":", "")

            val unaryField =
                KotoriVelocityData::class
                    .java
                    .getDeclaredField(name) ?: continue

            field.isAccessible = true
            unaryField.isAccessible = true

            val value = field[data] ?: continue

            unaryField[this] =
                value
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