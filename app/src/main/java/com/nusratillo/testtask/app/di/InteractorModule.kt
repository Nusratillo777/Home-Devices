package com.nusratillo.testtask.app.di

import com.nusratillo.testtask.domain.LanguageInteractor
import com.nusratillo.testtask.domain.UserDevicesInteractor
import com.nusratillo.testtask.domain.UserInteractor
import org.koin.dsl.module

val useCaseModule = module {
    single {
        UserDevicesInteractor(
            userDevicesRepository = get(),
            userInteractor = get(),
            prefManager = get()
        )
    }
    single {
        UserInteractor(prefManager = get())
    }
    single {
        LanguageInteractor(prefManager = get())
    }
}