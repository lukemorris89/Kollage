package dev.rarebit.kollage.ui.collageresult

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
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
import dev.rarebit.design.component.tools.CollageTool
import dev.rarebit.design.component.tools.FloatingToolRow
import dev.rarebit.design.modifier.regularScreen
import dev.rarebit.design.theme.Black
import dev.rarebit.design.theme.White
import dev.rarebit.design.theme.paddingLarge
import dev.rarebit.kollage.ui.collageresult.component.CollageResultPreview
import dev.rarebit.kollage.ui.collageresult.component.CollageResultToolRow
import dev.rarebit.kollage.ui.collageresult.component.secondarytools.BackgroundColourRowContent
import dev.rarebit.kollage.ui.collageresult.component.secondarytools.BackgroundSelectionRowContent
import dev.rarebit.kollage.ui.collageresult.component.secondarytools.ImageFormatRowContent
import dev.rarebit.kollage.ui.collageresult.data.CollageResultViewData
import dev.rarebit.kollage.ui.createcollage.util.imageutil.BackgroundSelection
import dev.rarebit.design.R as DR


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollageResultContent(
    viewData: CollageResultViewData,
    onViewAction: (CollageResultViewAction) -> Unit,
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
                            onViewAction(
                                CollageResultViewAction.OnBackPressed)
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
        CollageResultPreview(
            viewData = viewData,
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
            FloatingToolRow(
                isVisible = viewData.showFloatingToolRow,
                content = {
                    BackgroundColourRowContent(
                        viewData = viewData,
                        onViewAction = onViewAction,
                    )
                }
            )
            VerticalSpacer(paddingLarge)
            CollageResultToolRow(
                viewData = viewData,
                onViewAction = onViewAction,
            )
        }
    }
}