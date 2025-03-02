package dev.rarebit.test_utils

import app.cash.turbine.TurbineTestContext
import app.cash.turbine.test
import dev.rarebit.core.view.ViewDataResource
import dev.rarebit.core.view.ViewEvent
import dev.rarebit.core.viewmodel.BaseViewData
import dev.rarebit.core.viewmodel.BaseViewEvent
import dev.rarebit.core.viewmodel.BaseViewModel
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals

fun <T : BaseViewModel<*, *>> runVMTest(vm: T, block: suspend context(T) TestScope.() -> Unit) = runTest {
    with(vm) {
        block(this@runTest)
    }
}

fun <T> assertVDData(actual: ViewDataResource<T>) = assertVDData(null, actual)

fun <T> assertVDData(expected: T?, actual: ViewDataResource<T>) {
    assertEquals(ViewDataResource.Data(expected), actual)
}

suspend fun <T> TurbineTestContext<T>.assertVDData() = assertVDData(null)

suspend fun <T, D> TurbineTestContext<T>.assertVDData(expected: D?) {
    assertEquals(ViewDataResource.Data(expected), awaitItem())
}

suspend fun <VD : BaseViewData, VE : BaseViewEvent> BaseViewModel<VD, VE>.assertVDData() = assertVDData(null)

suspend fun <D, VD : BaseViewData, VE : BaseViewEvent> BaseViewModel<VD, VE>.assertVDData(expected: D?) {
    viewData?.test {
        assertVDData(expected)
    }
}

fun <T> assertVDLoading(actual: ViewDataResource<T>) = assertVDLoading(null, actual)

fun <T> assertVDLoading(expected: T?, actual: ViewDataResource<T>) {
    assertEquals(ViewDataResource.Loading(expected), actual)
}

suspend fun <T> TurbineTestContext<T>.assertVDLoading() = assertVDLoading(null)

suspend fun <T> TurbineTestContext<T>.assertVDLoading(expected: T?) {
    assertEquals(ViewDataResource.Loading(expected), awaitItem())
}

suspend fun <VD : BaseViewData, VE : BaseViewEvent> BaseViewModel<VD, VE>.assertVDLoading() = assertVDLoading(null)

suspend fun <VD : BaseViewData, VE : BaseViewEvent> BaseViewModel<VD, VE>.assertVDLoading(expected: VD?) {
    viewData?.test {
        assertVDLoading(expected)
    }
}

fun <T> assertVDError(actual: ViewDataResource<T>) = assertVDError(null, actual)

fun <T> assertVDError(expected: T?, actual: ViewDataResource<T>) {
    assertEquals(ViewDataResource.Error(expected), actual)
}

suspend fun <T> TurbineTestContext<T>.assertVDError() = assertVDError(null)

suspend fun <T> TurbineTestContext<T>.assertVDError(expected: T?) {
    assertEquals(ViewDataResource.Error(expected), awaitItem())
}

suspend fun <VD : BaseViewData, VE : BaseViewEvent> BaseViewModel<VD, VE>.assertVDError() = assertVDError(null)

suspend fun <VD : BaseViewData, VE : BaseViewEvent> BaseViewModel<VD, VE>.assertVDError(expected: VD?) {
    viewData?.test {
        assertVDError(expected)
    }
}

fun <T> assertVEvent(expected: T, actual: ViewEvent<T>) {
    assertEquals(ViewEvent(expected), actual)
}

suspend fun <T, E> TurbineTestContext<T>.assertVEvent(expected: E) {
    assertEquals(ViewEvent(expected), awaitItem())
}

suspend fun <VD : BaseViewData, VE : BaseViewEvent> BaseViewModel<VD, VE>.assertVEvent(expected: VE) {
    viewEvent?.test {
        assertVEvent(expected)
    }
}