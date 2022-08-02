package com.nusratillo.testtask.data

import androidx.annotation.StringRes
import com.nusratillo.testtask.R

enum class Languages(@StringRes val titleId: Int) {
    EN(R.string.english),
    FR(R.string.french)
}