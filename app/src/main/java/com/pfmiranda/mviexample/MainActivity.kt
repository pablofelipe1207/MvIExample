package com.pfmiranda.mviexample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pfmiranda.mviexample.ui.CounterEffect
import com.pfmiranda.mviexample.ui.CounterEvent
import com.pfmiranda.mviexample.ui.theme.MVIExampleTheme
import kotlinx.coroutines.flow.Flow



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MVIExampleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CounterView(
                        CounterViewModel()
                    )
                }
            }
        }
    }
}

@Composable
fun CounterView(
    viewModel: CounterViewModel
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HandleEffects(events = viewModel.effect)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Count: ${uiState.count}")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { viewModel.setEvent(CounterEvent.Increment) }) {
            Text(text = "Increment")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { viewModel.setEvent(CounterEvent.Decrement)}) {
            Text(text = "Decrement")
        }
    }
}

@Composable
fun HandleEffects(
    events: Flow<CounterEffect>
){
    val context = LocalContext.current
    events.collectWithLifecycle { event ->
        when(event){
            is CounterEffect.ShowMessage -> Toast.makeText(context, event.text, Toast.LENGTH_LONG).show()
        }
    }
}