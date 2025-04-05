package dev.rarebit.kollage.ui.collageresult.component.secondarytools

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dev.rarebit.design.component.HorizontalSpacer
import dev.rarebit.design.theme.Black
import dev.rarebit.design.theme.DarkGrey
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
            .padding(start = paddingMedium),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
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
                TextButton(
                    onClick = {
                        onViewAction(CollageResultViewAction.OnUpdateBackground(it))
                    }
                ) {
                    Text(
                        text = it.value,
                        color = if (viewData.backgroundSelection == it) {
                            Black
                        } else {
                            DarkGrey
                        }
                    )
                }
                HorizontalSpacer(paddingSmall)
            }
        }
    }
}