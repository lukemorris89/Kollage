package dev.rarebit.kollage.ui.createcollage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import dev.rarebit.design.component.ButtonHeight
import dev.rarebit.design.theme.White
import dev.rarebit.design.theme.paddingLarge

@Composable
fun SecondaryToolRow(
    content: @Composable () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(ButtonHeight.Medium.height)
            .clip(
                RoundedCornerShape(32.dp)
            )
            .background(White)
            .padding(horizontal = paddingLarge),
    ) {
        content()
    }
}
