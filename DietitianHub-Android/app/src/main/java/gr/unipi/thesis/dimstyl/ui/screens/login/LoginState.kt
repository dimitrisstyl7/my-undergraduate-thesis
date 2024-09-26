package gr.unipi.thesis.dimstyl.ui.screens.login

data class LoginState(
    val username: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false
) {

    val isLoginButtonEnabled: Boolean = username.isNotBlank() && password.isNotBlank()

}
