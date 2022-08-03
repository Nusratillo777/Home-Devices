package com.nusratillo.testtask.domain

import com.nusratillo.testtask.data.model.*
import com.nusratillo.testtask.data.preferences.PrefManager
import com.nusratillo.testtask.data.repository.UserDevicesRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class UserDevicesInteractor(
    private val userDevicesRepository: UserDevicesRepository,
    private val userInteractor: UserInteractor,
    private val prefManager: PrefManager
) {

    fun getUserDevicesBy(types: List<DeviceTypeWithNames>): Observable<List<Device>> {
        return if (!prefManager.isDevicesLoaded) {
            userDevicesRepository.getRemoteUserDevices()
                .map {
                    saveUserDevices(it.devices)
                    userInteractor.saveUser(it.user)
                }.flatMap {
                    prefManager.isDevicesLoaded = true
                    userDevicesRepository.getUserDevicesByType(types)
                }
        } else {
            userDevicesRepository.getUserDevicesByType(types)
        }
    }

    fun updateDevice(device: Device): Completable {
        return userDevicesRepository.updateDevice(device)
    }

    fun getDeviceBy(deviceId: Int): Single<Device> {
        return userDevicesRepository.getDeviceBy(deviceId)
    }

    fun deleteDeviceBy(deviceId: Int): Completable {
        return userDevicesRepository.deleteDeviceBy(deviceId)
    }

    private fun saveUserDevices(userDevices: List<Device>) {
        userDevices.forEach { device ->
            userDevicesRepository.saveDevice(device)
        }
    }
}