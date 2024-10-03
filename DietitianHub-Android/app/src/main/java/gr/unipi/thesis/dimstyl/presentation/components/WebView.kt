package gr.unipi.thesis.dimstyl.presentation.components

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import gr.unipi.thesis.dimstyl.utils.Constants.Authorization.HEADER_AUTHORIZATION
import gr.unipi.thesis.dimstyl.utils.Constants.Authorization.TOKEN_TYPE

@SuppressLint("SetJavaScriptEnabled")
/*
* Note: Enabling JavaScript can expose the WebView to security risks.
* Ensure that the content being loaded is trusted and secure.
*
* In this case, the WebView is used to load a trusted URL coming
* from the backend server.
* */
@Composable
fun WebView(url: String, token: String) {
    val authMap = mapOf(HEADER_AUTHORIZATION to "$TOKEN_TYPE $token")

    AndroidView(factory = { context ->
        android.webkit.WebView(context).apply {
            settings.javaScriptEnabled = true
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = false
            loadUrl(url, authMap)
        }
    })
}