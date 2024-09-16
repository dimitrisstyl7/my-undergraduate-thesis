package gr.unipi.thesis.dimstyl.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
    border: BorderStroke? = null
) {
    androidx.compose.material3.Card(
        modifier = Modifier
            .width(300.dp)
            .heightIn(min = 150.dp),
        colors = CardDefaults.cardColors(containerColor = CardContainerColor),
        border = border,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 150.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = title,
                textAlign = TextAlign.Center,
                color = titleColor
            )
            Text(
                modifier = Modifier.padding(8.dp),
                text = createdAt,
                textAlign = TextAlign.Center,
                color = createdAtColor
            )
        }
    }
}