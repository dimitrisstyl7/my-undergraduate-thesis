package gr.unipi.thesis.dimstyl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import gr.unipi.thesis.dimstyl.ui.screens.main.MainScreen
import gr.unipi.thesis.dimstyl.ui.theme.DietitianHubTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DietitianHubTheme {
                Surface(Modifier.fillMaxSize()) {
                    MainScreen(exitApp = { finish() })
                }
            }
        }
    }

}