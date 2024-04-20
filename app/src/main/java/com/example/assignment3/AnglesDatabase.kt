package com.example.assignment3

import androidx.room.RoomDatabase
import androidx.room.Database
import android.content.Context
import androidx.room.Room

@Database(entities = [AnglesData::class], version = 1, exportSchema = false)
abstract class AnglesDatabase: RoomDatabase() {
    abstract fun anglesDao(): AnglesDao

    companion object {
        @Volatile
        private var INSTANCE: AnglesDatabase? = null

        fun getInstance(context: Context): AnglesDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): AnglesDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AnglesDatabase::class.java,
                "angles_data"
            ).build()
        }
    }
}