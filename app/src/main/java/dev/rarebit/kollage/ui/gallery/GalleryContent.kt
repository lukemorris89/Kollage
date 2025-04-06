package dev.rarebit.kollage.ui.gallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.rarebit.design.component.VerticalSpacer
import dev.rarebit.design.theme.Black
import dev.rarebit.design.theme.White
import dev.rarebit.design.theme.paddingLarge
import dev.rarebit.design.theme.paddingMedium
import dev.rarebit.design.theme.paddingSmall
import dev.rarebit.kollage.R
import dev.rarebit.kollage.ui.gallery.component.CollageThumbnail
import dev.rarebit.kollage.ui.gallery.component.ConfirmDeleteDialog
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = viewData.title,
                    style = MaterialTheme.typography.headlineLarge.copy(
                        color = White
                    )
                )
                if (!viewData.isEmptyGallery) {
                    if (!viewData.isSelectMode) {
                        TextButton(
                            onClick = {
                                onViewAction(GalleryViewAction.OnClickSelect)
                            }
                        ) {
                            Text(
                                text = stringResource(R.string.select),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = White
                                )
                            )
                        }
                    } else {
                        Row {
                            if (viewData.selectedCollages.isNotEmpty()) {
                                IconButton(
                                    onClick = {
                                        onViewAction(GalleryViewAction.OnClickDelete)
                                    }
                                ) {
                                    Icon(
                                        painter = painterResource(DR.drawable.ic_delete),
                                        contentDescription = null
                                    )
                                }
                            }
                            IconButton(
                                onClick = {
                                    onViewAction(GalleryViewAction.OnClickSelect)
                                }
                            ) {
                                Icon(
                                    painter = painterResource(DR.drawable.ic_close),
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            }
            VerticalSpacer(paddingSmall)
//            HorizontalDivider(
//                modifier = Modifier.padding(end = 64.dp),
//                color = LightGrey,
//                thickness = 1.dp
//            )
            VerticalSpacer(paddingMedium)
            if (!viewData.isEmptyGallery) {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 100.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    content = {
                        viewData.collageList.forEach { group ->
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                Text(
                                    text = group.date,
                                    style = MaterialTheme.typography.titleMedium,
                                )
                            }
                            items(group.collages) { collage ->
                                CollageThumbnail(
                                    collage = collage,
                                    isSelectMode = viewData.isSelectMode,
                                    selected = viewData.selectedCollages.contains(collage),
                                    onClick = {
                                        if (!viewData.isSelectMode) {
                                            onViewAction(GalleryViewAction.OnClickThumbnail(collage))
                                        } else {
                                            onViewAction(
                                                GalleryViewAction.OnClickThumbnailSelectMode(
                                                    collage
                                                )
                                            )
                                        }
                                    },
                                    onLongPress = {
                                        onViewAction(GalleryViewAction.OnLongClickThumbnail)
                                    }
                                )
                            }
                        }
                    }
                )
            } else {
                EmptyGalleryContent(
                    viewData = viewData,
                    onViewAction = onViewAction,
                )
            }
        }
        if (viewData.showDeleteDialog) {
            ConfirmDeleteDialog(
                viewData = viewData,
                onViewAction = onViewAction
            )
        }
    }
}
