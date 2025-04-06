package dev.rarebit.kollage.ui.createcollage

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import dev.rarebit.design.component.LoadingIndicator
import dev.rarebit.design.theme.Black
import dev.rarebit.design.theme.White
import dev.rarebit.kollage.R
import dev.rarebit.kollage.ui.createcollage.camera.CameraContent
import dev.rarebit.kollage.ui.createcollage.collage.component.ConfirmExitDialog
import dev.rarebit.kollage.ui.createcollage.data.CreateCollageViewData
import dev.rarebit.design.R as DR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCollageContent(
    viewData: CreateCollageViewData,
    onViewAction: (CreateCollageViewAction) -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding(),
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onViewAction(CreateCollageViewAction.OnBackPressed)
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = DR.drawable.ic_back),
                            contentDescription = null,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Black.copy(alpha = 0.5f),
                    navigationIconContentColor = White,
                )
            )
        }
    ) { contentPadding ->
        CameraContent(
            viewData = viewData,
            onViewAction = onViewAction,
            contentPadding = contentPadding,
        )
        AnimatedVisibility(visible = viewData.isSaveLoading) {
            LoadingIndicator(
                text = stringResource(R.string.creating_kollage)
            )
        }
        if (viewData.showConfirmExitDialog) {
            ConfirmExitDialog(
                viewData = viewData,
                onViewAction = onViewAction
            )
        }
    }
}
