package dev.rarebit.kollage.ui.more

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.rarebit.design.component.VerticalSpacer
import dev.rarebit.design.component.WeightSpacer
import dev.rarebit.design.theme.LightGrey
import dev.rarebit.design.theme.White
import dev.rarebit.design.theme.paddingLarge
import dev.rarebit.design.theme.paddingMedium
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
            Row(
                modifier = Modifier.height(64.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = viewData.title,
                    style = MaterialTheme.typography.headlineLarge.copy(
                        color = White
                    )
                )
            }
            VerticalSpacer(paddingLarge)
            MoreButton(
                text = viewData.reviewPermissionsText
            ) {
                onViewAction(MoreViewAction.OnClickReviewPermissions)
            }
            VerticalSpacer(paddingMedium)
            MoreButton(
                text = viewData.viewTutorialText
            ) {
                onViewAction(MoreViewAction.OnClickViewTutorial)
            }
            VerticalSpacer(paddingLarge)
            Text(
                text = viewData.legalTitle,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = White
                )
            )
            VerticalSpacer(paddingMedium)
            MoreButton(
                text = viewData.privacyPolicyText
            ) {
                onViewAction(MoreViewAction.OnClickPrivacyPolicy)
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
