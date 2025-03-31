package dev.rarebit.kollage.ui.createcollage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.rarebit.design.component.ButtonHeight
import dev.rarebit.design.theme.Black
import dev.rarebit.design.theme.DarkGrey
import dev.rarebit.design.theme.KollageTheme
import dev.rarebit.design.theme.LightGrey
import dev.rarebit.design.theme.White
import dev.rarebit.kollage.ui.createcollage.CreateCollageViewAction
import dev.rarebit.kollage.ui.createcollage.data.CreateCollageViewData
import kotlinx.collections.immutable.persistentListOf
import dev.rarebit.design.R as DR

@Composable
fun CollageToolRow(
    modifier: Modifier = Modifier,
    viewData: CreateCollageViewData,
    onViewAction: (CreateCollageViewAction) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(ButtonHeight.Medium.height)
            .clip(
                RoundedCornerShape(100.dp))
            .background(if (viewData.selectedButton == null) {
                White
            } else {
                LightGrey
            }),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Top,
    ) {
        viewData.primaryButtons.forEach {
            val isSelected = it == viewData.selectedButton
            Box(
                modifier = Modifier
                    .width(54.dp)
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
                            viewData.selectedButton == null -> White
                            isSelected -> White
                            else -> LightGrey
                        }
                    ).then(if (isSelected) {
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
                    } else Modifier),

                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = {
                        onViewAction(CreateCollageViewAction.OnCollageToolButtonClicked(it))
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

@Preview
@Composable
private fun CollageToolRowPreview() {
    KollageTheme {
        Surface {
            CollageToolRow(
                viewData = CreateCollageViewData(
                    primaryButtons = persistentListOf(
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
                    ),
                    selectedButton = null
                ),
                onViewAction = {}
            )
        }
    }
}

@Preview
@Composable
private fun CollageToolRowSelectedPreview() {
    KollageTheme {
        Surface {
            val buttons = persistentListOf(
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
            CollageToolRow(
                viewData = CreateCollageViewData(
                    primaryButtons = buttons,
                    selectedButton = buttons[1]
                ),
                onViewAction = {}
            )
        }
    }
}