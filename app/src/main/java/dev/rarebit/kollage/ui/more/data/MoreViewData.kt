package dev.rarebit.kollage.ui.more.data

import dev.rarebit.core.viewmodel.BaseViewData

data class MoreViewData(
    val title: String,
    val legalTitle: String,
    val termsAndConditionsText: String,
    val privacyPolicyText: String,
    val settingsTitle: String,
    val createdByText: String,
    val reviewPermissionsText: String,
) : BaseViewData()
