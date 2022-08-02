package com.nusratillo.testtask.data.model.response

import com.google.gson.annotations.SerializedName

class AddressResponse(
    @SerializedName("city") val city: String?,
    @SerializedName("postalCode") val postalCode: Int?,
    @SerializedName("street") val street: String?,
    @SerializedName("streetCode") val streetCode: String?,
    @SerializedName("country") val country: String?
)