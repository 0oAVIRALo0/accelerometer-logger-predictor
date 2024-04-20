package com.example.assignment3

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AnglesDao {
    @Insert
    suspend fun insertAnglesData(anglesData: AnglesData)

    @Query("Select * from angles_data")
    suspend fun getAllAnglesData(): List<AnglesData>
}