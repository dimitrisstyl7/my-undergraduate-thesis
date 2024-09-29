package gr.unipi.thesis.dimstyl.presentation.screens.landing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gr.unipi.thesis.dimstyl.presentation.theme.BackgroundGradient
import gr.unipi.thesis.dimstyl.presentation.theme.LeftBarColor
import gr.unipi.thesis.dimstyl.presentation.theme.TopBarColor
import gr.unipi.thesis.dimstyl.presentation.utils.LoginStatus
import kotlinx.coroutines.delay

@Composable
fun LandingScreen(
    loginStatus: LoginStatus,
    onLoginStatusResolved: () -> Unit
) {
    val currentOnLoginStatusResolved by rememberUpdatedState(onLoginStatusResolved)

    LaunchedEffect(loginStatus) {
        // Minimum delay of 2.5 seconds
        delay(2500)

        // Wait for loginStatus to be resolved
        while (loginStatus == LoginStatus.UNKNOWN) {
            delay(100)
        }

        // Once loginStatus is resolved, call the callback function
        currentOnLoginStatusResolved()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGradient),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row {
            Text(
                text = "Dietitian",
                color = LeftBarColor,
                style = MaterialTheme.typography.displayMedium
            )
            Text(
                text = "Hub",
                color = TopBarColor,
                style = MaterialTheme.typography.displayMedium
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        LinearProgressIndicator(
            color = TopBarColor,
            trackColor = Color.Transparent
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LandingScreenPreview() {
    LandingScreen(LoginStatus.UNKNOWN) {}
}
