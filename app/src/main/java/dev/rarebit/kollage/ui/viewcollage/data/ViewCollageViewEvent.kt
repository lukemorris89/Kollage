package dev.rarebit.kollage.ui.viewcollage.data

import android.net.Uri
import dev.rarebit.core.viewmodel.BaseViewEvent

sealed class ViewCollageViewEvent : BaseViewEvent() {
    data object NavigateBack : ViewCollageViewEvent()
    data class ShowShareSheet(val uri: Uri) : ViewCollageViewEvent()
}
