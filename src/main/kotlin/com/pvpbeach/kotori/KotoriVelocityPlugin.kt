package com.pvpbeach.kotori

import com.pvpbeach.kotori.listener.ConnectionListener
import com.pvpbeach.kotori.vpn.type.IpIntelVirtualPrivateNetworkDetection
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer
import java.nio.file.Path
import java.util.logging.Logger
import javax.inject.Inject

class KotoriVelocityPlugin
@Inject constructor(val server: ProxyServer, val logger: Logger, @DataDirectory val dataDirectory: Path)
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
    }

    @Subscribe
    fun onProxyShutdown(event: ProxyShutdownEvent)
    {
        KotoriVelocityData.save(this)
    }
}