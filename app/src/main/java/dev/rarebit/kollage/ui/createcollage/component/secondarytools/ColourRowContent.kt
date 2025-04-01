package dev.rarebit.kollage.ui.createcollage.component.secondarytools

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.rarebit.design.component.HorizontalSpacer
import dev.rarebit.design.theme.Black
import dev.rarebit.design.theme.DarkGrey
import dev.rarebit.design.theme.White
import dev.rarebit.design.theme.paddingMedium
import dev.rarebit.design.theme.paddingSmall
import dev.rarebit.kollage.R
import dev.rarebit.kollage.ui.createcollage.CreateCollageViewAction
import dev.rarebit.kollage.ui.createcollage.data.CreateCollageViewData
import dev.rarebit.design.R as DR

@Composable
fun ColourRowContent(
    viewData: CreateCollageViewData,
    onViewAction: (CreateCollageViewAction) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = paddingMedium),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.colour),
            style = MaterialTheme.typography.titleMedium.copy(
                color = Black,
            ),
        )
        HorizontalSpacer(paddingSmall)
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ColourButton(
                colour = Color.Transparent,
                selected = viewData.selectedColor == Color.Transparent,
                onClick = {
                    onViewAction(CreateCollageViewAction.OnColourClicked(Color.Transparent))
                }
            )
            Colour.entries.forEach {
                ColourButton(
                    colour = it.colour,
                    selected = viewData.selectedColor == it.colour,
                    onClick = {
                        onViewAction(CreateCollageViewAction.OnColourClicked(it))
                    }
                )
            }
        }
    }
}

@Composable
private fun ColourButton(
    colour: Color,
    selected: Boolean,
    onClick: (Color) -> Unit,
) {
    IconButton(
        onClick = {
            onClick(colour)
        }
    ) {
        Box(
            modifier = Modifier
                .size(26.dp)
                .clip(CircleShape)
                .background(
                    when {
                        colour == Color.Transparent && selected -> Black
                        colour == Color.Transparent -> DarkGrey
                        selected -> Black
                        else -> Color.Transparent
                    }
                ),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painterResource(
                    id = DR.drawable.ic_circle_filled
                ),
                tint = if (colour == Color.Transparent) {
                    White
                } else {
                    colour
                },
                contentDescription = null,
            )
        }
    }
}
