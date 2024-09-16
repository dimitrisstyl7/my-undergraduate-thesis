package gr.unipi.thesis.dimstyl.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import gr.unipi.thesis.dimstyl.ui.theme.CardContainerColor

@Composable
fun Card(
    title: String,
    createdAt: String,
    titleColor: Color,
    createdAtColor: Color,
) {
    androidx.compose.material3.Card(
        modifier = Modifier.size(width = 300.dp, height = 150.dp),
        colors = CardDefaults.cardColors(containerColor = CardContainerColor),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = title,
                textAlign = TextAlign.Center,
                color = titleColor
            )
            Text(text = createdAt, color = createdAtColor)
        }
    }
}