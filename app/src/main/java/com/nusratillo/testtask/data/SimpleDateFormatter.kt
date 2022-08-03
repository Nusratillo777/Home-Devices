package com.nusratillo.testtask.data

import java.text.SimpleDateFormat
import java.util.*

fun Long.convertLongToDateString(): String {
    val date = Date(this)
    val format = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return format.format(date)
}

fun Triple<Int, Int, Int>.convertToLong(): Long {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.YEAR, first)
    calendar.set(Calendar.MONTH, second)
    calendar.set(Calendar.DAY_OF_MONTH, third)
    return calendar.time.time
}

fun Long.convertToTripleDate(): Triple<Int, Int, Int> {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return Triple(
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
}