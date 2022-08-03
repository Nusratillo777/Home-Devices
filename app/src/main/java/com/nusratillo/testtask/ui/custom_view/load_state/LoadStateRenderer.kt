package com.nusratillo.testtask.ui.custom_view.load_state

import android.view.LayoutInflater
import com.nusratillo.testtask.databinding.ViewErrorStateBinding
import com.nusratillo.testtask.databinding.ViewLoadingBinding

class LoadStateRenderer(
    private val placeHolder: PlaceHolder
) {
    private var currentLoadState: LoadState = NoneState

    private val errorView by lazy {
        ViewErrorStateBinding.inflate(LayoutInflater.from(placeHolder.context), placeHolder, false)
    }
    private val loadingView by lazy {
        ViewLoadingBinding.inflate(LayoutInflater.from(placeHolder.context), placeHolder, false)
    }

    fun render(loadState: LoadState) {
        if (currentLoadState == loadState)
            return

        currentLoadState = loadState
        when (loadState) {
            is ErrorState -> {
                placeHolder.isClickable = true
                placeHolder.isFocusable = true
                placeHolder.removeAllViews()
                placeHolder.addView(errorView.root)

                errorView.tryAgainBtn.setOnClickListener { loadState.reloadClickListener() }
                errorView.messageTv.text = loadState.errorMessage
            }

            is NoneState -> {
                placeHolder.isClickable = false
                placeHolder.isFocusable = false
                placeHolder.removeAllViews()
            }

            is LoadingState -> {
                placeHolder.isClickable = true
                placeHolder.isFocusable = true
                placeHolder.removeAllViews()
                placeHolder.addView(loadingView.root)
            }
        }
    }
}