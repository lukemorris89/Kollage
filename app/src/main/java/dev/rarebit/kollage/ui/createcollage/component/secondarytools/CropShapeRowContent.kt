package dev.rarebit.kollage.ui.createcollage.component.secondarytools

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import dev.rarebit.design.component.HorizontalSpacer
import dev.rarebit.design.theme.Black
import dev.rarebit.design.theme.DarkGrey
import dev.rarebit.design.theme.paddingMedium
import dev.rarebit.design.theme.paddingSmall
import dev.rarebit.kollage.R
import dev.rarebit.kollage.ui.createcollage.CreateCollageViewAction
import dev.rarebit.kollage.ui.createcollage.data.CreateCollageViewData

@Composable
fun CropShapeRowContent(
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
            text = stringResource(R.string.shape),
            style = MaterialTheme.typography.titleMedium.copy(
                color = Black,
            ),
        )
        HorizontalSpacer(paddingSmall)
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CropShape.entries.forEach {
                IconButton(
                    onClick = {
                        onViewAction(CreateCollageViewAction.OnCropShapeChanged(it))
                    }
                ) {
                    Icon(
                        painter = painterResource(id = it.iconRes),
                        tint = if (it == viewData.selectedCropShape) {
                            Black
                        } else {
                            DarkGrey
                        },
                        contentDescription = null,
                    )
                }
                HorizontalSpacer(paddingSmall)
            }
        }
    }
}
