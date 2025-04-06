package dev.rarebit.kollage.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dev.rarebit.kollage.data.model.Collage
import dev.rarebit.kollage.ui.collageresult.CollageResultScreen
import dev.rarebit.kollage.ui.createcollage.CreateCollageScreen
import dev.rarebit.kollage.ui.home.HomeScreen
import dev.rarebit.kollage.ui.permissions.PermissionsScreen
import dev.rarebit.kollage.ui.tutorial.TutorialScreen
import dev.rarebit.kollage.ui.viewcollage.ViewCollageScreen
import dev.rarebit.kollage.ui.welcome.WelcomeScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import kotlin.reflect.typeOf

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

        composable<AppRoute.ViewCollage>(
            typeMap = mapOf(
                typeOf<Collage>() to CustomNavType.CollageType
            )
        ) {
            val args = it.toRoute<AppRoute.ViewCollage>()
            ViewCollageScreen(
                navHostController = navHostController,
                viewModel = koinViewModel {
                    parametersOf(args.collage)
                }
            )
        }
    }
}
