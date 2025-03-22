package dev.rarebit.kollage.navigation

import kotlinx.serialization.Serializable

sealed interface AppRoute {
    @Serializable
    data object Welcome : AppRoute

    @Serializable
    data object Permissions : AppRoute

    @Serializable
    data object Home : AppRoute
}
