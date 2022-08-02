package com.nusratillo.testtask.data.model.mapper

import com.nusratillo.testtask.data.model.User
import com.nusratillo.testtask.data.model.UserDevices
import com.nusratillo.testtask.data.model.response.UserDevicesResponse

fun UserDevicesResponse.modelToResult(): UserDevices{
    return UserDevices(
        devices = devices?.map { it.mapToModel() } ?: emptyList(),
        user = user?.mapToResult() ?: User()
    )
}