package com.nusratillo.testtask.ui.activities.user

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nusratillo.testtask.EMPTY_STRING
import com.nusratillo.testtask.R
import com.nusratillo.testtask.data.convertToLong
import com.nusratillo.testtask.data.convertToTripleDate
import com.nusratillo.testtask.data.model.User
import com.nusratillo.testtask.domain.UserInteractor

class UserViewModel(
    private val userInteractor: UserInteractor
) : ViewModel() {
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user
    private val _message = MutableLiveData<Int>()
    val message: LiveData<Int> = _message
    private val _fieldEmptyAction = MutableLiveData<List<FieldEmptyAction>>()
    val fieldEmptyAction: LiveData<List<FieldEmptyAction>> = _fieldEmptyAction
    private val _showDatePickerAction = MutableLiveData<Triple<Int, Int, Int>>()
    val showDatePickerAction: LiveData<Triple<Int, Int, Int>> = _showDatePickerAction

    init {
        _user.postValue(userInteractor.getUser())
    }

    fun setField(state: FieldState) {
        when (state.type) {
            FieldType.FIRST_NAME -> _user.value?.firstName = state.value
            FieldType.LAST_NAME -> _user.value?.lastName = state.value
            FieldType.CITY -> _user.value?.address?.city = state.value
            FieldType.POSTAL_CODE ->
                _user.value?.address?.postalCode = state.value.toIntOrNull() ?: 0
            FieldType.STREET -> _user.value?.address?.street = state.value
            FieldType.STREET_CODE -> _user.value?.address?.streetCode = state.value
            FieldType.COUNTRY -> _user.value?.address?.country = state.value
        }
    }

    fun birthDateOnClick() {
        _showDatePickerAction.postValue(
            _user.value?.birthDate?.convertToTripleDate()
        )
    }

    fun birthDateOnSelect(date: Triple<Int, Int, Int>) {
        val newUser = _user.value?.copy(birthDate = date.convertToLong()) ?: return
        _user.postValue(newUser)
    }

    fun updateUser() {
        if (isAllFieldsCorrect()) {
            userInteractor.saveUser(_user.value ?: User())
            _message.postValue(R.string.user_updated_successfully)
        }
    }

    private fun isAllFieldsCorrect(): Boolean {
        val actions = mutableListOf<FieldEmptyAction>()
        if (_user.value?.firstName == EMPTY_STRING) {
            actions.add(
                FieldEmptyAction(
                    FieldType.FIRST_NAME,
                    R.string.field_could_not_be_empty
                )
            )
        }
        if (_user.value?.lastName == EMPTY_STRING) {
            actions.add(
                FieldEmptyAction(
                    FieldType.LAST_NAME,
                    R.string.field_could_not_be_empty
                )
            )
        }
        if (_user.value?.address?.city == EMPTY_STRING) {
            actions.add(
                FieldEmptyAction(
                    FieldType.CITY,
                    R.string.field_could_not_be_empty
                )
            )
        }
        if (_user.value?.address?.postalCode == 0) {
            actions.add(
                FieldEmptyAction(
                    FieldType.POSTAL_CODE,
                    R.string.enter_valid_postal_code
                )
            )
        }
        if (_user.value?.address?.street == EMPTY_STRING) {
            actions.add(
                FieldEmptyAction(
                    FieldType.STREET,
                    R.string.field_could_not_be_empty
                )
            )
        }
        if (_user.value?.address?.streetCode == EMPTY_STRING) {
            actions.add(
                FieldEmptyAction(
                    FieldType.STREET_CODE,
                    R.string.field_could_not_be_empty
                )
            )
        }
        if (_user.value?.address?.country == EMPTY_STRING) {
            actions.add(
                FieldEmptyAction(
                    FieldType.COUNTRY,
                    R.string.field_could_not_be_empty
                )
            )
        }
        _fieldEmptyAction.postValue(actions)
        return actions.isEmpty()
    }
}

class FieldEmptyAction(
    val type: FieldType,
    @StringRes val messageId: Int
)

class FieldState(
    val type: FieldType,
    val value: String
)

enum class FieldType {
    FIRST_NAME,
    LAST_NAME,
    CITY,
    POSTAL_CODE,
    STREET,
    STREET_CODE,
    COUNTRY
}