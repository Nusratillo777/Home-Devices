package com.nusratillo.testtask.data.repository

import com.nusratillo.testtask.data.data_sources.UserDeviceLocalDataSource
import com.nusratillo.testtask.data.data_sources.UserDeviceRemoteDataSource
import com.nusratillo.testtask.data.model.Device
import com.nusratillo.testtask.data.model.DeviceTypeWithNames
import com.nusratillo.testtask.data.model.UserDevices
import com.nusratillo.testtask.data.model.mapper.mapToModel
import com.nusratillo.testtask.data.model.mapper.mapToResponse
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class UserDevicesRepository(
    private val userDeviceRemoteDataSource: UserDeviceRemoteDataSource,
    private val userDeviceLocalDataSource: UserDeviceLocalDataSource
) {

    fun getRemoteUserDevices(): Observable<UserDevices> {
        return userDeviceRemoteDataSource.getUserDevices()
    }

    fun getUserDevicesByType(types: List<DeviceTypeWithNames>): Observable<List<Device>> {
        val devices = if (types.isEmpty()) userDeviceLocalDataSource.getAllUserDevices()
        else userDeviceLocalDataSource.getUserDevicesBy(types.map { it.actualName })

        return devices.map { deviceEntities ->
            deviceEntities.map { deviceEntity ->
                deviceEntity.mapToResponse().mapToModel()
            }
        }
    }

    fun saveDevice(device: Device) {
        userDeviceLocalDataSource.saveDevice(device.mapToEntity())
    }

    fun getDeviceBy(id: Int): Single<Device> {
        return userDeviceLocalDataSource.getDeviceBy(id)
            .map { it.mapToResponse().mapToModel() }
    }

    fun updateDevice(device: Device): Completable {
        return userDeviceLocalDataSource.updateDevice(device.mapToEntity())
    }

    fun deleteDeviceBy(id: Int): Completable {
        return userDeviceLocalDataSource.deleteDeviceBy(id)
    }
}