package dev.rarebit.design.modifier

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.rarebit.design.theme.paddingLarge

@Composable
fun Modifier.regularScreen(
    paddingValues: PaddingValues = PaddingValues(paddingLarge),
) = this.then(Modifier.padding(paddingValues))
