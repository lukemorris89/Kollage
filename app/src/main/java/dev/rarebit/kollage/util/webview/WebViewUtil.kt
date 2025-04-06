package dev.rarebit.kollage.util.webview

import androidx.navigation.NavHostController
import dev.rarebit.kollage.navigation.AppRoute

fun navigateWebView(navController: NavHostController, url: String) {
    navController.navigate(AppRoute.WebView(url))
}
