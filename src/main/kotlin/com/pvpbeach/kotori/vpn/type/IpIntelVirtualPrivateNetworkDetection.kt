package com.pvpbeach.kotori.vpn.type

import com.pvpbeach.kotori.vpn.VirtualPrivateNetworkDetection
import java.net.URL

object IpIntelVirtualPrivateNetworkDetection : VirtualPrivateNetworkDetection
{
    private const val ENDPOINT =
        "http://check.getipintel.net/check.php?ip=<ip>"

    override fun isProxy(ip: String): Boolean
    {
        return isVpn(ip)
    }

    override fun isVpn(ip: String): Boolean
    {
        val endpoint = ENDPOINT
            .replace("<ip>", ip)

        val result = URL(endpoint).readText()
        val value = result.toIntOrNull()
            ?: return true

        return value == 1
    }
}