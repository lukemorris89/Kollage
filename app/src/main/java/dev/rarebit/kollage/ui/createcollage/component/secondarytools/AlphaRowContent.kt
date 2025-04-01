package dev.rarebit.kollage.ui.createcollage.component.secondarytools

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.rarebit.design.component.HorizontalSpacer
import dev.rarebit.design.theme.Black
import dev.rarebit.design.theme.DarkGrey
import dev.rarebit.design.theme.LightGrey
import dev.rarebit.design.theme.White
import dev.rarebit.design.theme.paddingMedium
import dev.rarebit.design.theme.paddingSmall
import dev.rarebit.kollage.R
import dev.rarebit.kollage.ui.createcollage.CreateCollageViewAction
import dev.rarebit.kollage.ui.createcollage.data.CreateCollageViewData

@Composable
fun AlphaRowContent(
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
            text = stringResource(R.string.alpha),
            style = MaterialTheme.typography.titleMedium.copy(
                color = Black,
            ),
        )
        HorizontalSpacer(paddingSmall)
        SliderRow(
            value = viewData.selectedAlpha,
            onValueChanged = { onViewAction(CreateCollageViewAction.OnAlphaChanged(it)) },
            valueRange = 0f..1f
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SliderRow(
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    onValueChanged: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Slider(
            modifier = Modifier
                .height(8.dp)
                .weight(1f),
            value = value,
            steps = 100,
            valueRange = valueRange,
            colors = SliderDefaults.colors(
                thumbColor = Black,
                activeTrackColor = White,
                activeTickColor = DarkGrey,
                inactiveTrackColor = White,
                inactiveTickColor = LightGrey,
            ),
            onValueChange = { onValueChanged(it) }
        )
        HorizontalSpacer(paddingSmall)
        Text(
            modifier = Modifier.wrapContentWidth(),
            text = (value * 100).toInt().toString(),
            style = MaterialTheme.typography.bodyLarge.copy(color = Black),
        )
    }
}
