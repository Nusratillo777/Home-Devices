package com.nusratillo.testtask.ui.activities.shutter_roller

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nusratillo.testtask.R
import com.nusratillo.testtask.SELECTED_DEVICE_ID
import com.nusratillo.testtask.data.model.RollerShutter
import com.nusratillo.testtask.databinding.ActivityRollerShutterBinding
import com.nusratillo.testtask.ui.custom_view.load_state.LoadStateRenderer
import org.koin.androidx.viewmodel.ext.android.viewModel

class RollerShutterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRollerShutterBinding
    private lateinit var renderer: LoadStateRenderer
    private val viewModel: RollerShutterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRollerShutterBinding.inflate(layoutInflater)
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
        setSupportActionBar(binding.rollerShutterToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun observeData() {
        viewModel.loadState.observe(this, renderer::render)
        viewModel.device.observe(this) { observeDevice(it as RollerShutter) }
        viewModel.message.observe(this, ::showMessage)
    }

    private fun observeDevice(device: RollerShutter) {
        binding.titleTv.text = device.deviceName
        binding.positionSlider.value = device.position.toFloat()
        binding.positionTv.text = device.position.toString()
    }

    private fun showMessage(messageId: Int) {
        Toast.makeText(this@RollerShutterActivity, getString(messageId), Toast.LENGTH_SHORT).show()
    }

    private fun initListeners() {
        binding.positionSlider.addOnChangeListener { _, value, fromUser ->
            if (fromUser) viewModel.positionChanged(value)
        }
    }

    companion object {
        fun openRollerShutterActivity(context: Context, deviceId: Int) {
            val intent = Intent(context, RollerShutterActivity::class.java)
            intent.putExtra(SELECTED_DEVICE_ID, deviceId)
            context.startActivity(intent)
        }
    }
}