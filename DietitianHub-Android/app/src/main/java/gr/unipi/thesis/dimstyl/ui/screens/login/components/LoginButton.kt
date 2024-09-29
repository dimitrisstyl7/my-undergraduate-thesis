package gr.unipi.thesis.dimstyl.ui.screens.login.components

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import gr.unipi.thesis.dimstyl.ui.theme.PrimaryColor

@Composable
fun LoginButton(isLoginButtonEnabled: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        enabled = isLoginButtonEnabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryColor.copy(alpha = 0.8f),
            contentColor = Color.White,
            disabledContainerColor = PrimaryColor.copy(alpha = 0.4f),
            disabledContentColor = Color.White
        )
    ) { Text("Log In") }
}