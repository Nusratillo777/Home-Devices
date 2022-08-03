package com.nusratillo.testtask.ui.activities.user_devices.adapter

import androidx.recyclerview.widget.DiffUtil
import com.nusratillo.testtask.data.model.Device

class ColorDiffCallback(
    private val oldColors: List<Device>,
    private val newColors: List<Device>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldColors.size
    }

    override fun getNewListSize(): Int {
        return newColors.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldColors[oldItemPosition] == newColors[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldColors[oldItemPosition].id == newColors[newItemPosition].id
    }
}