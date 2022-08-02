package com.pvpbeach.kotori.provider

import java.util.UUID

interface RankProvider
{
    fun getRankOfPlayer(id: UUID): Rank
}