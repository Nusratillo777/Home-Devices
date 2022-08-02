package com.nusratillo.testtask.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nusratillo.testtask.EMPTY_STRING
import com.nusratillo.testtask.R
import com.nusratillo.testtask.data.model.Device
import com.nusratillo.testtask.data.model.RollerShutter
import com.nusratillo.testtask.domain.UserDevicesUseCase
import com.nusratillo.testtask.ui.custom_view.load_state.ErrorState
import com.nusratillo.testtask.ui.custom_view.load_state.LoadState
import com.nusratillo.testtask.ui.custom_view.load_state.LoadingState
import com.nusratillo.testtask.ui.custom_view.load_state.NoneState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

abstract class DeviceBaseViewModel(
    private val userDevicesUseCase: UserDevicesUseCase
) : ViewModel() {
    protected val _loadState = MutableLiveData<LoadState>()
    val loadState: LiveData<LoadState> = _loadState
    protected val _device = MutableLiveData<Device>()
    val device: LiveData<Device> = _device
    protected val _message = MutableLiveData<Int>()
    val message: LiveData<Int> = _message

    protected val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.clear()
    }

    fun updateDevice() {
        _loadState.postValue(LoadingState)
        compositeDisposable.add(
            userDevicesUseCase.updateDevice(device.value ?: return)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _loadState.postValue(NoneState)
                        _message.postValue(R.string.device_updated_successfully)
                    },
                    {
                        _loadState.postValue(
                            ErrorState(it.message ?: EMPTY_STRING) {
                                updateDevice()
                            }
                        )
                    }
                )
        )
    }

    protected fun getDeviceById(deviceId: Int) {
        _loadState.postValue(LoadingState)
        compositeDisposable.add(
            userDevicesUseCase.getDeviceBy(deviceId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _loadState.postValue(NoneState)
                        _device.postValue(it)
                    },
                    {
                        _loadState.postValue(
                            ErrorState(it.message ?: EMPTY_STRING) {
                                getDeviceById(deviceId)
                            }
                        )
                    }
                )
        )
    }
}