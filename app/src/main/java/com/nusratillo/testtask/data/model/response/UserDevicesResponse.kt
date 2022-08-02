package com.nusratillo.testtask.data.model.response

import com.google.gson.annotations.SerializedName

class UserDevicesResponse(
    @SerializedName("devices") val devices: List<DeviceResponse>?,
    @SerializedName("user") val user: UserResponse?
)