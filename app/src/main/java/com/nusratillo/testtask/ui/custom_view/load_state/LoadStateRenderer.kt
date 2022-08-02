package com.nusratillo.testtask.ui.custom_view.load_state

import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import com.nusratillo.testtask.R

class LoadStateRenderer(
    private val placeHolder: PlaceHolder
) {
    private var currentLoadState: LoadState = NoneState

    private val errorView by lazy {
        LayoutInflater.from(placeHolder.context)
            .inflate(R.layout.view_error_state, placeHolder, false)
    }
    private val loadingView by lazy {
        LayoutInflater.from(placeHolder.context)
            .inflate(R.layout.view_loading, placeHolder, false)
    }

    private val reloadBtn: Button by lazy { errorView.findViewById(R.id.try_again_btn) }
    private val errorMessageTv: TextView by lazy { errorView.findViewById(R.id.message_tv) }

    fun render(loadState: LoadState) {
        if (currentLoadState == loadState)
            return

        currentLoadState = loadState
        when (loadState) {
            is ErrorState -> {
                placeHolder.isClickable = true
                placeHolder.isFocusable = true
                placeHolder.removeAllViews()
                placeHolder.addView(errorView)

                reloadBtn.setOnClickListener { loadState.reloadClickListener() }
                errorMessageTv.text = loadState.errorMessage
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
                placeHolder.addView(loadingView)
            }
        }
    }
}