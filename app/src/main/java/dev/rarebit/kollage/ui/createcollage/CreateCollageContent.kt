package dev.rarebit.kollage.ui.createcollage

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import dev.rarebit.design.component.VerticalSpacer
import dev.rarebit.design.modifier.regularScreen
import dev.rarebit.design.theme.Black
import dev.rarebit.design.theme.White
import dev.rarebit.design.theme.paddingLarge
import dev.rarebit.kollage.ui.createcollage.camera.CameraContent
import dev.rarebit.kollage.ui.createcollage.component.CollageTool
import dev.rarebit.kollage.ui.createcollage.component.CollageToolRow
import dev.rarebit.kollage.ui.createcollage.component.SecondaryToolRow
import dev.rarebit.kollage.ui.createcollage.component.secondarytools.AlphaRowContent
import dev.rarebit.kollage.ui.createcollage.component.secondarytools.ColourRowContent
import dev.rarebit.kollage.ui.createcollage.component.secondarytools.CropShapeRowContent
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
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .regularScreen()
                .padding(contentPadding)
                .safeDrawingPadding(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AnimatedVisibility(
                visible = viewData.showSecondaryToolOptions,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                SecondaryToolRow(
                    content = {
                        viewData.selectedSecondaryTool?.let {
                            when (it.name) {
                                CollageTool.SHAPE ->
                                    CropShapeRowContent(
                                        viewData = viewData,
                                        onViewAction = onViewAction,
                                    )

                                CollageTool.ALPHA ->
                                    AlphaRowContent(
                                        viewData = viewData,
                                        onViewAction = onViewAction,
                                    )

                                CollageTool.COLOUR ->
                                    ColourRowContent(
                                        viewData = viewData,
                                        onViewAction = onViewAction,
                                    )

                                else -> {}
                            }
                        }
                    }
                )
            }
            VerticalSpacer(paddingLarge)
            CollageToolRow(
                viewData = viewData,
                onViewAction = onViewAction,
            )
        }
    }
}
