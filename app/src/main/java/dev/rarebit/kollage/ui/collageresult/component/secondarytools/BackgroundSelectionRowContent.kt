package dev.rarebit.kollage.ui.collageresult.component.secondarytools

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dev.rarebit.design.component.HorizontalSpacer
import dev.rarebit.design.component.tools.ToolTextButton
import dev.rarebit.design.theme.Black
import dev.rarebit.design.theme.White
import dev.rarebit.design.theme.paddingMedium
import dev.rarebit.design.theme.paddingSmall
import dev.rarebit.kollage.R
import dev.rarebit.kollage.ui.collageresult.CollageResultViewAction
import dev.rarebit.kollage.ui.collageresult.data.CollageResultViewData
import dev.rarebit.kollage.ui.createcollage.util.imageutil.BackgroundSelection

@Composable
fun BackgroundSelectionRowContent(
    viewData: CollageResultViewData,
    onViewAction: (CollageResultViewAction) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(White)
            .padding(horizontal = paddingMedium, vertical = paddingSmall),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.padding(start = paddingMedium),
            text = stringResource(R.string.background),
            style = MaterialTheme.typography.titleMedium.copy(
                color = Black,
            ),
        )
        HorizontalSpacer(paddingSmall)
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BackgroundSelection.entries.forEach {
                ToolTextButton(
                    label = it.value,
                    isSelected = viewData.backgroundSelection == it,
                    onClick = {
                        onViewAction(CollageResultViewAction.OnUpdateBackground(it))
                    }
                )
                HorizontalSpacer(paddingSmall)
            }
        }
    }
}