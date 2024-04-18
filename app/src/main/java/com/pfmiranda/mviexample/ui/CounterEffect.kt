package com.pfmiranda.mviexample.ui
import com.pfmiranda.mviexample.ui.base.ViewSideEffect

sealed class CounterEffect : ViewSideEffect {
    data class ShowMessage(val text : String) : CounterEffect()
}