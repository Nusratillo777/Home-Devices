package com.nusratillo.testtask.domain

import com.nusratillo.testtask.data.model.*
import com.nusratillo.testtask.data.preferences.PrefManager
import com.nusratillo.testtask.data.repository.UserDevicesLocalRepository
import com.nusratillo.testtask.data.repository.UserDevicesRemoteRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class UserDevicesUseCase(
    private val userDevicesRemoteRepository: UserDevicesRemoteRepository,
    private val userDevicesLocalRepository: UserDevicesLocalRepository,
    private val userUseCase: UserUseCase,
    private val prefManager: PrefManager
) {

    fun getUserDevicesBy(types: List<DeviceTypeWithNames>): Observable<List<Device>> {
        return if (prefManager.isFirstLoading) {
            prefManager.isFirstLoading = false
            userDevicesRemoteRepository.getUserDevices()
                .map {
                    saveUserDevices(it.devices)
                    userUseCase.saveUser(it.user)
                }.flatMap {
                    userDevicesLocalRepository.getUserDevicesByType(types)
                }
        } else {
            userDevicesLocalRepository.getUserDevicesByType(types)
        }
    }

    fun updateDevice(device: Device): Completable {
        return userDevicesLocalRepository.updateDevice(device)
    }

    fun getDeviceBy(deviceId: Int): Single<Device> {
        return userDevicesLocalRepository.getDeviceBy(deviceId)
    }

    fun deleteDeviceBy(deviceId: Int): Completable {
        return userDevicesLocalRepository.deleteDeviceBy(deviceId)
    }

    private fun saveUserDevices(userDevices: List<Device>) {
        userDevices.forEach { device ->
            userDevicesLocalRepository.saveDevice(device)
        }
    }
}