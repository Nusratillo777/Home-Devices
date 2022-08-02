package com.nusratillo.testtask.data.daos

import androidx.room.*
import com.nusratillo.testtask.data.model.entities.UserDeviceEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface UserDevicesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUserDevice(userDevices: UserDeviceEntity)

    @Query("SELECT * FROM user_devices")
    fun getAllUserDevices(): Observable<List<UserDeviceEntity>>

    @Query("SELECT * FROM user_devices WHERE id = :id")
    fun getBy(id: Int): Single<UserDeviceEntity>

    @Query("SELECT * FROM user_devices WHERE productType IN (:productTypes)")
    fun getDevicesBy(productTypes: List<String>): Observable<List<UserDeviceEntity>>

    @Update
    fun updateUserDevice(userDevice: UserDeviceEntity): Completable

    @Query("DELETE FROM user_devices WHERE id = :id")
    fun deleteById(id: Int): Completable
}