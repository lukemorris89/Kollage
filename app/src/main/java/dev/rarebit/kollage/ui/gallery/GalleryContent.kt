package dev.rarebit.kollage.ui.gallery

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.rarebit.design.component.VerticalSpacer
import dev.rarebit.design.theme.Black
import dev.rarebit.design.theme.LightGrey
import dev.rarebit.design.theme.White
import dev.rarebit.design.theme.paddingLarge
import dev.rarebit.design.theme.paddingMedium
import dev.rarebit.design.theme.paddingSmall
import dev.rarebit.kollage.ui.gallery.data.GalleryViewData
import dev.rarebit.design.R as DR

@Composable
fun GalleryContent(
    viewData: GalleryViewData,
    onViewAction: (GalleryViewAction) -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        floatingActionButton = {
            if (!viewData.isEmptyGallery) {
                FloatingActionButton(
                    modifier = Modifier
                        .padding(end = paddingSmall),
                    containerColor = White,
                    contentColor = Black,
                    shape = CircleShape,
                    onClick = {
                        onViewAction(GalleryViewAction.OnClickCreateNew)
                    }
                ) {
                    Icon(
                        painter = painterResource(id = DR.drawable.ic_add),
                        contentDescription = null,
                    )
                }
            }
        },
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingLarge)
                .padding(contentPadding),
        ) {
            Text(
                text = viewData.title,
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
            if (viewData.isEmptyGallery) {
                EmptyGalleryContent(
                    viewData = viewData,
                    onViewAction = onViewAction,
                )
            }
        }
    }
}
