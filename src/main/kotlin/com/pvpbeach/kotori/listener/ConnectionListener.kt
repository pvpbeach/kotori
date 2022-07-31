package com.pvpbeach.kotori.listener

import com.pvpbeach.kotori.KotoriVelocityData
import com.pvpbeach.kotori.KotoriVelocityPlugin
import com.velocitypowered.api.event.ResultedEvent
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.LoginEvent
import net.kyori.text.TextComponent
import net.kyori.text.format.TextColor

object ConnectionListener
{
    @Subscribe
    fun connect(event: LoginEvent)
    {
        val connection = event
            .player
            .remoteAddress
            .hostName

        if (KotoriVelocityData.whitelistedIps.contains(connection)
            || KotoriVelocityData
                .whitelistedIds
                .contains(event.player.uniqueId)
        )
        {
            return
        }

        val detected = KotoriVelocityPlugin
            .instance
            .detection
            .any {
                it.isVpn(connection) || it.isProxy(connection)
            }

        if (detected)
        {
            event.result = ResultedEvent.ComponentResult.denied(
                TextComponent.of("No proxies or VPNs are permitted on the server!", TextColor.RED)
            )
        }
    }
}