package gr.unipi.thesis.dimstyl.presentation.components.modalDrawerSheet

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun HorizontalDivider(
    paddingValues: PaddingValues = PaddingValues(vertical = 10.dp, horizontal = 18.dp),
    thickness: Dp = 2.dp,
    color: Color = Color.White
) {
    androidx.compose.material3.HorizontalDivider(
        modifier = Modifier.padding(paddingValues),
        thickness = thickness,
        color = color
    )
}