package dev.rarebit.kollage.ui.createcollage.collage.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import dev.rarebit.kollage.ui.createcollage.CreateCollageViewAction
import dev.rarebit.kollage.ui.createcollage.data.CreateCollageViewData
import kotlinx.collections.immutable.persistentListOf
import dev.rarebit.design.R as DR

@Composable
fun CollageToolRow(
    viewData: CreateCollageViewData,
    onCaptureCollage: () -> Unit,
    modifier: Modifier = Modifier,
    onViewAction: (CreateCollageViewAction) -> Unit,
) {
    val primaryButtons = persistentListOf(
        Tool(
            iconRes = DR.drawable.ic_undo,
            name = CollageTool.UNDO,
            enabled = viewData.isUndoEnabled,
            onClick = {
                onViewAction(CreateCollageViewAction.OnUndoCollageLayer)
            }
        ),
        Tool(
            iconRes = DR.drawable.ic_camera_switch,
            name = CollageTool.SWITCH_CAMERA,
            onClick = {
                onViewAction(CreateCollageViewAction.OnSwitchCamera)
            }
        ),
        Tool(
            iconRes = DR.drawable.ic_edit,
            name = CollageTool.EDIT,
            onClick = {
                onViewAction(CreateCollageViewAction.OnEditClicked)
            }
        ),
        Tool(
            iconRes = DR.drawable.ic_check,
            name = CollageTool.DONE,
            onClick = {
                onCaptureCollage()
            },

        ),
    )
    val secondaryButtons = persistentListOf(
        Tool(
            iconRes = DR.drawable.ic_shape,
            name = CollageTool.SHAPE,
            onClick = {
                onViewAction(CreateCollageViewAction.OnCropShapeClicked)
            }
        ),
        Tool(
            iconRes = DR.drawable.ic_filter,
            name = CollageTool.COLOUR,
            onClick = {
                onViewAction(CreateCollageViewAction.OnColourClicked)
            }
        ),
    )
    Column(
        modifier = modifier
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
            SecondaryToolRow(
                buttons = secondaryButtons,
                selectedTool = viewData.selectedSecondaryTool,
            )
        }
        PrimaryToolRow(
            buttons = primaryButtons,
            isExpanded = viewData.isToolbarExpanded,
            selectedTool = if (viewData.selectedPrimaryTool == CollageTool.EDIT) {
                viewData.selectedPrimaryTool
            } else {
                null
            },
        )
    }
}
