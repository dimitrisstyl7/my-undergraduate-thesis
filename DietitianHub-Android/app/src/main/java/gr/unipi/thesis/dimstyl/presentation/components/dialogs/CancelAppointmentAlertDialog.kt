package gr.unipi.thesis.dimstyl.presentation.components.dialogs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import gr.unipi.thesis.dimstyl.presentation.theme.WarningColor

@Composable
fun CancelAppointmentAlertDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        title = "Cancel Appointment",
        text = {
            Text(
                text = "You are about to cancel your appointment. This action cannot be undone. Are you sure?",
                textAlign = TextAlign.Center
            )
        },
        confirmButtonText = "Yes, I'm Sure",
        dismissButtonText = "No, Go Back",
        icon = Icons.Rounded.Warning,
        iconContentColor = WarningColor,
        onConfirm = onConfirm,
        onDismiss = onDismiss
    )
}