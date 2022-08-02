package com.nusratillo.testtask.app.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nusratillo.testtask.data.daos.UserDevicesDao
import com.nusratillo.testtask.data.model.entities.UserDeviceEntity

@Database(
    entities = [UserDeviceEntity::class],
    version = 1
)
abstract class UserDeviceDatabase : RoomDatabase() {
    abstract fun userDevicesDao(): UserDevicesDao
}