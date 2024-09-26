package gr.unipi.thesis.dimstyl.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import gr.unipi.thesis.dimstyl.R
import gr.unipi.thesis.dimstyl.ui.components.OutlinedTextField
import gr.unipi.thesis.dimstyl.ui.theme.BackgroundGradient
import gr.unipi.thesis.dimstyl.ui.theme.LeftBarColor
import gr.unipi.thesis.dimstyl.ui.theme.PrimaryColor
import gr.unipi.thesis.dimstyl.ui.theme.TopBarColor

@Composable
fun LoginScreen(viewModel: LoginViewModel = viewModel(), onSuccessfulLogin: () -> Unit) {
    val focusManager = LocalFocusManager.current
    val state by viewModel.state.collectAsStateWithLifecycle()

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
        OutlinedTextField(
            paddingValues = PaddingValues(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 16.dp),
            label = "Username",
            placeholder = "Enter your username",
            value = state.username,
            isError = false, // TODO: Implement error handling
            supportingText = null, // TODO: Field validation message
            onValueChange = { viewModel.setUsername(it) },
            readOnly = false,
            leadingIcon = {
                Icon(imageVector = Icons.Rounded.Person, contentDescription = null)
            },
            trailingIconVisible = state.username.isNotEmpty(),
            onTrailingIconClick = { viewModel.setUsername("") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.None
            ),
            keyboardActions = KeyboardActions { focusManager.moveFocus(FocusDirection.Next) }
        )
        OutlinedTextField(
            paddingValues = PaddingValues(start = 24.dp, end = 24.dp, bottom = 24.dp),
            label = "Password",
            placeholder = "Enter your password",
            value = state.password,
            isError = false, // TODO: Implement error handling
            supportingText = null, // TODO: Field validation message
            onValueChange = { viewModel.setPassword(it) },
            readOnly = false,
            leadingIcon = {
                Icon(imageVector = Icons.Rounded.Lock, contentDescription = null)
            },
            trailingIcon = {
                val iconRes =
                    if (state.isPasswordVisible) R.drawable.round_remove_red_eye_24
                    else R.drawable.eye_off_svgrepo_com
                Icon(
                    painter = painterResource(iconRes),
                    contentDescription = "Show password"
                )
            },
            trailingIconVisible = state.password.isNotEmpty(),
            onTrailingIconClick = { viewModel.togglePasswordVisibility() },
            visualTransformation = if (state.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
                capitalization = KeyboardCapitalization.None
            ),
            keyboardActions = KeyboardActions { focusManager.clearFocus() }
        )
        Button(
            onClick = onSuccessfulLogin,
            enabled = state.isLoginButtonEnabled,
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryColor.copy(alpha = 0.8f),
                contentColor = Color.White,
                disabledContainerColor = PrimaryColor.copy(alpha = 0.4f),
                disabledContentColor = Color.White
            )
        ) { Text("Log In") }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen {}
}