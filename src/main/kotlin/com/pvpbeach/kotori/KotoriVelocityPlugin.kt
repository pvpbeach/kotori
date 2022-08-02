package com.pvpbeach.kotori

import com.pvpbeach.kotori.announce.AnnounceCommand
import com.pvpbeach.kotori.listener.ConnectionListener
import com.pvpbeach.kotori.maintenance.MaintenanceListener
import com.pvpbeach.kotori.maintenance.command.MaintenanceCommand
import com.pvpbeach.kotori.vpn.command.VirtualPrivateNetworkCommand
import com.pvpbeach.kotori.vpn.type.IpIntelVirtualPrivateNetworkDetection
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer
import revxrsal.commands.velocity.VelocityCommandHandler
import java.nio.file.Path
import java.util.logging.Logger
import javax.inject.Inject

class KotoriVelocityPlugin
@Inject constructor(
    val server: ProxyServer,
    val logger: Logger,
    @DataDirectory val dataDirectory: Path
)
{
    companion object
    {
        lateinit var instance: KotoriVelocityPlugin
    }

    val detection = mutableListOf(
        IpIntelVirtualPrivateNetworkDetection
    )

    @Subscribe
    fun onProxyInitialization(event: ProxyInitializeEvent)
    {
        instance = this

        KotoriVelocityData.load(this)

        server
            .eventManager
            .register(this, ConnectionListener)

        server
            .eventManager
            .register(this, MaintenanceListener)

        val manager = VelocityCommandHandler.create(this, server)

        manager.register(VirtualPrivateNetworkCommand)
        manager.register(AnnounceCommand)
        manager.register(MaintenanceCommand)

        manager.setHelpWriter { command, _ ->
            String.format(
                "%s %s - %s",
                command.path.toRealString(),
                command.usage,
                command.description
            )
        }
    }

    @Subscribe
    fun onProxyShutdown(event: ProxyShutdownEvent)
    {
        KotoriVelocityData.save(this)
    }
}