package com.pvpbeach.kotori.provider

import java.util.UUID

data class Rank(
    val id: UUID,
    val name: String,
    val weight: Int
)