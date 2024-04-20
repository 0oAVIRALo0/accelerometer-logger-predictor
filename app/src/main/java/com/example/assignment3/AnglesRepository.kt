package com.example.assignment3

class AnglesRepository(private val anglesDao: AnglesDao) {
    suspend fun insertAnglesData(anglesData: AnglesData) {
        anglesDao.insertAnglesData(anglesData)
    }

    suspend fun getAllAnglesData(): List<AnglesData> {
        return anglesDao.getAllAnglesData()
    }
}