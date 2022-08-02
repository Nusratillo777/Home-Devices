package com.nusratillo.testtask.data.model

import com.nusratillo.testtask.EMPTY_STRING

data class User(
    var firstName: String = EMPTY_STRING,
    var lastName: String = EMPTY_STRING,
    val address: Address = Address(),
    var birthDate: Long = 0
)