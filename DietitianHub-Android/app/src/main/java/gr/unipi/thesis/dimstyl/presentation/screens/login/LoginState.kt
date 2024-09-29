package gr.unipi.thesis.dimstyl.presentation.screens.login

data class LoginState(
    val username: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val processingLoginRequest: Boolean = false,
    val loginHasError: Boolean = false,
    val errorMessage: String = ""
) {

    val isLoginButtonEnabled: Boolean = username.isNotBlank() && password.isNotBlank()

}
