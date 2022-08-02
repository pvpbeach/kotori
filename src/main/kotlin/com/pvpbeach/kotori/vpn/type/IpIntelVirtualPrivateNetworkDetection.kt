package com.pvpbeach.kotori.vpn.type

import com.pvpbeach.kotori.vpn.VirtualPrivateNetworkDetection
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object IpIntelVirtualPrivateNetworkDetection : VirtualPrivateNetworkDetection
{
    private const val ENDPOINT =
        "https://check.getipintel.net/check.php?ip=<ip>&contact=daphnekt@proton.me"

    override fun isProxy(ip: String): Boolean
    {
        return false
    }

    override fun isVpn(ip: String): Boolean
    {
        val endpoint = ENDPOINT
            .replace("<ip>", ip)

        val result = URL(endpoint).read()
        val value = result.toIntOrNull()
            ?: return true

        return value == 1
    }

    private fun URL.read(): String
    {
        val connection = this.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.connect()

        val reader = BufferedReader(
            InputStreamReader(connection.inputStream)
        )

        return reader.readLine()
    }
}