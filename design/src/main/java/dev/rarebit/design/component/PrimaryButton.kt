package dev.rarebit.design.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.rarebit.design.theme.Black
import dev.rarebit.design.theme.KollageTheme
import dev.rarebit.design.theme.White

@Composable
fun PrimaryButton(
    text: String,
    @DrawableRes iconRes: Int? = null,
    buttonColours: ButtonColours = ButtonColours.Primary,
    contentDescription: String? = null,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColours.containerColour,
            contentColor = buttonColours.contentColour,
            disabledContainerColor = buttonColours.containerColour.copy(alpha = 0.5f),
            disabledContentColor = buttonColours.contentColour.copy(alpha = 0.5f),
        ),
        border = BorderStroke(
            width = 1.dp,
            color = buttonColours.borderColour,
        ),
        elevation = ButtonDefaults.buttonElevation()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            iconRes?.let {
                Icon(
                    painter = painterResource(id = it),
                    contentDescription = contentDescription,
                )
                HorizontalSpacer(12.dp)
            }
            Text(
                text = text,
            )
        }
    }
}

enum class ButtonColours(
    val containerColour: Color,
    val contentColour: Color,
    val borderColour: Color
) {
    Primary(
        containerColour = Black,
        contentColour = White,
        borderColour = Black,
    ),
    PrimaryOutlined(
        containerColour = Black,
        contentColour = White,
        borderColour = White,
    ),
    Secondary(
        containerColour = White,
        contentColour = Black,
        borderColour = White,
    ),
    SecondaryOutlined(
        containerColour = White,
        contentColour = Black,
        borderColour = Black,
    ),
    PrimaryGhost(
        containerColour = Color.Transparent,
        contentColour = White,
        borderColour = Color.Transparent,
    ),
    SecondaryGhost(
        containerColour = Color.Transparent,
        contentColour = Black,
        borderColour = Color.Transparent,
    ),
    PrimaryGhostOutlined(
        containerColour = Color.Transparent,
        contentColour = White,
        borderColour = White,
    ),
    SecondaryGhostOutlined(
        containerColour = Color.Transparent,
        contentColour = Black,
        borderColour = Black,
    )
}

@Preview
@Composable
private fun PrimaryButtonPreview() {
    KollageTheme {
        Column {
            PrimaryButton(text = "Click me!", buttonColours = ButtonColours.Primary) { }
            PrimaryButton(text = "Click me!", buttonColours = ButtonColours.PrimaryOutlined) { }
            PrimaryButton(text = "Click me!", buttonColours = ButtonColours.Secondary) { }
            PrimaryButton(text = "Click me!", buttonColours = ButtonColours.SecondaryOutlined) { }
            PrimaryButton(text = "Click me!", buttonColours = ButtonColours.PrimaryGhost) { }
            PrimaryButton(text = "Click me!", buttonColours = ButtonColours.SecondaryGhost) { }
            PrimaryButton(
                text = "Click me!",
                buttonColours = ButtonColours.PrimaryGhostOutlined
            ) { }
            PrimaryButton(
                text = "Click me!",
                buttonColours = ButtonColours.SecondaryGhostOutlined
            ) { }
        }
    }
}
