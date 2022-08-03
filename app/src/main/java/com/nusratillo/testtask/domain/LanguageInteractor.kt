package com.nusratillo.testtask.domain

import com.nusratillo.testtask.data.Languages
import com.nusratillo.testtask.data.preferences.PrefManager

class LanguageInteractor(
    private val prefManager: PrefManager
) {
    var selectedLanguage: Languages
        get() = prefManager.getLanguage()
        set(value) {
            prefManager.saveLanguage(value)
        }
}