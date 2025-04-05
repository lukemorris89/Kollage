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
    selectedTool: Tool?,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (!isExpanded && (selectedTool == null || selectedTool.name != CollageTool.EDIT)) {
                    White
                } else {
                    LightGrey
                }
            ),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        buttons.forEach {
            val isSelected = it == selectedTool && it.name == CollageTool.EDIT
            ToolButton(
                tool = it,
                selected = isSelected,
                colours = ToolButtonColours.primaryButtonColours(),
            )
        }
    }
}

@Composable
fun SecondaryToolRow(
    buttons: PersistentList<Tool>,
    selectedTool: Tool?,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(White),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        buttons.forEach {
            val isSelected = it == selectedTool
            ToolButton(
                tool = it,
                selected = isSelected,
                colours = ToolButtonColours.secondaryButtonColours(),
            )
        }
    }
}