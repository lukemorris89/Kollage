package dev.rarebit.kollage.onboarding.ui.tutorial

import dev.rarebit.core.viewmodel.BaseViewAction

sealed class TutorialViewAction : BaseViewAction() {
    data object OnClickBack : TutorialViewAction()
    data object OnClickPrimaryCta : TutorialViewAction()
    data class OnClickPage(val pageIndex: Int) : TutorialViewAction()
    data object OnClickSkipCta : TutorialViewAction()
}
