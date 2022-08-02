package com.nusratillo.testtask.data.model.mapper

import com.nusratillo.testtask.EMPTY_STRING
import com.nusratillo.testtask.data.model.Address
import com.nusratillo.testtask.data.model.User
import com.nusratillo.testtask.data.model.response.UserResponse

fun UserResponse.mapToResult(): User {
    return User(
        firstName = firstName ?: EMPTY_STRING,
        lastName = lastName ?: EMPTY_STRING,
        address = address?.mapToModel() ?: Address(),
        birthDate = birthDate ?: 0
    )
}