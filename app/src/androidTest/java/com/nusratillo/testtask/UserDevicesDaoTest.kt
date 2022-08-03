package com.nusratillo.testtask

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nusratillo.testtask.app.db.UserDeviceDatabase
import com.nusratillo.testtask.data.daos.UserDevicesDao
import com.nusratillo.testtask.data.model.entities.UserDeviceEntity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class UserDevicesDaoTest {
    private lateinit var userDevicesDao: UserDevicesDao
    private lateinit var db: UserDeviceDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, UserDeviceDatabase::class.java
        ).build()
        userDevicesDao = db.userDevicesDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeDeviceAndReadText() {
        val userDevice = UserDeviceEntity(id=1, deviceName = "Test Device")

        userDevicesDao.insertUserDevice(userDevice)
        val userDeviceEntity = userDevicesDao.getBy(1).blockingGet()
        assert(userDeviceEntity.deviceName == "Test Device")
    }

    @Test
    @Throws(Exception::class)
    fun filterProductTest() {
        val userDevice = UserDeviceEntity(
            id=1,
            deviceName = "Test Device",
            productType = "Light"
        )

        userDevicesDao.insertUserDevice(userDevice)
        val userDeviceEntities = userDevicesDao.getDevicesBy(listOf("Light")).blockingFirst()
        assert(userDeviceEntities.size == 1)
    }
}