package dev.rarebit.design.component.tools

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.rarebit.design.theme.Black
import dev.rarebit.design.theme.DarkGrey
import dev.rarebit.design.theme.White

@Composable
fun ToolButton(
    tool: Tool,
    selected: Boolean,
    colours: ToolButtonColours,
) {
    Box(
        modifier = Modifier
            .width(54.dp)
            .padding(bottom = 8.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = 100.dp,
                    bottomEnd = 100.dp
                )
            )
            .background(
                when {
                    selected -> colours.selectedButtonBackgroundColour
                    else -> colours.unselectedButtonBackgroundColour
                }
            )
            .then(
                if (selected) {
                    Modifier.border(
                        width = 1.dp,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                White,
                                DarkGrey
                            )
                        ),
                        shape = RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 0.dp,
                            bottomStart = 100.dp,
                            bottomEnd = 100.dp
                        )
                    )
                } else {
                    Modifier
                }
            )
            .padding(top = 8.dp),

        contentAlignment = Alignment.Center
    ) {
        IconButton(
            enabled = tool.enabled,
            onClick = tool.onClick,
        ) {
            Icon(
                painter = painterResource(id = tool.iconRes),
                tint = Black,
                contentDescription = null,
            )
        }
    }
}