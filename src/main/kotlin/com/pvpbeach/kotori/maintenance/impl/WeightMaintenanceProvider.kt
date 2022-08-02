package com.pvpbeach.kotori.maintenance.impl

import com.pvpbeach.kotori.KotoriVelocityData
import com.pvpbeach.kotori.maintenance.MaintenanceProvider
import com.pvpbeach.kotori.provider.RankContainer
import java.util.*

object WeightMaintenanceProvider : MaintenanceProvider
{
    var weight = 0

    override fun isMaintenanceToggled(): Boolean
    {
        return KotoriVelocityData.maintenanceEnabled
    }

    override fun hasMaintenanceAccess(id: UUID): Boolean
    {
        val rank = RankContainer
            .provider
            .getRankOfPlayer(id)

        return rank.weight > weight
    }
}