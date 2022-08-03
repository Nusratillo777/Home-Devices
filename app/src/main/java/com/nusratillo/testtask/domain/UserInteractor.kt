package com.nusratillo.testtask.domain

import com.nusratillo.testtask.data.model.User
import com.nusratillo.testtask.data.preferences.PrefManager

class UserInteractor(
    private val prefManager: PrefManager
) {
    fun getUser(): User = prefManager.user

    fun saveUser(user: User) {
        prefManager.user = user
    }
}