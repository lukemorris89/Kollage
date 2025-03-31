package dev.rarebit.kollage.ui.more

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.rarebit.design.component.VerticalSpacer
import dev.rarebit.design.component.WeightSpacer
import dev.rarebit.design.theme.LightGrey
import dev.rarebit.design.theme.White
import dev.rarebit.design.theme.paddingLarge
import dev.rarebit.design.theme.paddingMedium
import dev.rarebit.design.theme.paddingSmall
import dev.rarebit.kollage.ui.more.component.MoreButton
import dev.rarebit.kollage.ui.more.data.MoreViewData

@Composable
fun MoreContent(
    viewData: MoreViewData,
    onViewAction: (MoreViewAction) -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingLarge)
                .padding(contentPadding),
        ) {
            Text(
                text = viewData.title,
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = White
                )
            )
            VerticalSpacer(paddingSmall)
            HorizontalDivider(
                modifier = Modifier.padding(end = 64.dp),
                color = LightGrey,
                thickness = 1.dp
            )
            VerticalSpacer(paddingMedium)
            MoreButton(
                text = viewData.termsAndConditionsText
            ) {
                // TODO add click to t&cs
            }
            VerticalSpacer(paddingMedium)
            MoreButton(
                text = viewData.privacyPolicyText
            ) {
                // TODO add click to privacy policy
            }
            VerticalSpacer(paddingLarge)
            WeightSpacer(1f)
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = viewData.createdByText,
                style = MaterialTheme.typography.bodyMedium.copy(
                    textAlign = TextAlign.Center,
                    color = LightGrey
                )
            )
        }
    }
}
