package dev.rarebit.kollage.ui.more.data

import dev.rarebit.core.viewmodel.BaseViewData

data class MoreViewData(
    val title: String,
    val termsAndConditionsText: String,
    val privacyPolicyText: String,
    val createdByText: String,
) : BaseViewData()
