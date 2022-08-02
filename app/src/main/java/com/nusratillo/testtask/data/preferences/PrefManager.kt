package com.nusratillo.testtask.data.preferences

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.nusratillo.testtask.*
import com.nusratillo.testtask.data.Languages
import com.nusratillo.testtask.data.model.User

class PrefManager constructor(
    context: Context,
    private val gson: Gson
) {
    private val pref: SharedPreferences = context.getSharedPreferences(APP_PREFERENCE_NAME, 0)
    private val editor: SharedPreferences.Editor = pref.edit()

    var isFirstLoading: Boolean
        get() = pref.getBoolean(KEY_FIRST_LOADING, true)
        set(value) {
            editor.apply {
                putBoolean(KEY_FIRST_LOADING, value)
                commit()
            }
        }

    var user: User
        get() {
            val jsonString = pref.getString(KEY_USER, EMPTY_STRING) ?: EMPTY_STRING
            return gson.fromJson(jsonString, User::class.java) ?: User()
        }
        set(value) {
            val jsonString = gson.toJson(value)
            editor.apply {
                putString(KEY_USER, jsonString)
                commit()
            }
        }

    fun saveLanguage(languages: Languages) {
        editor.apply {
            putString(KEY_LANGUAGE, languages.name)
            commit()
        }
    }

    fun getLanguage(): Languages {
        return Languages.valueOf(pref.getString(KEY_LANGUAGE, DEFAULT_LANG) ?: DEFAULT_LANG)
    }
}