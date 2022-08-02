package com.pvpbeach.kotori.maintenance

import java.util.UUID

interface MaintenanceProvider
{
    fun isMaintenanceToggled(): Boolean
    fun hasMaintenanceAccess(id: UUID): Boolean
}