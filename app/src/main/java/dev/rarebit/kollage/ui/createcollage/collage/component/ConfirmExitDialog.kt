package dev.rarebit.kollage.ui.createcollage.collage.component

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
import dev.rarebit.kollage.ui.createcollage.CreateCollageViewAction
import dev.rarebit.kollage.ui.createcollage.data.CreateCollageViewData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmExitDialog(
    viewData: CreateCollageViewData,
    onViewAction: (CreateCollageViewAction) -> Unit
) {
    AlertDialog(
        containerColor = White,
        titleContentColor = Black,
        textContentColor = Black,
        onDismissRequest = {
            onViewAction(CreateCollageViewAction.OnDismissConfirmExitDialog)
        },
        title = {
            Text(
                text = viewData.exitDialogTitle,
                style = MaterialTheme.typography.titleLarge,
            )
        },
        text = {
            Text(text = viewData.exitDialogDescription)
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onViewAction(CreateCollageViewAction.OnConfirmExit)
                }
            ) {
                Text(
                    text = stringResource(R.string.exit),
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = MaterialTheme.colorScheme.error,
                    ),
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onViewAction(CreateCollageViewAction.OnDismissConfirmExitDialog)
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
