package com.nusratillo.testtask.data.model.response

import com.google.gson.annotations.SerializedName

class DeviceResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("deviceName") val deviceName: String?,
    @SerializedName("intensity") val intensity: Int?,
    @SerializedName("mode") val mode: String?,
    @SerializedName("productType") val productType: String?,
    @SerializedName("position") val position: Int?,
    @SerializedName("temperature") val temperature: Float?
)