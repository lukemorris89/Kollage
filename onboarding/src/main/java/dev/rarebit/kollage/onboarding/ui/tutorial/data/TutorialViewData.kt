package dev.rarebit.kollage.onboarding.ui.tutorial.data

import dev.rarebit.core.viewmodel.BaseViewData
import kotlinx.collections.immutable.PersistentList

data class TutorialViewData(
    val currentPageIndex: Int,
    val pages: PersistentList<TutorialPage>,
    val primaryCtaLabel: String,
    val skipCtaLabel: String,
    val showSkip: Boolean = true,
) : BaseViewData()

data class TutorialPage(
    val index: Int,
    val animationRes: Int? = null,
    val description: String,
)
