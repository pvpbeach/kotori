package com.pvpbeach.kotori.maintenance.impl

import com.pvpbeach.kotori.KotoriVelocityData
import com.pvpbeach.kotori.KotoriVelocityPlugin
import com.pvpbeach.kotori.maintenance.MaintenanceProvider
import java.util.*

object PermissionMaintenanceProvider : MaintenanceProvider
{
    override fun isMaintenanceToggled(): Boolean
    {
        return KotoriVelocityData.maintenanceEnabled
    }

    override fun hasMaintenanceAccess(id: UUID): Boolean
    {
        val server = KotoriVelocityPlugin
            .instance
            .server

        val player = server
            .getPlayer(id)
            .orElse(null) ?: return false

        return player.hasPermission("kotori.maintenance.access")
    }
}