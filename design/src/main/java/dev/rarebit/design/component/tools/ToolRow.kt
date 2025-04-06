package dev.rarebit.design.component.tools

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.rarebit.design.theme.LightGrey
import dev.rarebit.design.theme.White
import kotlinx.collections.immutable.PersistentList

@Composable
fun PrimaryToolRow(
    buttons: PersistentList<Tool>,
    isExpanded: Boolean,
    selectedTool: CollageTool?,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (!isExpanded && selectedTool == null) {
                    White
                } else {
                    LightGrey
                }
            ),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        buttons.forEach {
            val isSelected = it.name == selectedTool
            ToolButton(
                tool = it,
                selected = isSelected,
                colours = if (!isExpanded && selectedTool == null) {
                    ToolButtonColours.secondaryButtonColours()
                } else {
                    ToolButtonColours.primaryButtonColours()
                },
            )
        }
    }
}

@Composable
fun SecondaryToolRow(
    buttons: PersistentList<Tool>,
    selectedTool: CollageTool?,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(White),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        buttons.forEach {
            val isSelected = it.name == selectedTool
            ToolButton(
                tool = it,
                selected = isSelected,
                colours = ToolButtonColours.secondaryButtonColours(),
            )
        }
    }
}
