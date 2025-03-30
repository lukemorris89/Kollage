package dev.rarebit.kollage.onboarding.ui.permissions.data

import dev.rarebit.core.viewmodel.BaseViewData

data class PermissionsViewData(
    val title: String,
    val description: String,
    val ctaLabel: String,
    val showPermissionRationaleBottomsheet: Boolean,
    val permissionRationaleDescription: String,
    val permissionRationalePrimaryCtaLabel: String,
    val permissionRationaleSecondaryCtaLabel: String,
    val showPermissionDeniedBottomsheet: Boolean,
    val permissionDeniedTitle: String,
    val permissionDeniedDescription: String,
    val permissionDeniedPrimaryCtaLabel: String,
    val permissionDeniedSecondaryCtaLabel: String,
) : BaseViewData()
