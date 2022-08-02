package com.nusratillo.testtask.ui.heater

import com.nusratillo.testtask.data.model.Heater
import com.nusratillo.testtask.domain.UserDevicesUseCase
import com.nusratillo.testtask.ui.DeviceBaseViewModel

class HeaterViewModel(
    userDevicesUseCase: UserDevicesUseCase
) : DeviceBaseViewModel(userDevicesUseCase) {

    override fun onCleared() {
        super.onCleared()
    }

    fun setDeviceId(deviceId: Int) {
        getDeviceById(deviceId)
    }

    fun heaterModeChanged(mode: Boolean) {
        val newDevice = (device.value as? Heater)?.copy(mode = mode)
        _device.postValue(newDevice)
    }

    fun temperatureChanged(newTemperature: Float) {
        val newDevice = (device.value as? Heater)?.copy(temperature = newTemperature)
        _device.postValue(newDevice)
    }
}