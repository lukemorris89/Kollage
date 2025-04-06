package dev.rarebit.design.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.rarebit.design.theme.Black
import dev.rarebit.design.theme.KollageTheme
import dev.rarebit.design.theme.White

@Composable
fun LoadingIndicator(
    text: String,
    textColor: Color = White,
    indicatorColor: Color = White,
    backgroundColor: Color = Black.copy(alpha = 0.9f)
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor),
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CircularProgressIndicator(color = indicatorColor)
            VerticalSpacer(height = 16.dp)
            Text(text = text, color = textColor, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview
@Composable
private fun LoadingIndicatorPreview() {
    KollageTheme {
        LoadingIndicator(text = "Loading")
    }
}
