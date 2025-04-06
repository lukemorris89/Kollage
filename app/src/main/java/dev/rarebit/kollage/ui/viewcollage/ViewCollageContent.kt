package dev.rarebit.kollage.ui.viewcollage

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import dev.rarebit.design.theme.Black
import dev.rarebit.design.theme.White
import dev.rarebit.kollage.R
import dev.rarebit.kollage.ui.viewcollage.component.ConfirmDeleteDialog
import dev.rarebit.kollage.ui.viewcollage.data.ViewCollageViewData
import dev.rarebit.design.R as DR

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewCollageContent(
    viewData: ViewCollageViewData,
    onViewAction: (ViewCollageViewAction) -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding(),
        topBar = {
            AnimatedVisibility(
                visible = viewData.showTopAppBar,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                onViewAction(ViewCollageViewAction.OnBackPressed)
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = DR.drawable.ic_back),
                                contentDescription = null,
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                onViewAction(ViewCollageViewAction.OnClickMenu)
                            }
                        ) {
                            Icon(
                                painterResource(id = DR.drawable.ic_menu),
                                tint = White,
                                contentDescription = null,
                            )
                        }
                        DropdownMenu(
                            expanded = viewData.showMenuDropdown,
                            onDismissRequest = {
                                onViewAction(ViewCollageViewAction.OnClickMenu)
                            },
                            containerColor = White,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = stringResource(R.string.share),
                                        style = MaterialTheme.typography.labelLarge.copy(
                                            color = Black,
                                        )
                                    )
                                },
                                onClick = {
                                    onViewAction(ViewCollageViewAction.OnClickShare)
                                }
                            )
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = stringResource(R.string.delete),
                                        style = MaterialTheme.typography.labelLarge.copy(
                                            color = Black,
                                        )
                                    )
                                },
                                onClick = {
                                    onViewAction(ViewCollageViewAction.OnClickDelete)
                                }
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Black.copy(alpha = 0.5f),
                        navigationIconContentColor = White,
                    )
                )
            }
        }
    ) { contentPadding ->
        AsyncImage(
            model = viewData.collage.uri,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        if (viewData.showDeleteDialog) {
            ConfirmDeleteDialog(
                viewData = viewData,
                onViewAction = onViewAction
            )
        }
    }
}