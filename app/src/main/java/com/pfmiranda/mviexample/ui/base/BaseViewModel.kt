package com.pfmiranda.mviexample.ui.base


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

interface ViewEvent

interface ViewState

interface ViewSideEffect

abstract class BaseViewModel<UI_STATE, EFFECT, EVENT> : ViewModel() {

    private val initialState : UI_STATE by lazy { createInitialState() }

    private val _uiState : MutableStateFlow<UI_STATE> = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    //Emite si no hay Colector
    private val events: MutableSharedFlow<EVENT> = MutableSharedFlow()

    //Es necesario un Colector para que se genere el flujo completo esperando al subscriptor
    private val _effect = Channel<EFFECT>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()


    init {
        subscribeEvents()
    }

    private fun subscribeEvents(){
        viewModelScope.launch {
            events.collect {
                handleEvent(it)
            }
        }
    }


    fun setEvent(event: EVENT){
        viewModelScope.launch {
            events.emit(event)
        }
    }

    protected fun setState(reduce: UI_STATE.() ->  UI_STATE){
        _uiState.value = uiState.value.reduce()
    }

    protected fun setEffect(builder: () -> EFFECT){
        viewModelScope.launch {
            _effect.send(builder())
        }
    }

    protected abstract fun createInitialState() : UI_STATE
    protected abstract fun handleEvent(event: EVENT)

}