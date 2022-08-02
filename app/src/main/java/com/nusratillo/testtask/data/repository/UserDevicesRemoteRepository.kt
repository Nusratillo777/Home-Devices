package com.nusratillo.testtask.data.repository

import com.nusratillo.testtask.data.model.UserDevices
import com.nusratillo.testtask.data.model.mapper.modelToResult
import com.nusratillo.testtask.data.rest.UserDevicesService
import io.reactivex.Observable

class UserDevicesRemoteRepository(
    private val userDevicesService: UserDevicesService
) {
    fun getUserDevices(): Observable<UserDevices> {
        return userDevicesService.getUserDevices()
            .map { it.modelToResult() }
    }
}