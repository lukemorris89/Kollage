package dev.rarebit.kollage.ui.welcome.data

import dev.rarebit.core.viewmodel.BaseViewData

data class WelcomeViewData(
    val title: String,
    val ctaLabel: String,
    val termsText: String,
) : BaseViewData()
