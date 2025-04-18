package dev.rarebit.kollage.ui.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.rarebit.design.component.ButtonColours
import dev.rarebit.design.component.PrimaryButton
import dev.rarebit.design.component.VerticalSpacer
import dev.rarebit.design.component.WeightSpacer
import dev.rarebit.design.modifier.regularScreen
import dev.rarebit.design.theme.White
import dev.rarebit.design.theme.paddingLarge
import dev.rarebit.kollage.ui.welcome.data.WelcomeViewData

@Composable
fun WelcomeContent(
    viewData: WelcomeViewData,
    onViewAction: (WelcomeViewAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .regularScreen(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        WeightSpacer(1f)
        Text(
            text = viewData.title,
            style = MaterialTheme.typography.headlineLarge.copy(
                color = White,
            ),
        )
        WeightSpacer(1f)
        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            buttonColours = ButtonColours.Secondary,
            text = viewData.ctaLabel,
            onClick = { onViewAction(WelcomeViewAction.OnClickPrimaryCta) },
        )
        VerticalSpacer(paddingLarge)
    }
}
