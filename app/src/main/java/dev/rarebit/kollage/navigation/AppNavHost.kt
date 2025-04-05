package dev.rarebit.kollage.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.rarebit.kollage.onboarding.ui.permissions.PermissionsScreen
import dev.rarebit.kollage.onboarding.ui.welcome.WelcomeScreen
import dev.rarebit.kollage.ui.collageresult.CollageResultScreen
import dev.rarebit.kollage.ui.createcollage.CreateCollageScreen
import dev.rarebit.kollage.ui.home.HomeScreen
import dev.rarebit.kollage.ui.tutorial.TutorialScreen

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

        composable<AppRoute.Tutorial> {
            TutorialScreen(
                navHostController = navHostController
            )
        }

        composable<AppRoute.CreateCollage> {
            CreateCollageScreen(
                navHostController = navHostController
            )
        }

        composable<AppRoute.CollageResult> {
            CollageResultScreen(
                navHostController = navHostController
            )
        }
    }
}
