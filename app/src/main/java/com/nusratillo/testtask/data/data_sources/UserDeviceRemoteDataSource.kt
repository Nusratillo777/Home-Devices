package com.nusratillo.testtask.data.data_sources

import com.nusratillo.testtask.data.model.UserDevices
import com.nusratillo.testtask.data.model.mapper.modelToResult
import com.nusratillo.testtask.data.rest.UserDevicesService
import io.reactivex.Observable

class UserDeviceRemoteDataSource(
    private val userDevicesService: UserDevicesService
) {
    fun getUserDevices(): Observable<UserDevices> {
        return userDevicesService.getUserDevices()
            .map { it.modelToResult() }
    }
}