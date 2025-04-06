package dev.rarebit.kollage.ui.gallery.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import dev.rarebit.design.theme.Black
import dev.rarebit.design.theme.White
import dev.rarebit.kollage.R
import dev.rarebit.kollage.ui.gallery.GalleryViewAction
import dev.rarebit.kollage.ui.gallery.data.GalleryViewData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmDeleteDialog(
    viewData: GalleryViewData,
    onViewAction: (GalleryViewAction) -> Unit
) {
    AlertDialog(
        containerColor = White,
        titleContentColor = Black,
        textContentColor = Black,
        onDismissRequest = {
            onViewAction(GalleryViewAction.OnDismissConfirmDeleteDialog)
        },
        title = {
            Text(
                text = viewData.deleteDialogTitle,
                style = MaterialTheme.typography.titleLarge,
            )
        },
        text = {
            Text(text = viewData.deleteDialogDescription)
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onViewAction(GalleryViewAction.OnConfirmDelete)
                }
            ) {
                Text(
                    text = stringResource(R.string.confirm),
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = MaterialTheme.colorScheme.error,
                    ),
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onViewAction(GalleryViewAction.OnDismissConfirmDeleteDialog)
                }
            ) {
                Text(
                    text = stringResource(R.string.cancel),
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = Black,
                    ),
                )
            }
        }
    )
}
