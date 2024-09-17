package gr.unipi.thesis.dimstyl.ui.screens.profile.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import gr.unipi.thesis.dimstyl.ui.theme.InputTextColor
import gr.unipi.thesis.dimstyl.ui.theme.LeftBarColor
import gr.unipi.thesis.dimstyl.ui.theme.TopBarColor

@Composable
fun ProfileOutlinedTextField(
    paddingValues: PaddingValues,
    label: String,
    placeholder: String,
    value: String,
    isError: Boolean,
    supportingText: @Composable (() -> Unit)?, // TODO: Remove nullable when validation is implemented
    onValueChange: (String) -> Unit,
    readOnly: Boolean,
    leadingIcon: @Composable (() -> Unit)? = {},
    trailingIconVisible: Boolean,
    onTrailingIconClick: () -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Next,
        capitalization = KeyboardCapitalization.Words
    ),
    keyboardActions: KeyboardActions
) {
    OutlinedTextField(
        modifier = Modifier
            .width(480.dp)
            .padding(paddingValues),
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        value = value,
        isError = isError,
        supportingText = supportingText,
        onValueChange = onValueChange,
        singleLine = true,
        readOnly = readOnly,
        leadingIcon = leadingIcon,
        trailingIcon = {
            AnimatedVisibility(
                visible = trailingIconVisible,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                IconButton(onClick = onTrailingIconClick) {
                    Icon(Icons.Outlined.Clear, "Clear $label field")
                }
            }
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = InputTextColor,
            unfocusedTextColor = InputTextColor,
            cursorColor = Color.Black,
            focusedBorderColor = TopBarColor,
            unfocusedBorderColor = LeftBarColor,
            focusedLeadingIconColor = TopBarColor,
            unfocusedLeadingIconColor = LeftBarColor,
            focusedTrailingIconColor = TopBarColor,
            unfocusedTrailingIconColor = LeftBarColor,
            focusedLabelColor = TopBarColor,
            unfocusedLabelColor = LeftBarColor
        )
    )
}

@Composable
fun ProfileOutlinedTextField(
    modifier: Modifier,
    label: String,
    placeholder: @Composable (() -> Unit)? = null,
    value: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)
) {
    OutlinedTextField(
        modifier = modifier,
        label = { Text(label) },
        placeholder = placeholder,
        value = value,
        onValueChange = {},
        singleLine = true,
        readOnly = true,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = InputTextColor,
            unfocusedTextColor = InputTextColor,
            cursorColor = Color.Black,
            focusedBorderColor = TopBarColor,
            unfocusedBorderColor = LeftBarColor,
            focusedLeadingIconColor = TopBarColor,
            unfocusedLeadingIconColor = LeftBarColor,
            focusedTrailingIconColor = TopBarColor,
            unfocusedTrailingIconColor = LeftBarColor,
            focusedLabelColor = TopBarColor,
            unfocusedLabelColor = LeftBarColor
        )
    )
}