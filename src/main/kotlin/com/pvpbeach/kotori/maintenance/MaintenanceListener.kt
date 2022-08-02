package com.pvpbeach.kotori.maintenance

import com.pvpbeach.kotori.KotoriVelocityData
import com.velocitypowered.api.event.ResultedEvent
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.LoginEvent
import com.velocitypowered.api.event.proxy.ProxyPingEvent
import com.velocitypowered.api.proxy.server.ServerPing
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer

object MaintenanceListener
{
    @Subscribe
    fun connect(event: LoginEvent)
    {
        val provider = MaintenanceContainer.provider
        val player = event.player

        if (provider.isMaintenanceToggled()
            && !provider.hasMaintenanceAccess(player.uniqueId)
        )
        {
            event.result = ResultedEvent.ComponentResult.denied(
                LegacyComponentSerializer
                    .legacy('&')
                    .deserialize(
                        KotoriVelocityData
                            .maintenanceKickMessage
                            .joinToString("\n") {
                                it
                            }
                    )
            )
        }
    }

    @Subscribe
    fun ping(event: ProxyPingEvent)
    {
        val ping = event.ping

        event.ping = ServerPing(
            ServerPing.Version(-1, "Maintenance"),
            ping.players.orElse(null),
            ping.descriptionComponent,
            ping.favicon.orElse(null),
        )
    }
}