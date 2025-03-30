package dev.rarebit.kollage.ui.gallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.rarebit.design.component.ButtonColours
import dev.rarebit.design.component.PrimaryButton
import dev.rarebit.design.component.VerticalSpacer
import dev.rarebit.design.theme.White
import dev.rarebit.design.theme.paddingLarge
import dev.rarebit.kollage.R
import dev.rarebit.kollage.ui.home.HomeViewAction
import dev.rarebit.kollage.ui.home.data.HomeViewData
import dev.rarebit.design.R as DR

@Composable
fun EmptyGalleryContent(
    viewData: HomeViewData,
    onViewAction: (HomeViewAction) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.padding(horizontal = paddingLarge),
            text = stringResource(R.string.you_haven_t_created_anything_yet),
            style = MaterialTheme.typography.titleLarge.copy(
                color = White,
                textAlign = TextAlign.Center,
            )
        )

        VerticalSpacer(height = 54.dp)
        PrimaryButton(
            text = stringResource(R.string.create_new),
            buttonColours = ButtonColours.Secondary,
            iconRes = DR.drawable.ic_add
        ) {
            onViewAction(HomeViewAction.OnClickCreateNew)
        }
    }
}
