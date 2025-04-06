package dev.rarebit.kollage.ui.permissions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.rarebit.design.component.ButtonColours
import dev.rarebit.design.component.PrimaryButton
import dev.rarebit.design.component.VerticalSpacer
import dev.rarebit.design.component.WeightSpacer
import dev.rarebit.design.modifier.regularScreen
import dev.rarebit.kollage.ui.permissions.component.PermissionDeniedBottomsheet
import dev.rarebit.kollage.ui.permissions.component.PermissionRationaleBottomsheet
import dev.rarebit.kollage.ui.permissions.data.PermissionsViewData

@Composable
fun PermissionsContent(
    viewData: PermissionsViewData,
    onViewAction: (PermissionsViewAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .regularScreen()
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        WeightSpacer(1f)
        Text(
            text = viewData.title,
            style = MaterialTheme.typography.headlineLarge,
        )
        VerticalSpacer(24.dp)
        Text(
            modifier = Modifier
                .padding(horizontal = 32.dp),
            text = viewData.description,
            style = MaterialTheme.typography.bodyMedium.copy(
                textAlign = TextAlign.Center,
            ),
        )
        WeightSpacer(1f)
        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            buttonColours = ButtonColours.Secondary,
            text = viewData.ctaLabel,
            onClick = { onViewAction(PermissionsViewAction.OnClickPrimaryCta) },
        )
    }

    if (viewData.showPermissionRationaleBottomsheet) {
        PermissionRationaleBottomsheet(
            viewData = viewData,
            onViewAction = onViewAction,
        )
    }

    if (viewData.showPermissionDeniedBottomsheet) {
        PermissionDeniedBottomsheet(
            viewData = viewData,
            onViewAction = onViewAction,
        )
    }
}
