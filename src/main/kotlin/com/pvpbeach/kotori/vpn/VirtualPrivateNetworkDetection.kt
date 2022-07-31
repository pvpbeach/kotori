package com.pvpbeach.kotori.vpn

interface VirtualPrivateNetworkDetection
{
    fun isProxy(ip: String): Boolean
    fun isVpn(ip: String): Boolean
}