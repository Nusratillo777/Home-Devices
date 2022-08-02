package com.nusratillo.testtask.data.model

import com.nusratillo.testtask.EMPTY_STRING

data class Address(
    var city: String = EMPTY_STRING,
    var postalCode: Int = 0,
    var street: String = EMPTY_STRING,
    var streetCode: String = EMPTY_STRING,
    var country: String = EMPTY_STRING
)