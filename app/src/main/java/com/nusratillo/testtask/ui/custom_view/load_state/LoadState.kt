package com.nusratillo.testtask.ui.custom_view.load_state

sealed class LoadState

class ErrorState(
    val errorMessage: String = "",
    val reloadClickListener: () -> Unit = {}
) : LoadState()

object LoadingState : LoadState()
object NoneState : LoadState()