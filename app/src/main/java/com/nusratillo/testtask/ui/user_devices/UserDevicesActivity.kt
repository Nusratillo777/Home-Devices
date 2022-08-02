package com.nusratillo.testtask.ui.user_devices

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ItemTouchHelper
import com.nusratillo.testtask.R
import com.nusratillo.testtask.data.Languages
import com.nusratillo.testtask.data.model.DeviceTypeWithNames
import com.nusratillo.testtask.databinding.ActivityUserDevicesBinding
import com.nusratillo.testtask.ui.changeLocales
import com.nusratillo.testtask.ui.custom_view.load_state.*
import com.nusratillo.testtask.ui.heater.HeaterActivity
import com.nusratillo.testtask.ui.lights.LightsActivity
import com.nusratillo.testtask.ui.shutter_roller.RollerShutterActivity
import com.nusratillo.testtask.ui.user.UserActivity
import com.nusratillo.testtask.ui.user_devices.adapter.SwipeToRightCallback
import com.nusratillo.testtask.ui.user_devices.adapter.UserDeviceAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserDevicesActivity : AppCompatActivity() {
    private val userDevicesViewModel: UserDevicesViewModel by viewModel()
    private lateinit var binding: ActivityUserDevicesBinding
    private lateinit var adapter: UserDeviceAdapter
    private lateinit var loadStateRenderer: LoadStateRenderer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDevicesBinding.inflate(layoutInflater)
        loadStateRenderer = LoadStateRenderer(binding.placeHolder)
        changeLocales(userDevicesViewModel.selectedLanguage.name.lowercase())
        setContentView(binding.root)

        initViews()
        observeData()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_user -> {
                UserActivity.openUserActivity(this@UserDevicesActivity)
                true
            }
            R.id.menu_language -> {
                userDevicesViewModel.onLanguageClick()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initViews() {
        setSupportActionBar(binding.toolbar)
        adapter = UserDeviceAdapter { device ->
            userDevicesViewModel.onDeviceClicked(device)
        }
        binding.userDevicesRv.adapter = adapter
        binding.filterBtn.setOnClickListener {
            userDevicesViewModel.onFilterClick()
        }
        ItemTouchHelper(
            SwipeToRightCallback {
                userDevicesViewModel.deleteDevice(it)
            }
        ).attachToRecyclerView(binding.userDevicesRv)
    }

    private fun observeData() {
        userDevicesViewModel.loadState.observe(this, loadStateRenderer::render)
        userDevicesViewModel.devices.observe(this) {
            binding.emptyInfoTv.isVisible = it.isEmpty()
            adapter.setItems(it)
        }
        userDevicesViewModel.openActivityByType.observe(this, ::observeActivityAction)
        userDevicesViewModel.showFilters.observe(this, ::showMultipleItemSelectorDialog)
        userDevicesViewModel.selectedFilter.observe(this) {
            binding.filterBtn.isSelected = it.isNotEmpty()
        }
        userDevicesViewModel.message.observe(this, ::showMessage)
        userDevicesViewModel.showLanguages.observe(this, ::showLanguageSelectorDialog)
    }

    private fun observeActivityAction(action: ActivityAction) {
        userDevicesViewModel.openActivityActionHandled()
        when (action.type) {
            DeviceTypeWithNames.HEATER -> HeaterActivity.openHeaterActivity(
                this@UserDevicesActivity,
                action.deviceId
            )
            DeviceTypeWithNames.LIGHT -> LightsActivity.openLightsActivity(
                this@UserDevicesActivity,
                action.deviceId
            )
            DeviceTypeWithNames.ROLLER_SHUTTER -> RollerShutterActivity.openRollerShutterActivity(
                this@UserDevicesActivity,
                action.deviceId
            )
        }
    }

    private fun showLanguageSelectorDialog(selectableItems: SelectableItems) {
        if (selectableItems.state == DialogState.OPEN) {
            showSingleItemSelectorDialog(selectableItems) {
                userDevicesViewModel.onLanguageSelected(it)
                changeLanguage(userDevicesViewModel.selectedLanguage)
            }
        }
    }

    private fun showSingleItemSelectorDialog(
        selectableItems: SelectableItems,
        onSelectedAction: (Int) -> Unit
    ) {
        val items: Array<String> = selectableItems.items.map { getString(it) }.toTypedArray()
        AlertDialog.Builder(this@UserDevicesActivity)
            .setTitle(R.string.select_device_type)
            .setSingleChoiceItems(
                items,
                selectableItems.selectedItemPosition
            ) { dialog, position ->
                onSelectedAction(position)
                dialog.dismiss()
            }.show()
    }

    private fun showMultipleItemSelectorDialog(selectableItems: MultiSelectableItems) {
        val items: Array<String> = selectableItems.items.map { getString(it) }.toTypedArray()
        AlertDialog.Builder(this@UserDevicesActivity)
            .setTitle(R.string.select_device_type)
            .setPositiveButton(R.string.ok) { _, _ ->
                userDevicesViewModel.applyFilter()
            }.setMultiChoiceItems(
                items,
                selectableItems.selectedItems.toBooleanArray()
            ) { _, position, isSelected ->
                userDevicesViewModel.onFilterSelected(position, isSelected)
            }.show()
    }

    private fun showMessage(messageId: Int) {
        Toast.makeText(this@UserDevicesActivity, getString(messageId), Toast.LENGTH_SHORT).show()
    }

    private fun changeLanguage(languages: Languages) {
        changeLocales(languages.name.lowercase())
        recreate()
        this.overridePendingTransition(0, 0)
    }
}