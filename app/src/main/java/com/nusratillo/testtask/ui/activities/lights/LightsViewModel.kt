package com.nusratillo.testtask.ui.activities.lights

import com.nusratillo.testtask.data.model.Light
import com.nusratillo.testtask.domain.UserDevicesInteractor
import com.nusratillo.testtask.ui.DeviceBaseViewModel

class LightsViewModel(
    userDevicesInteractor: UserDevicesInteractor,
) : DeviceBaseViewModel(userDevicesInteractor) {

    fun setDeviceId(deviceId: Int) {
        getDeviceById(deviceId)
    }

    fun lightModeChanged(mode: Boolean) {
        val newDevice = (device.value as? Light)?.copy(mode = mode) ?: return
        _device.postValue(newDevice)
    }

    fun intensityChanged(newIntensity: Float) {
        val newDevice = (device.value as? Light)?.copy(intensity = newIntensity.toInt()) ?: return
        _device.postValue(newDevice)
    }
}