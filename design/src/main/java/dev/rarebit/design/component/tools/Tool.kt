package dev.rarebit.design.component.tools

import androidx.annotation.DrawableRes

data class Tool(
    @DrawableRes val iconRes: Int,
    val name: CollageTool,
    val enabled: Boolean = true,
    val onClick: () -> Unit,
)

enum class CollageTool {
    UNDO,
    SWITCH_CAMERA,
    EDIT,
    DONE,
    SHAPE,
    ALPHA,
    FILTER,
    COLOUR,
}