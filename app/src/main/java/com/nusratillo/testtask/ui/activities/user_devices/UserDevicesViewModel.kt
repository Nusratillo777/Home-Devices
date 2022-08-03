package com.nusratillo.testtask.ui.activities.user_devices

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nusratillo.testtask.EMPTY_STRING
import com.nusratillo.testtask.R
import com.nusratillo.testtask.data.Languages
import com.nusratillo.testtask.data.model.*
import com.nusratillo.testtask.domain.LanguageInteractor
import com.nusratillo.testtask.domain.UserDevicesInteractor
import com.nusratillo.testtask.ui.custom_view.load_state.ErrorState
import com.nusratillo.testtask.ui.custom_view.load_state.LoadState
import com.nusratillo.testtask.ui.custom_view.load_state.LoadingState
import com.nusratillo.testtask.ui.custom_view.load_state.NoneState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class UserDevicesViewModel(
    private val userDevicesInteractor: UserDevicesInteractor,
    private val languageInteractor: LanguageInteractor
) : ViewModel() {
    private val _loadState = MutableLiveData<LoadState>()
    val loadState: LiveData<LoadState> = _loadState
    private val _devices = MutableLiveData<List<Device>>()
    val devices: LiveData<List<Device>> = _devices
    private val _openActivityAction = MutableLiveData<ActivityAction>()
    val openActivityByType: LiveData<ActivityAction> = _openActivityAction
    private val _showFilters = MutableLiveData<MultiSelectableItems>()
    val showFilters: LiveData<MultiSelectableItems> = _showFilters
    private val _selectedFilter = MutableLiveData<MutableList<DeviceTypeWithNames>>(mutableListOf())
    val selectedFilter: LiveData<MutableList<DeviceTypeWithNames>> = _selectedFilter
    private val _message = MutableLiveData<Int>()
    val message: LiveData<Int> = _message
    private var _showLanguages = MutableLiveData<SelectableItems>()
    val showLanguages: LiveData<SelectableItems> = _showLanguages
    val selectedLanguage: Languages
        get() = languageInteractor.selectedLanguage

    private val compositeDisposable = CompositeDisposable()

    init {
        getUserDevicesBy()
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }

    fun onDeviceClicked(device: Device) {
        when (device) {
            is Light -> _openActivityAction.postValue(
                ActivityAction(
                    DeviceTypeWithNames.LIGHT,
                    device.id
                )
            )
            is Heater -> _openActivityAction.postValue(
                ActivityAction(
                    DeviceTypeWithNames.HEATER,
                    device.id
                )
            )
            is RollerShutter -> _openActivityAction.postValue(
                ActivityAction(
                    DeviceTypeWithNames.ROLLER_SHUTTER,
                    device.id
                )
            )
        }
    }

    fun onFilterClick() {
        val selectedItems = mutableListOf<Boolean>()
        DeviceTypeWithNames.values()
            .forEach { selectedItems.add(selectedFilter.value?.contains(it) ?: false) }

        _showFilters.postValue(
            MultiSelectableItems(
                DeviceTypeWithNames.values().map { it.userVisibleNameId },
                selectedItems,
                DialogState.OPEN
            )
        )
    }

    fun onFilterSelected(selectedFilterPosition: Int, isSelected: Boolean) {
        val deviceType = DeviceTypeWithNames.values()[selectedFilterPosition]
        if (isSelected)
            _selectedFilter.value?.add(deviceType)
        else
            _selectedFilter.value?.remove(deviceType)
    }

    fun applyFilter() {
        _selectedFilter.postValue(_selectedFilter.value)
        _showFilters.value = _showFilters.value?.copy(state = DialogState.CLOSE)
        getUserDevicesBy()
    }

    fun onLanguageClick() {
        _showLanguages.postValue(
            SelectableItems(
                Languages.values().map { it.titleId },
                if (languageInteractor.selectedLanguage == Languages.EN) 0 else 1,
                DialogState.OPEN
            )
        )
    }

    fun openActivityActionHandled() {
        _openActivityAction.value = _openActivityAction.value?.copy(isHandled = true)
    }

    fun onLanguageSelected(selectedPosition: Int) {
        val selectedLanguage = Languages.values()[selectedPosition]
        languageInteractor.selectedLanguage = selectedLanguage
        _showLanguages.postValue(_showLanguages.value?.copy(state = DialogState.CLOSE))
    }

    fun deleteDevice(itemPosition: Int) {
        _loadState.postValue(LoadingState)
        compositeDisposable.add(
            userDevicesInteractor.deleteDeviceBy(devices.value?.get(itemPosition)?.id ?: return)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _loadState.postValue(NoneState)
                        _message.postValue(R.string.device_deleted)
                    },
                    {
                        _loadState.postValue(
                            ErrorState(it.message ?: EMPTY_STRING) {
                                deleteDevice(itemPosition)
                            }
                        )
                    }
                )
        )
    }

    private fun getUserDevicesBy(
        types: List<DeviceTypeWithNames> = _selectedFilter.value ?: emptyList()
    ) {
        _loadState.postValue(LoadingState)
        compositeDisposable.clear()
        compositeDisposable.add(
            userDevicesInteractor.getUserDevicesBy(types)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _loadState.postValue(NoneState)
                        _devices.postValue(it)
                    }, {
                        _loadState.postValue(
                            ErrorState(
                                it.message ?: EMPTY_STRING
                            ) { getUserDevicesBy(types) })
                    }
                )

        )
    }
}

data class ActivityAction(
    val type: DeviceTypeWithNames,
    val deviceId: Int,
    val isHandled: Boolean = false
)