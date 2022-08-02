package com.nusratillo.testtask.data.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_devices")
class UserDeviceEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "deviceName") val deviceName: String? = null,
    @ColumnInfo(name = "intensity") val intensity: Int? = null,
    @ColumnInfo(name = "mode") val mode: Boolean? = null,
    @ColumnInfo(name = "productType") val productType: String? = null,
    @ColumnInfo(name = "position") val position: Int? = null,
    @ColumnInfo(name = "temperature") val temperature: Float? = null
)