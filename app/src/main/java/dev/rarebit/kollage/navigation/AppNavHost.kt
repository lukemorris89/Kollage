package dev.rarebit.kollage.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.rarebit.kollage.onboarding.ui.welcome.WelcomeScreen

@Composable
fun AppNavHost(
    startDestination: AppRoute = AppRoute.Welcome
) {
    val navHostController = rememberNavController()
    NavHost(
        navController = navHostController,
        startDestination = startDestination,
    ) {
        composable<AppRoute.Welcome> {
            WelcomeScreen(
                navHostController = navHostController,
            )
        }
    }
}
