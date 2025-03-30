package dev.rarebit.kollage.ui.gallery

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.rarebit.design.component.VerticalSpacer
import dev.rarebit.design.theme.Black
import dev.rarebit.design.theme.LightGrey
import dev.rarebit.design.theme.White
import dev.rarebit.design.theme.paddingLarge
import dev.rarebit.design.theme.paddingMedium
import dev.rarebit.design.theme.paddingSmall
import dev.rarebit.kollage.R
import dev.rarebit.kollage.ui.home.HomeViewAction
import dev.rarebit.kollage.ui.home.data.HomeViewData

@Composable
fun GalleryContent(
    viewData: HomeViewData,
    onViewAction: (HomeViewAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
            .padding(paddingLarge)
    ) {
        Text(
            text = stringResource(id = R.string.gallery),
            style = MaterialTheme.typography.headlineLarge.copy(
                color = White
            )
        )
        VerticalSpacer(paddingSmall)
        HorizontalDivider(
            modifier = Modifier.padding(end = 64.dp),
            color = LightGrey,
            thickness = 1.dp
        )
        VerticalSpacer(paddingMedium)
        if (viewData.showEmptyGallery) {
            EmptyGalleryContent(
                viewData = viewData,
                onViewAction = onViewAction,
            )
        }
    }
}
