package com.nusratillo.testtask.domain

import com.nusratillo.testtask.data.Languages
import com.nusratillo.testtask.data.model.User
import com.nusratillo.testtask.data.preferences.PrefManager

class UserUseCase(
    private val prefManager: PrefManager
) {
    var selectedLanguage: Languages
        get() = prefManager.getLanguage()
        set(value) {
            prefManager.saveLanguage(value)
        }

    fun getUser(): User = prefManager.user

    fun saveUser(user: User) {
        prefManager.user = user
    }
}