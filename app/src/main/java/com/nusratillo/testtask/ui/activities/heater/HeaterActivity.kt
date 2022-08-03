package com.nusratillo.testtask.ui.activities.heater

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.nusratillo.testtask.R
import com.nusratillo.testtask.SELECTED_DEVICE_ID
import com.nusratillo.testtask.data.model.Heater
import com.nusratillo.testtask.databinding.ActivityHeaterBinding
import com.nusratillo.testtask.ui.custom_view.load_state.LoadStateRenderer
import org.koin.androidx.viewmodel.ext.android.viewModel

class HeaterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHeaterBinding
    private lateinit var renderer: LoadStateRenderer
    private val viewModel: HeaterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeaterBinding.inflate(layoutInflater)
        renderer = LoadStateRenderer(binding.placeHolder)
        setContentView(binding.root)

        val deviceId: Int = intent.getIntExtra(SELECTED_DEVICE_ID, 0)
        viewModel.setDeviceId(deviceId)

        initToolbar()
        observeData()
        initListeners()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_device, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.update_menu -> {
                viewModel.updateDevice()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initToolbar() {
        setSupportActionBar(binding.heaterToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun observeData() {
        viewModel.loadState.observe(this, renderer::render)
        viewModel.device.observe(this) { observeDevice(it as Heater) }
        viewModel.message.observe(this, ::showMessage)
    }

    private fun observeDevice(device: Heater) {
        binding.titleTv.text = device.deviceName
        binding.temperatureTv.text = device.temperature.toString()
        binding.temperatureSlider.value = device.temperature
        binding.heaterModeSwitch.isChecked = device.mode

        binding.temperatureTitleTv.isVisible = device.mode
        binding.temperatureTv.isVisible = device.mode
        binding.temperatureSlider.isVisible = device.mode
    }

    private fun showMessage(messageId: Int) {
        Toast.makeText(this@HeaterActivity, getString(messageId), Toast.LENGTH_SHORT).show()
    }

    private fun initListeners() {
        binding.temperatureSlider.addOnChangeListener { _, value, fromUser ->
            if (fromUser) viewModel.temperatureChanged(value)
        }
        binding.heaterModeSwitch.setOnCheckedChangeListener { _, mode ->
            viewModel.heaterModeChanged(mode)
        }
    }

    companion object {
        fun openHeaterActivity(context: Context, deviceId: Int) {
            val intent = Intent(context, HeaterActivity::class.java)
            intent.putExtra(SELECTED_DEVICE_ID, deviceId)
            context.startActivity(intent)
        }
    }
}