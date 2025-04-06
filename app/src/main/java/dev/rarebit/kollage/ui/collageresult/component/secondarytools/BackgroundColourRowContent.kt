package dev.rarebit.kollage.ui.collageresult.component.secondarytools

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.rarebit.design.theme.Black
import dev.rarebit.design.theme.paddingMedium
import dev.rarebit.kollage.ui.collageresult.CollageResultViewAction
import dev.rarebit.kollage.ui.collageresult.data.CollageResultViewData
import dev.rarebit.kollage.ui.createcollage.collage.component.secondarytools.LayerColour
import dev.rarebit.design.R as DR

@Composable
fun BackgroundColourRowContent(
    viewData: CollageResultViewData,
    onViewAction: (CollageResultViewAction) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = paddingMedium),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            LayerColour.entries.forEach {
                ColourButton(
                    colour = it,
                    selected = viewData.backgroundColor == it,
                    onClick = {
                        onViewAction(CollageResultViewAction.OnUpdateBackgroundColour(it))
                    }
                )
            }
        }
    }
}

@Composable
private fun ColourButton(
    colour: LayerColour,
    selected: Boolean,
    onClick: (LayerColour) -> Unit,
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
                tint = colour.colour,
                contentDescription = null,
            )
        }
    }
}
