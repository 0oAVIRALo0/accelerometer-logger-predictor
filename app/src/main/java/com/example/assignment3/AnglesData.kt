package com.example.assignment3

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "angles_data")
data class AnglesData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val time: String,
    val x: Double,
    val y: Double,
    val z: Double
)
