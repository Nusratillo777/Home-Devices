package com.nusratillo.testtask.data.model.response

import com.google.gson.annotations.SerializedName

class UserResponse(
    @SerializedName("firstName") val firstName: String?,
    @SerializedName("lastName") val lastName: String?,
    @SerializedName("address") val address: AddressResponse?,
    @SerializedName("birthDate") val birthDate: Long?
)