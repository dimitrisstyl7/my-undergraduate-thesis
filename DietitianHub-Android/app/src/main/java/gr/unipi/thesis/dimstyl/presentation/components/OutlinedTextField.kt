package gr.unipi.thesis.dimstyl.presentation.components

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
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import gr.unipi.thesis.dimstyl.presentation.theme.LeftBarColor
import gr.unipi.thesis.dimstyl.presentation.theme.PrimaryColor
import gr.unipi.thesis.dimstyl.presentation.theme.TextFieldInputColor
import gr.unipi.thesis.dimstyl.presentation.theme.TopBarColor

@Composable
fun OutlinedTextField(
    paddingValues: PaddingValues,
    label: String,
    placeholder: String,
    value: String,
    isError: Boolean,
    supportingText: @Composable (() -> Unit)?,
    onValueChange: (String) -> Unit,
    readOnly: Boolean,
    leadingIcon: @Composable () -> Unit = {},
    trailingIcon: @Composable () -> Unit = {
        Icon(
            imageVector = Icons.Outlined.Clear,
            contentDescription = "Clear $label field"
        )
    },
    trailingIconVisible: Boolean,
    onTrailingIconClick: () -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Next,
        capitalization = KeyboardCapitalization.Words
    ),
    keyboardActions: KeyboardActions
) {
    androidx.compose.material3.OutlinedTextField(
        modifier = Modifier
            .width(480.dp)
            .padding(paddingValues),
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        value = value,
        isError = isError,
        supportingText = supportingText?.let { { it() } },
        onValueChange = onValueChange,
        singleLine = true,
        readOnly = readOnly,
        leadingIcon = { leadingIcon() },
        trailingIcon = {
            AnimatedVisibility(
                visible = trailingIconVisible,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                IconButton(onClick = onTrailingIconClick) { trailingIcon() }
            }
        },
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = TextFieldInputColor,
            unfocusedTextColor = TextFieldInputColor,
            cursorColor = PrimaryColor,
            focusedBorderColor = TopBarColor,
            unfocusedBorderColor = LeftBarColor,
            focusedLeadingIconColor = TopBarColor,
            unfocusedLeadingIconColor = LeftBarColor,
            focusedTrailingIconColor = TopBarColor,
            unfocusedTrailingIconColor = LeftBarColor,
            focusedLabelColor = TopBarColor,
            unfocusedLabelColor = LeftBarColor,
            focusedPlaceholderColor = TopBarColor
        )
    )
}

@Composable
fun OutlinedTextField(
    modifier: Modifier = Modifier,
    label: String,
    placeholder: @Composable (() -> Unit)? = null,
    value: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit),
    colors: TextFieldColors
) {
    androidx.compose.material3.OutlinedTextField(
        modifier = modifier,
        label = { Text(label) },
        placeholder = placeholder?.let { { it() } },
        value = value,
        onValueChange = {},
        singleLine = true,
        readOnly = true,
        leadingIcon = leadingIcon?.let { { it() } },
        trailingIcon = { trailingIcon() },
        colors = colors
    )
}