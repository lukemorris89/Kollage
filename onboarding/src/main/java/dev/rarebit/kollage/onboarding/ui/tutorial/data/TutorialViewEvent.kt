package dev.rarebit.kollage.onboarding.ui.tutorial.data

import dev.rarebit.core.viewmodel.BaseViewEvent

sealed class TutorialViewEvent : BaseViewEvent() {
    data object NavigateBack : TutorialViewEvent()
    data object NavigateToNewCollage : TutorialViewEvent()
}
