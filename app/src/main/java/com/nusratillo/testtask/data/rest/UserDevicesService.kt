package com.nusratillo.testtask.data.rest

import com.nusratillo.testtask.data.model.response.UserDevicesResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface UserDevicesService {
    @GET(ServerUrls.USER_DEVICES)
    fun getUserDevices(): Observable<UserDevicesResponse>
}