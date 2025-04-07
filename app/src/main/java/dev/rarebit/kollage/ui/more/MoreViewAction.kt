package dev.rarebit.kollage.ui.more

import dev.rarebit.core.viewmodel.BaseViewAction

sealed class MoreViewAction : BaseViewAction() {
    data object OnClickReviewPermissions : MoreViewAction()
    data object OnClickPrivacyPolicy : MoreViewAction()
    data object OnClickViewTutorial : MoreViewAction()
}
