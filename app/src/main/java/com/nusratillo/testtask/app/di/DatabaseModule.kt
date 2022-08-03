package com.nusratillo.testtask.app.di

import com.nusratillo.testtask.app.db.UserDeviceDatabase
import org.koin.dsl.module

val databaseModule = module {
    single { get<UserDeviceDatabase>().userDevicesDao() }
}