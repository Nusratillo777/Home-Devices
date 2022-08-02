package com.nusratillo.testtask.ui.user_devices

data class SelectableItems(
    val items: List<Int>,
    val selectedItemPosition: Int,
    val state: DialogState
)

enum class DialogState {
    OPEN,
    CLOSE
}

data class MultiSelectableItems(
    val items: List<Int>,
    val selectedItems: List<Boolean>
)