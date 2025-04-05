package dev.rarebit.kollage.ui.collageresult.component

import androidx.compose.runtime.Composable
import dev.rarebit.design.component.tools.CollageTool
import dev.rarebit.design.component.tools.PrimaryToolRow
import dev.rarebit.design.component.tools.Tool
import dev.rarebit.kollage.ui.collageresult.CollageResultViewAction
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