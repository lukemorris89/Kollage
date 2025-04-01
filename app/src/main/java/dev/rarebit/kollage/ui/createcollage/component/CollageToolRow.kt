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
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.rarebit.design.theme.Black
import dev.rarebit.design.theme.DarkGrey
import dev.rarebit.design.theme.KollageTheme
import dev.rarebit.design.theme.LightGrey
import dev.rarebit.design.theme.White
import dev.rarebit.kollage.ui.createcollage.CreateCollageViewAction
import dev.rarebit.kollage.ui.createcollage.component.secondarytools.CropShape
import dev.rarebit.kollage.ui.createcollage.data.CreateCollageViewData
import kotlinx.collections.immutable.persistentListOf
import dev.rarebit.design.R as DR

@Suppress("CyclomaticComplexMethod")
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(White),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                viewData.secondaryToolButtons.forEach {
                    val isSelected = it == viewData.selectedSecondaryTool
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
                                onViewAction(
                                    CreateCollageViewAction.OnSecondaryToolButtonClicked(
                                        it
                                    )
                                )
                                it.onClick()
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    if (!viewData.isToolbarExpanded && (viewData.selectedPrimaryTool == null || !viewData.selectedPrimaryTool.hasSecondaryButtons)) {
                        White
                    } else {
                        LightGrey
                    }
                ),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            viewData.primaryToolButtons.forEach {
                val isSelected = it == viewData.selectedPrimaryTool && it.hasSecondaryButtons
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
                                viewData.selectedPrimaryTool == null || !viewData.selectedPrimaryTool.hasSecondaryButtons -> White
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
                            onViewAction(
                                CreateCollageViewAction.OnPrimaryToolButtonClicked(
                                    it
                                )
                            )
                            it.onClick()
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
}

@Preview
@Composable
private fun CollageToolRowPreview() {
    KollageTheme {
        Surface {
            CollageToolRow(
                viewData = previewViewData,
                onViewAction = {}
            )
        }
    }
}

@Preview
@Composable
private fun CollageToolRowPrimarySelectedPreview() {
    KollageTheme {
        Surface {
            CollageToolRow(
                viewData = previewViewData.copy(
                    selectedPrimaryTool = previewViewData.primaryToolButtons[1],
                    isToolbarExpanded = true,
                ),
                onViewAction = {}
            )
        }
    }
}

@Preview
@Composable
private fun CollageToolRowSecondarySelectedPreview() {
    KollageTheme {
        Surface {
            CollageToolRow(
                viewData = previewViewData.copy(
                    selectedPrimaryTool = previewViewData.primaryToolButtons[1],
                    selectedSecondaryTool = previewViewData.secondaryToolButtons[2],
                    isToolbarExpanded = true,
                ),
                onViewAction = {}
            )
        }
    }
}

private val previewButtons = persistentListOf(
    CollageToolButton(
        iconRes = DR.drawable.ic_add,
        onClick = {}
    ),
    CollageToolButton(
        iconRes = DR.drawable.ic_menu,
        onClick = {}
    ),
    CollageToolButton(
        iconRes = DR.drawable.ic_gallery,
        onClick = {}
    ),
    CollageToolButton(
        iconRes = DR.drawable.ic_back,
        onClick = {}
    ),
)

private val previewViewData = CreateCollageViewData(
    primaryToolButtons = previewButtons,
    secondaryToolButtons = previewButtons,
    selectedPrimaryTool = null,
    selectedSecondaryTool = null,
    isToolbarExpanded = false,
    selectedCropShape = CropShape.SQUARE,
    showSecondaryToolOptions = false,
)
