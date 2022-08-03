package com.nusratillo.testtask.data.model

import androidx.annotation.StringRes
import com.nusratillo.testtask.R

enum class DeviceTypeWithNames(
    val actualName: String,
    @StringRes val userVisibleNameId: Int
) {
    LIGHT("Light", R.string.lights),
    HEATER("Heater", R.string.heaters),
    ROLLER_SHUTTER("RollerShutter", R.string.roller_shutters);

    companion object {
        fun getByActualName(actualName: String): DeviceTypeWithNames {
            return values().filter { it.actualName == actualName }.getOrNull(0) ?: LIGHT
        }
    }
}