package dev.rarebit.kollage.ui.createcollage

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.rarebit.design.component.VerticalSpacer
import dev.rarebit.design.modifier.regularScreen
import dev.rarebit.design.theme.paddingLarge
import dev.rarebit.kollage.ui.createcollage.component.CollageToolRow
import dev.rarebit.kollage.ui.createcollage.component.SecondaryToolRow
import dev.rarebit.kollage.ui.createcollage.component.secondarytools.CropShapeRowContent
import dev.rarebit.kollage.ui.createcollage.data.CreateCollageViewData

@Composable
fun CreateCollageContent(
    viewData: CreateCollageViewData,
    onViewAction: (CreateCollageViewAction) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { contentPadding ->
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
                        CropShapeRowContent(
                            viewData = viewData,
                            onViewAction = onViewAction,
                        )
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
