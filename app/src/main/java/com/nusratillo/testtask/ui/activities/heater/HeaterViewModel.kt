package com.nusratillo.testtask.ui.activities.heater

import com.nusratillo.testtask.data.model.Heater
import com.nusratillo.testtask.domain.UserDevicesInteractor
import com.nusratillo.testtask.ui.DeviceBaseViewModel

class HeaterViewModel(
    userDevicesInteractor: UserDevicesInteractor,
) : DeviceBaseViewModel(userDevicesInteractor) {

    fun setDeviceId(deviceId: Int) {
        getDeviceById(deviceId)
    }

    fun heaterModeChanged(mode: Boolean) {
        val newDevice = (device.value as? Heater)?.copy(mode = mode) ?: return
        _device.postValue(newDevice)
    }

    fun temperatureChanged(newTemperature: Float) {
        val newDevice = (device.value as? Heater)?.copy(temperature = newTemperature) ?: return
        _device.postValue(newDevice)
    }
}