package com.nusratillo.testtask.app.di

import com.nusratillo.testtask.app.db.UserDeviceDatabase
import com.nusratillo.testtask.data.daos.UserDevicesDao
import org.koin.dsl.module

val databaseModule = module {
    single<UserDevicesDao> { get<UserDeviceDatabase>().userDevicesDao() }
}