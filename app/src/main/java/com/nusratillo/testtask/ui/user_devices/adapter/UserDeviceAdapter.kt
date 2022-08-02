package com.nusratillo.testtask.ui.user_devices.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nusratillo.testtask.R
import com.nusratillo.testtask.data.model.Device
import com.nusratillo.testtask.databinding.ItemUserDeviceBinding
import com.nusratillo.testtask.ui.inflate

class UserDeviceAdapter(
    private val userDevices: MutableList<Device> = mutableListOf(),
    private val userDeviceOnClick: (Device) -> Unit
) : RecyclerView.Adapter<UserDeviceAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.item_user_device, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(userDevices[position])
    }

    override fun getItemCount(): Int = userDevices.size

    fun setItems(newUserDevices: List<Device>) {
        val colorDiffCallback = ColorDiffCallback(userDevices, newUserDevices)
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(colorDiffCallback)

        userDevices.clear()
        userDevices.addAll(newUserDevices)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemUserDeviceBinding.bind(itemView)

        fun bind(device: Device) {
            binding.userDeviceTv.text = device.deviceName
            binding.userDeviceTv.setOnClickListener {
                userDeviceOnClick(device)
            }
        }
    }
}