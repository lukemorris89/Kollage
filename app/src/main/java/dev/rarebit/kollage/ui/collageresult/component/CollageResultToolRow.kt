package dev.rarebit.kollage.ui.collageresult.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import dev.rarebit.design.component.tools.CollageTool
import dev.rarebit.design.component.tools.PrimaryToolRow
import dev.rarebit.design.component.tools.SecondaryToolRow
import dev.rarebit.design.component.tools.Tool
import dev.rarebit.kollage.ui.collageresult.CollageResultViewAction
import dev.rarebit.kollage.ui.collageresult.component.secondarytools.BackgroundSelectionRowContent
import dev.rarebit.kollage.ui.collageresult.component.secondarytools.ImageFormatRowContent
import dev.rarebit.kollage.ui.collageresult.data.CollageResultViewData
import kotlinx.collections.immutable.persistentListOf
import dev.rarebit.design.R as DR

@Composable
fun CollageResultToolRow(
    viewData: CollageResultViewData,
    onViewAction: (CollageResultViewAction) -> Unit,
) {
    val buttons = persistentListOf(
        Tool(
            iconRes = DR.drawable.ic_image_format,
            name = CollageTool.IMAGE_FORMAT,
            onClick = {
                onViewAction(CollageResultViewAction.OnToolClicked(CollageTool.IMAGE_FORMAT))
            }
        ),
        Tool(
            iconRes = DR.drawable.ic_background,
            name = CollageTool.BACKGROUND,
            onClick = {
                onViewAction(CollageResultViewAction.OnToolClicked(CollageTool.BACKGROUND))
            }
        ),
        Tool(
            iconRes = DR.drawable.ic_save,
            name = CollageTool.SAVE,
            onClick = {
                onViewAction(CollageResultViewAction.OnSaveClicked)
            }
        ),
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(
                RoundedCornerShape(32.dp)
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        AnimatedVisibility(
            visible = viewData.isToolbarExpanded,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            when (viewData.selectedTool) {
                CollageTool.IMAGE_FORMAT -> {
                    ImageFormatRowContent(
                        viewData = viewData,
                        onViewAction = onViewAction,
                    )
                }

                CollageTool.BACKGROUND -> {
                    BackgroundSelectionRowContent(
                        viewData = viewData,
                        onViewAction = onViewAction,
                    )
                }

                else -> Unit
            }

        }
        PrimaryToolRow(
            buttons = buttons,
            isExpanded = false,
            selectedTool = if (viewData.selectedTool == CollageTool.SAVE) {
                null
            } else {
                viewData.selectedTool
            },
        )
    }
}