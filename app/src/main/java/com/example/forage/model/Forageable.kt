package com.example.forage.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Forageable entity to be stored in the forageable_database.
 */

@Entity
data class Forageable(
    @PrimaryKey val id: Long = 0,
    val name: String,
    val address: String,
    val inSeason: Boolean,
    val notes: String?
)
