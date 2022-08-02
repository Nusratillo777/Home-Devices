package com.nusratillo.testtask.data.model.mapper

import com.nusratillo.testtask.EMPTY_STRING
import com.nusratillo.testtask.data.model.Address
import com.nusratillo.testtask.data.model.response.AddressResponse

fun AddressResponse.mapToModel(): Address {
    return Address(
        city ?: EMPTY_STRING,
        postalCode ?: 0,
        street ?: EMPTY_STRING,
        streetCode ?: EMPTY_STRING,
        country ?: EMPTY_STRING
    )
}