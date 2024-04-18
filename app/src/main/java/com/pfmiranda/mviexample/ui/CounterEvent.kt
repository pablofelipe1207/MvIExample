package com.pfmiranda.mviexample.ui
import com.pfmiranda.mviexample.ui.base.ViewEvent

sealed class CounterEvent : ViewEvent {
    data object Increment : CounterEvent()
    data object Decrement : CounterEvent()
}