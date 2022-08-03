package com.nusratillo.testtask.data.data_sources

import com.nusratillo.testtask.data.daos.UserDevicesDao
import com.nusratillo.testtask.data.model.entities.UserDeviceEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class UserDeviceLocalDataSource(
    private val userDevicesDao: UserDevicesDao
) {

    fun getAllUserDevices(): Observable<List<UserDeviceEntity>> {
        return userDevicesDao.getAllUserDevices()
    }

    fun getUserDevicesBy(types: List<String>): Observable<List<UserDeviceEntity>> {
        return userDevicesDao.getDevicesBy(types)
    }

    fun saveDevice(device: UserDeviceEntity) {
        userDevicesDao.insertUserDevice(device)
    }

    fun getDeviceBy(id: Int): Single<UserDeviceEntity> {
        return userDevicesDao.getBy(id)
    }

    fun updateDevice(device: UserDeviceEntity): Completable {
        return userDevicesDao.updateUserDevice(device)
    }

    fun deleteDeviceBy(id: Int): Completable {
        return userDevicesDao.deleteById(id)
    }
}