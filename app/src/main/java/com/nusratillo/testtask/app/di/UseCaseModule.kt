package com.nusratillo.testtask.app.di

import com.nusratillo.testtask.domain.UserDevicesUseCase
import com.nusratillo.testtask.domain.UserUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single {
        UserDevicesUseCase(
            userDevicesRemoteRepository = get(),
            userDevicesLocalRepository = get(),
            userUseCase = get(),
            prefManager = get()
        )
    }
    single {
        UserUseCase(prefManager = get())
    }
}