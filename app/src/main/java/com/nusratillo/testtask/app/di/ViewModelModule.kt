package com.nusratillo.testtask.app.di

import com.nusratillo.testtask.ui.activities.heater.HeaterViewModel
import com.nusratillo.testtask.ui.activities.lights.LightsViewModel
import com.nusratillo.testtask.ui.activities.shutter_roller.RollerShutterViewModel
import com.nusratillo.testtask.ui.activities.user.UserViewModel
import com.nusratillo.testtask.ui.activities.user_devices.UserDevicesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        UserDevicesViewModel(userDevicesUseCase = get(), userUseCase = get())
    }
    viewModel {
        HeaterViewModel(userDevicesUseCase = get())
    }
    viewModel {
        LightsViewModel(userDevicesUseCase = get())
    }
    viewModel {
        RollerShutterViewModel(userDevicesUseCase = get())
    }
    viewModel {
        UserViewModel(userUseCase = get())
    }
}