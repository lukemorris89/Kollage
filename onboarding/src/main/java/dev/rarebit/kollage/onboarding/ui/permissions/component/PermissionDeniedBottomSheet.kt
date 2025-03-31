package dev.rarebit.kollage.onboarding.ui.permissions.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialog
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialogProperties
import com.holix.android.bottomsheetdialog.compose.NavigationBarProperties
import dev.rarebit.design.component.ButtonColours
import dev.rarebit.design.component.PrimaryButton
import dev.rarebit.design.component.VerticalSpacer
import dev.rarebit.design.modifier.bottomSheetShape
import dev.rarebit.design.theme.Black
import dev.rarebit.design.theme.White
import dev.rarebit.design.theme.paddingLarge
import dev.rarebit.design.theme.paddingSmall
import dev.rarebit.kollage.onboarding.ui.permissions.PermissionsViewAction
import dev.rarebit.kollage.onboarding.ui.permissions.data.PermissionsViewData

@Composable
fun PermissionDeniedBottomsheet(
    viewData: PermissionsViewData,
    onViewAction: (PermissionsViewAction) -> Unit
) {
    BottomSheetDialog(
        onDismissRequest = {
            onViewAction(
                PermissionsViewAction.OnTogglePermissionDeniedBottomsheet(
                    false
                )
            )
        },
        properties = BottomSheetDialogProperties(
            navigationBarProperties = NavigationBarProperties(
                color = White,
            ),
        ),
        content = {
            PermissionDeniedBottomsheetContent(viewData, onViewAction)
        },
    )
}

@Composable
private fun PermissionDeniedBottomsheetContent(
    viewData: PermissionsViewData,
    onViewAction: (PermissionsViewAction) -> Unit
) {
    Column(
        modifier = Modifier
            .bottomSheetShape(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = viewData.permissionDeniedTitle,
            style = MaterialTheme.typography.titleLarge,
            color = Black,
        )
        VerticalSpacer(paddingLarge)
        Text(
            text = viewData.permissionRationaleDescription,
            style = MaterialTheme.typography.bodyLarge.copy(
                textAlign = TextAlign.Center,
                color = Black,
            ),
        )
        VerticalSpacer(paddingLarge)
        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = viewData.permissionDeniedPrimaryCtaLabel,
            buttonColours = ButtonColours.Primary,
            onClick = {
                onViewAction(PermissionsViewAction.OnClickPermissionDeniedPrimaryCta)
            }
        )
        VerticalSpacer(paddingSmall)
        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = viewData.permissionDeniedSecondaryCtaLabel,
            buttonColours = ButtonColours.SecondaryOutlined,
            onClick = {
                onViewAction(PermissionsViewAction.OnClickPermissionDeniedSecondaryCta)
            }
        )
        VerticalSpacer(paddingSmall)
    }
}
