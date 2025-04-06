package dev.rarebit.kollage.navigation

import dev.rarebit.kollage.data.model.Collage
import kotlinx.serialization.Serializable

sealed interface AppRoute {
    @Serializable
    data object Welcome : AppRoute

    @Serializable
    data object Permissions : AppRoute

    @Serializable
    data object Home : AppRoute

    @Serializable
    data object Tutorial : AppRoute

    @Serializable
    data object CreateCollage : AppRoute

    @Serializable
    data object CollageResult : AppRoute

    @Serializable
    data class ViewCollage(val collage: Collage) : AppRoute

    @Serializable
    data class WebView(val url: String) : AppRoute
}
