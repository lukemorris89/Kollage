package dev.rarebit.kollage.ui.more.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.rarebit.design.component.ButtonHeight
import dev.rarebit.design.theme.Black
import dev.rarebit.design.theme.White
import dev.rarebit.design.theme.paddingMedium
import dev.rarebit.design.R as DR

@Composable
fun MoreButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(ButtonHeight.Large.height)
            .clip(RoundedCornerShape(12.dp))
            .background(White)
            .clickable(enabled = enabled) {
                onClick()
            }
            .padding(horizontal = paddingMedium),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge.copy(
                color = Black
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Icon(
            painter = painterResource(id = DR.drawable.ic_chevron_right),
            contentDescription = null,
            tint = Black,
        )
    }
}
