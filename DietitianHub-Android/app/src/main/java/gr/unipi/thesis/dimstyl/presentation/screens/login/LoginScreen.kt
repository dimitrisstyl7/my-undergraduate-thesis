package gr.unipi.thesis.dimstyl.presentation.screens.login

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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
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
import gr.unipi.thesis.dimstyl.App
import gr.unipi.thesis.dimstyl.R
import gr.unipi.thesis.dimstyl.presentation.components.OutlinedTextField
import gr.unipi.thesis.dimstyl.presentation.components.circularProgressIndicators.ButtonCircularProgressIndicator
import gr.unipi.thesis.dimstyl.presentation.screens.login.components.LoginButton
import gr.unipi.thesis.dimstyl.presentation.theme.BackgroundGradient
import gr.unipi.thesis.dimstyl.presentation.theme.LeftBarColor
import gr.unipi.thesis.dimstyl.presentation.theme.TopBarColor
import gr.unipi.thesis.dimstyl.presentation.utils.viewModelFactory

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel<LoginViewModel>(
        factory = viewModelFactory { LoginViewModel(App.appModule.loginUseCase) }
    ),
    onSuccessfulLogin: (String) -> Unit
) {
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
            isError = state.loginHasError,
            supportingText = null,
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
            isError = state.loginHasError,
            supportingText = if (state.loginHasError) {
                { Text(state.errorMessage) }
            } else null,
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
        if (state.processingLoginRequest) ButtonCircularProgressIndicator()
        else LoginButton(isLoginButtonEnabled = state.isLoginButtonEnabled) {
            viewModel.setProcessingLoginRequest(true)
            viewModel.login(onSuccessfulLogin = ({ token -> onSuccessfulLogin(token) }))
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen {}
}