package com.pfmiranda.mviexample

import com.pfmiranda.mviexample.ui.CounterEffect
import com.pfmiranda.mviexample.ui.CounterEvent
import com.pfmiranda.mviexample.ui.CounterState
import com.pfmiranda.mviexample.ui.base.BaseViewModel


class CounterViewModel : BaseViewModel<CounterState,CounterEffect, CounterEvent>() {

    override fun createInitialState() = CounterState(0)
    override fun handleEvent(event: CounterEvent) {
        when (event) {
            is CounterEvent.Increment -> {
                setState { copy(count = uiState.value.count + 1) }
                validateNumber()
            }

            is CounterEvent.Decrement -> {
                setState { copy(count = uiState.value.count - 1) }
            }
        }
    }

    private fun validateNumber(value : Int = 100){
        if(uiState.value.count == value){
            setEffect { CounterEffect.ShowMessage("Numero : $value") }
        }
    }

}

