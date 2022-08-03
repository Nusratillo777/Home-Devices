package com.nusratillo.testtask.app.di

import com.nusratillo.testtask.data.data_sources.UserDeviceLocalDataSource
import com.nusratillo.testtask.data.data_sources.UserDeviceRemoteDataSource
import com.nusratillo.testtask.data.repository.UserDevicesRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory {
        UserDevicesRepository(
            userDeviceRemoteDataSource = get(),
            userDeviceLocalDataSource = get()
        )
    }
    single {
        UserDeviceRemoteDataSource(
            userDevicesService = get()
        )
    }
    single {
        UserDeviceLocalDataSource(
            userDevicesDao = get()
        )
    }
}