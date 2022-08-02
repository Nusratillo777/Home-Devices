package com.nusratillo.testtask.data.model.mapper

import com.nusratillo.testtask.EMPTY_STRING
import com.nusratillo.testtask.OFF
import com.nusratillo.testtask.ON
import com.nusratillo.testtask.data.model.*
import com.nusratillo.testtask.data.model.entities.UserDeviceEntity
import com.nusratillo.testtask.data.model.response.DeviceResponse

fun DeviceResponse.mapToModel(): Device {
    return when (DeviceTypeWithNames.getByActualName(productType ?: EMPTY_STRING)) {
        DeviceTypeWithNames.LIGHT -> Light(
            id = id ?: 0,
            deviceName = deviceName ?: EMPTY_STRING,
            mode = mode == ON,
            intensity = intensity ?: 0
        )
        DeviceTypeWithNames.ROLLER_SHUTTER -> RollerShutter(
            id = id ?: 0,
            deviceName = deviceName ?: EMPTY_STRING,
            position = position ?: 0
        )
        DeviceTypeWithNames.HEATER -> Heater(
            id = id ?: 0,
            deviceName = deviceName ?: EMPTY_STRING,
            mode = mode == ON,
            temperature = temperature ?: 0f
        )
        else -> None
    }
}

fun UserDeviceEntity.mapToResponse(): DeviceResponse {
    return DeviceResponse(
        id = id,
        deviceName = deviceName,
        productType = productType,
        intensity = intensity,
        mode = if (mode == true) ON else OFF,
        position = position,
        temperature = temperature
    )
}