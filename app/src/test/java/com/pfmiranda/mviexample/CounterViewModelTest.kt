package com.pfmiranda.mviexample

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.pfmiranda.mviexample.ui.CounterEvent
import com.pfmiranda.mviexample.ui.CounterState
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class CounterViewModelTest {

    private lateinit var viewModel: CounterViewModel

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule: CoroutineTestRule = CoroutineTestRule()

    @Before
    fun setup(){
        viewModel = CounterViewModel()
    }

    @Test
    fun test_state_changes() =
        coroutineTestRule.runBlockingTest {

            val stateChanges = mutableListOf<CounterState>()
            val expectedSates = mutableListOf(
                CounterState(count = 0),
                CounterState(count = 1),
                CounterState(count = 2),
                CounterState(count = 1),
            )

            launch {
                viewModel.uiState.collect { stateChanges.add(it) }
            }

            viewModel.setEvent(CounterEvent.Increment)
            viewModel.setEvent(CounterEvent.Increment)
            viewModel.setEvent(CounterEvent.Decrement)

            assertEquals(expectedSates, stateChanges)
        }
}