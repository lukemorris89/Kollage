package dev.rarebit.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.rarebit.core.view.ViewEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.plus

abstract class BaseViewModel<BViewData : BaseViewData, BViewEvent : BaseViewEvent> : ViewModel() {
    abstract val viewData: StateFlow<BViewData>?
    abstract val viewEvent: SharedFlow<ViewEvent<BViewEvent>>?

    val backgroundScope: CoroutineScope
        get() = viewModelScope + Dispatchers.Default
}
