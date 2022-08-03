package com.nusratillo.testtask.ui.activities.lights

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import com.nusratillo.testtask.R
import com.nusratillo.testtask.SELECTED_DEVICE_ID
import com.nusratillo.testtask.data.model.Light
import com.nusratillo.testtask.databinding.ActivityLightsBinding
import com.nusratillo.testtask.ui.custom_view.load_state.LoadStateRenderer
import org.koin.androidx.viewmodel.ext.android.viewModel

class LightsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLightsBinding
    private lateinit var renderer: LoadStateRenderer
    private val viewModel: LightsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLightsBinding.inflate(layoutInflater)
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
        setSupportActionBar(binding.lightsToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun observeData() {
        viewModel.loadState.observe(this, renderer::render)
        viewModel.device.observe(this) { observeDevice(it as Light) }
        viewModel.message.observe(this, ::showMessage)
    }

    private fun observeDevice(device: Light) {
        binding.titleTv.text = device.deviceName
        binding.intensitySlider.value = device.intensity.toFloat()
        binding.intensityTv.text = device.intensity.toString()
        binding.lightsModeSwitch.isChecked = device.mode

        binding.intensityTv.isVisible = device.mode
        binding.intensityTitleTv.isVisible = device.mode
        binding.intensitySlider.isVisible = device.mode
    }

    private fun showMessage(messageId: Int) {
        Toast.makeText(this@LightsActivity, getString(messageId), Toast.LENGTH_SHORT).show()
    }

    private fun initListeners() {
        binding.intensitySlider.addOnChangeListener { _, value, fromUser ->
            if (fromUser) viewModel.intensityChanged(value)
        }
        binding.lightsModeSwitch.setOnCheckedChangeListener { _, mode ->
            viewModel.lightModeChanged(mode)
        }
    }

    companion object {
        fun openLightsActivity(context: Context, deviceId: Int) {
            val intent = Intent(context, LightsActivity::class.java)
            intent.putExtra(SELECTED_DEVICE_ID, deviceId)
            context.startActivity(intent)
        }
    }
}