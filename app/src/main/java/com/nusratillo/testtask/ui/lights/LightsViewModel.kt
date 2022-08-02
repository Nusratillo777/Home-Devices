package com.nusratillo.testtask.ui.lights

import com.nusratillo.testtask.data.model.Light
import com.nusratillo.testtask.domain.UserDevicesUseCase
import com.nusratillo.testtask.ui.DeviceBaseViewModel

class LightsViewModel(
    userDevicesUseCase: UserDevicesUseCase
) : DeviceBaseViewModel(userDevicesUseCase) {

    override fun onCleared() {
        super.onCleared()
    }

    fun setDeviceId(deviceId: Int) {
        getDeviceById(deviceId)
    }

    fun lightModeChanged(mode: Boolean) {
        val newDevice = (device.value as? Light)?.copy(mode = mode)
        _device.postValue(newDevice)
    }

    fun intensityChanged(newIntensity: Float) {
        val newDevice = (device.value as? Light)?.copy(intensity = newIntensity.toInt())
        _device.postValue(newDevice)
    }
}