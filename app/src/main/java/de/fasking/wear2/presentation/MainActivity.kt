/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package de.fasking.wear2.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import de.fasking.wear2.presentation.theme.Wear2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WearAppScreen()
        }
    }
}

class WearAppViewModel : ViewModel() {
    private val _text: MutableLiveData<String> = MutableLiveData("Pausiert.")
    val text: LiveData<String> = _text

    fun onTextChange(newText: String) {
        _text.value = newText
    }

    private val _status: MutableLiveData<Int> = MutableLiveData(0)
    val status: LiveData<Int> = _status

    fun onStatusChange(newStatus: Int) {
        _status.value = newStatus
    }
}

@Composable
fun WearAppScreen(wearAppViewModel: WearAppViewModel = WearAppViewModel()) {
    val text: String by wearAppViewModel.text.observeAsState("Pausiert.")
    val status: Int by wearAppViewModel.status.observeAsState(0)
    WearAppContent(
        text = text,
        onTextChange = { wearAppViewModel.onTextChange(it) },
        status = status,
        onStatusChange = { wearAppViewModel.onStatusChange(it) }
    )
}

@Composable
fun WearAppContent(
    text: String,
    onTextChange: (String) -> Unit,
    status: Int,
    onStatusChange: (Int) -> Unit
) {
    Wear2Theme {
        /* If you have enough items in your list, use [ScalingLazyColumn] which is an optimized
         * version of LazyColumn for wear devices with some added features. For more information,
         * see d.android.com/wear/compose.
         */
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.primary,
                text = text
            )
            Button(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                enabled = status == 0,
                onClick = {
                    onTextChange("Läuft.")
                    onStatusChange(1)
                }) {
                Text(
                    text = "Start"
                )
            }
            Button(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                enabled = status == 1,
                onClick = {
                    onTextChange("Pausiert.")
                    onStatusChange(0)
                }) {
                Text(text = "Stop")
            }
        }
    }
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    WearAppScreen()
}