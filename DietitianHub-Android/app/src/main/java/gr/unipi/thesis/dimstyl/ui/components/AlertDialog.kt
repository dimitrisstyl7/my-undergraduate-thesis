package gr.unipi.thesis.dimstyl.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import gr.unipi.thesis.dimstyl.ui.theme.LeftBarColor

@Composable
fun AlertDialog(
    title: String,
    text: String,
    confirmButtonText: String,
    dismissButtonText: String,
    icon: ImageVector,
    containerColor: Color = LeftBarColor,
    iconContentColor: Color = Color.Unspecified,
    titleContentColor: Color = Color.White,
    textContentColor: Color = Color.White,
    dismissButtonContentColor: Color = Color.White,
    confirmButtonContentColor: Color = Color.White,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        icon = { Icon(imageVector = icon, contentDescription = null) },
        title = { Text(title) },
        text = { Text(text) },
        containerColor = containerColor,
        titleContentColor = titleContentColor,
        textContentColor = textContentColor,
        iconContentColor = iconContentColor,
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(
                onClick = { onConfirm() },
                colors = ButtonDefaults.textButtonColors(contentColor = confirmButtonContentColor)
            ) { Text(confirmButtonText) }
        },
        dismissButton = {
            TextButton(
                onClick = { onDismiss() },
                colors = ButtonDefaults.textButtonColors(contentColor = dismissButtonContentColor)
            ) { Text(dismissButtonText) }
        }
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AlertDialogPreview() {
    val openAlertDialog = remember { mutableStateOf(true) }
    TextButton(modifier = Modifier.fillMaxSize(),
        onClick = { openAlertDialog.value = true }) {
        Text("Open AlertDialog")
    }
    when {
        openAlertDialog.value -> {
            AlertDialog(
                title = "Title",
                text = "Alert Dialog Text",
                confirmButtonText = "Confirm",
                dismissButtonText = "Cancel",
                icon = Icons.Rounded.Info,
                onDismiss = { openAlertDialog.value = false },
                onConfirm = { openAlertDialog.value = false },
            )
        }
    }
}