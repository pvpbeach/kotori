package com.pvpbeach.kotori.announce

import com.pvpbeach.kotori.KotoriVelocityPlugin
import net.kyori.text.serializer.legacy.LegacyComponentSerializer
import revxrsal.commands.annotation.Command
import revxrsal.commands.velocity.annotation.CommandPermission

object AnnounceCommand
{
    @Command("announce", "alert")
    @CommandPermission("kotori.command.announce")
    fun announce(message: String)
    {
        KotoriVelocityPlugin
            .instance
            .server
            .broadcast(
                LegacyComponentSerializer
                    .legacy()
                    .deserialize(message, '&')
            )
    }
}