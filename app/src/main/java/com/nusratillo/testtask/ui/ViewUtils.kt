package com.nusratillo.testtask.ui

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import java.util.*

fun ViewGroup.inflate(@LayoutRes layoutResId: Int, isAttachToRoot: Boolean = false): View {
    val inflater = LayoutInflater.from(context)
    return inflater.inflate(layoutResId, this, isAttachToRoot)
}

fun Context.changeLocales(lang: String) {
    val config = resources.configuration
    val locale = Locale(lang)
    Locale.setDefault(locale)
    config.setLocale(locale)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        createConfigurationContext(config)
    resources.updateConfiguration(config, resources.displayMetrics)
}