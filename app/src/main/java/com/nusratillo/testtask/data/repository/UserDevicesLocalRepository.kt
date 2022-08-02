package com.nusratillo.testtask.data.repository

import android.util.Log
import com.nusratillo.testtask.data.daos.UserDevicesDao
import com.nusratillo.testtask.data.model.Device
import com.nusratillo.testtask.data.model.DeviceTypeWithNames
import com.nusratillo.testtask.data.model.mapper.mapToModel
import com.nusratillo.testtask.data.model.mapper.mapToResponse
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class UserDevicesLocalRepository(
    private val userDevicesDao: UserDevicesDao
) {

    fun getUserDevicesByType(types: List<DeviceTypeWithNames>): Observable<List<Device>> {
        Log.i("USE CASE --> ", types.toString())
        val devices = if (types.isEmpty()) userDevicesDao.getAllUserDevices()
        else userDevicesDao.getDevicesBy(types.map { it.actualName })

        return devices.map { deviceEntities ->
            deviceEntities.map { deviceEntity ->
                deviceEntity.mapToResponse().mapToModel()
            }
        }
    }

    fun saveDevice(device: Device) {
        userDevicesDao.insertUserDevice(device.mapToEntity())
    }

    fun getDeviceBy(id: Int): Single<Device> {
        return userDevicesDao.getBy(id).map {
            it.mapToResponse().mapToModel()
        }
    }

    fun updateDevice(device: Device): Completable {
        return userDevicesDao.updateUserDevice(device.mapToEntity())
    }

    fun deleteDeviceBy(id: Int): Completable {
        return userDevicesDao.deleteById(id)
    }
}