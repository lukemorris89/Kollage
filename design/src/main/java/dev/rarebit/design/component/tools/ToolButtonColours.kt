package dev.rarebit.design.component.tools

import androidx.compose.ui.graphics.Color
import dev.rarebit.design.theme.LightGrey
import dev.rarebit.design.theme.White

data class ToolButtonColours(
    val selectedButtonBackgroundColour: Color,
    val unselectedButtonBackgroundColour: Color,
) {
    companion object {
        fun primaryButtonColours() = ToolButtonColours(
            selectedButtonBackgroundColour = White,
            unselectedButtonBackgroundColour = LightGrey
        )

        fun secondaryButtonColours() = ToolButtonColours(
            selectedButtonBackgroundColour = LightGrey,
            unselectedButtonBackgroundColour = White
        )
    }
}
