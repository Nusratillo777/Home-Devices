package com.nusratillo.testtask.app.di

import com.nusratillo.testtask.data.repository.UserDevicesLocalRepository
import com.nusratillo.testtask.data.repository.UserDevicesRemoteRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory {
        UserDevicesRemoteRepository(userDevicesService = get())
    }
    factory {
        UserDevicesLocalRepository(
            userDevicesDao = get()
        )
    }
}