package dev.rarebit.design.component.tools

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import dev.rarebit.design.theme.Black
import dev.rarebit.design.theme.DarkGrey

@Composable
fun ToolTextButton(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    TextButton(
        shape = RoundedCornerShape(8.dp),
        border = if (isSelected) {
            BorderStroke(width = 1.dp, color = DarkGrey)
        } else {
            null
        },
        onClick = {
            onClick()
        }
    ) {
        Text(
            text = label,
            color = if (isSelected) {
                Black
            } else {
                DarkGrey
            }
        )
    }
}
