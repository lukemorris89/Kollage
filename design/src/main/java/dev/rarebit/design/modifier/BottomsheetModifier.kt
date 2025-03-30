package dev.rarebit.design.modifier

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import dev.rarebit.design.theme.White
import dev.rarebit.design.theme.paddingLarge

fun Modifier.bottomSheetShape() = this.then(
    Modifier
        .clip(RoundedCornerShape(topStart = paddingLarge, topEnd = paddingLarge))
        .background(White)
        .padding(
            start = paddingLarge,
            end = paddingLarge,
            top = 32.dp,
            bottom = 8.dp,
        )
        .navigationBarsPadding()
)
