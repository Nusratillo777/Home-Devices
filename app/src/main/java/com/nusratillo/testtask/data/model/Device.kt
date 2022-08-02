package com.nusratillo.testtask.data.model

import com.nusratillo.testtask.EMPTY_STRING
import com.nusratillo.testtask.data.model.entities.UserDeviceEntity

sealed interface Device {
    val id: Int
    val deviceName: String

    fun mapToEntity(): UserDeviceEntity
}

data class Light(
    override val id: Int,
    override val deviceName: String,
    val mode: Boolean,
    val intensity: Int
) : Device {
    override fun mapToEntity() = UserDeviceEntity(
        id = id,
        deviceName = deviceName,
        mode = mode,
        intensity = intensity,
        productType = DeviceTypeWithNames.LIGHT.actualName
    )
}

data class RollerShutter(
    override val id: Int,
    override val deviceName: String,
    val position: Int
) : Device {
    override fun mapToEntity() = UserDeviceEntity(
        id = id,
        deviceName = deviceName,
        position = position,
        productType = DeviceTypeWithNames.ROLLER_SHUTTER.actualName
    )
}

data class Heater(
    override val id: Int,
    override val deviceName: String,
    val mode: Boolean,
    val temperature: Float
) : Device {
    override fun mapToEntity() = UserDeviceEntity(
        id = id,
        deviceName = deviceName,
        mode = mode,
        productType = DeviceTypeWithNames.HEATER.actualName,
        temperature = temperature
    )
}

object None : Device {
    override val id: Int = 0
    override val deviceName: String = EMPTY_STRING

    override fun mapToEntity() = UserDeviceEntity(0)
}