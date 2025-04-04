package dev.rarebit.kollage.ui.createcollage.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.rarebit.design.theme.Black
import dev.rarebit.design.theme.DarkGrey
import dev.rarebit.design.theme.LightGrey
import dev.rarebit.design.theme.White
import dev.rarebit.kollage.ui.createcollage.CreateCollageViewAction
import dev.rarebit.kollage.ui.createcollage.data.CreateCollageViewData
import kotlinx.collections.immutable.persistentListOf
import dev.rarebit.design.R as DR

@Composable
fun CollageToolRow(
    viewData: CreateCollageViewData,
    modifier: Modifier = Modifier,
    onViewAction: (CreateCollageViewAction) -> Unit,
) {
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
            ExpandedToolRow(
                viewData = viewData,
                onViewAction = onViewAction,
            )
        }
        CollapsedToolRow(
            viewData = viewData,
            onViewAction = onViewAction,
        )
    }
}

@Suppress("CyclomaticComplexMethod")
@Composable
private fun CollapsedToolRow(
    viewData: CreateCollageViewData,
    onViewAction: (CreateCollageViewAction) -> Unit,
) {
    val primaryButtons = persistentListOf(
        CollageToolButton(
            iconRes = DR.drawable.ic_undo,
            name = CollageTool.UNDO,
            enabled = viewData.undoEnabled,
            onClick = {
                onViewAction(CreateCollageViewAction.OnUndoCollageLayer)
            }
        ),
        CollageToolButton(
            iconRes = DR.drawable.ic_camera_switch,
            name = CollageTool.SWITCH_CAMERA,
            onClick = {
                onViewAction(CreateCollageViewAction.OnSwitchCamera)
            }
        ),
        CollageToolButton(
            iconRes = DR.drawable.ic_edit,
            name = CollageTool.EDIT,
            onClick = {
                onViewAction(CreateCollageViewAction.OnEditClicked)
            }
        ),
        CollageToolButton(
            iconRes = DR.drawable.ic_check,
            name = CollageTool.DONE,
            onClick = {
                onViewAction(CreateCollageViewAction.OnDoneClicked)
            }
        ),
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (!viewData.isToolbarExpanded && (viewData.selectedPrimaryTool == null || viewData.selectedPrimaryTool != CollageTool.EDIT)) {
                    White
                } else {
                    LightGrey
                }
            ),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        primaryButtons.forEach {
            val isSelected = it.name == viewData.selectedPrimaryTool && it.name == CollageTool.EDIT
            Box(
                modifier = Modifier
                    .width(54.dp)
                    .padding(bottom = 8.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 0.dp,
                            bottomStart = 100.dp,
                            bottomEnd = 100.dp
                        )
                    )
                    .background(
                        when {
                            viewData.selectedPrimaryTool == null || viewData.selectedPrimaryTool != CollageTool.EDIT -> White
                            isSelected -> White
                            else -> LightGrey
                        }
                    )
                    .then(
                        if (isSelected) {
                            Modifier.border(
                                width = 1.dp,
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        White,
                                        DarkGrey
                                    )
                                ),
                                shape = RoundedCornerShape(
                                    topStart = 0.dp,
                                    topEnd = 0.dp,
                                    bottomStart = 100.dp,
                                    bottomEnd = 100.dp
                                )
                            )
                        } else {
                            Modifier
                        }
                    )
                    .padding(top = 8.dp),

                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = {
                        when (it.name) {
                            CollageTool.UNDO -> onViewAction(CreateCollageViewAction.OnUndoCollageLayer)
                            CollageTool.SWITCH_CAMERA -> onViewAction(CreateCollageViewAction.OnSwitchCamera)
                            CollageTool.EDIT -> onViewAction(CreateCollageViewAction.OnEditClicked)
                            CollageTool.DONE -> onViewAction(CreateCollageViewAction.OnDoneClicked)
                            else -> Unit
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(id = it.iconRes),
                        tint = Black,
                        contentDescription = null,
                    )
                }
            }
        }
    }
}

@Composable
private fun ExpandedToolRow(
    viewData: CreateCollageViewData,
    onViewAction: (CreateCollageViewAction) -> Unit,
) {
    val secondaryButtons = persistentListOf(
        CollageToolButton(
            iconRes = DR.drawable.ic_shape,
            name = CollageTool.SHAPE,
            onClick = {
                onViewAction(CreateCollageViewAction.OnCropShapeClicked)
            }
        ),
        CollageToolButton(
            iconRes = DR.drawable.ic_alpha,
            name = CollageTool.ALPHA,
            onClick = {
                onViewAction(CreateCollageViewAction.OnAlphaClicked)
            }
        ),
        CollageToolButton(
            iconRes = DR.drawable.ic_filter,
            name = CollageTool.COLOUR,
            onClick = {
                onViewAction(CreateCollageViewAction.OnColourClicked)
            }
        ),
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(White),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        secondaryButtons.forEach {
            val isSelected = it.name == viewData.selectedSecondaryTool
            Box(
                modifier = Modifier
                    .width(54.dp)
                    .padding(bottom = 8.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 0.dp,
                            bottomStart = 100.dp,
                            bottomEnd = 100.dp
                        )
                    )
                    .background(
                        if (isSelected) {
                            LightGrey
                        } else {
                            White
                        }
                    )
                    .padding(top = 8.dp)
                    .then(
                        if (isSelected) {
                            Modifier.border(
                                width = 1.dp,
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        White,
                                        DarkGrey
                                    )
                                ),
                                shape = RoundedCornerShape(
                                    topStart = 0.dp,
                                    topEnd = 0.dp,
                                    bottomStart = 100.dp,
                                    bottomEnd = 100.dp
                                )
                            )
                        } else {
                            Modifier
                        }
                    ),

                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = {
                        when (it.name) {
                            CollageTool.SHAPE -> onViewAction(CreateCollageViewAction.OnCropShapeClicked)
                            CollageTool.ALPHA -> onViewAction(CreateCollageViewAction.OnAlphaClicked)
                            CollageTool.COLOUR -> onViewAction(CreateCollageViewAction.OnColourClicked)
                            else -> Unit
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(id = it.iconRes),
                        tint = Black,
                        contentDescription = null,
                    )
                }
            }
        }
    }
}
