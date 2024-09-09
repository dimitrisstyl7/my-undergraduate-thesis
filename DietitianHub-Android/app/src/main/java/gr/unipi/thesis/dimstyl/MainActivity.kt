package gr.unipi.thesis.dimstyl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import gr.unipi.thesis.dimstyl.ui.theme.DietitianHubTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DietitianHubTheme {
            }
        }
    }

}