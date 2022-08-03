package com.nusratillo.testtask.ui.activities.shutter_roller

import com.nusratillo.testtask.data.model.RollerShutter
import com.nusratillo.testtask.domain.UserDevicesUseCase
import com.nusratillo.testtask.ui.DeviceBaseViewModel

class RollerShutterViewModel(
    private val userDevicesUseCase: UserDevicesUseCase
) : DeviceBaseViewModel(userDevicesUseCase) {

    override fun onCleared() {
        super.onCleared()
    }

    fun setDeviceId(deviceId: Int) {
        getDeviceById(deviceId)
    }

    fun positionChanged(position: Float) {
        val newDevice = (device.value as? RollerShutter)?.copy(position = position.toInt()) ?: return
        _device.postValue(newDevice)
    }
}