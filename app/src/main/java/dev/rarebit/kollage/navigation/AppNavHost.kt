package dev.rarebit.kollage.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.rarebit.kollage.onboarding.ui.permissions.PermissionsScreen
import dev.rarebit.kollage.onboarding.ui.welcome.WelcomeScreen
import dev.rarebit.kollage.ui.home.HomeScreen

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

        composable<AppRoute.Permissions> {
            PermissionsScreen(
                navHostController = navHostController,
            )
        }

        composable<AppRoute.Home> {
            HomeScreen(
                navHostController = navHostController
            )
        }
    }
}
