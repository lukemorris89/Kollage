package dev.rarebit.kollage.ui.permissions

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import dev.rarebit.core.permission.hasCameraPermission
import dev.rarebit.core.permission.openAppSettings
import dev.rarebit.kollage.navigation.AppRoute
import dev.rarebit.kollage.ui.permissions.data.PermissionsViewEvent
import dev.rarebit.kollage.ui.permissions.data.PermissionsViewModel
import org.koin.androidx.compose.koinViewModel

@Suppress("CyclomaticComplexMethod")
@Composable
fun PermissionsScreen(
    navHostController: NavHostController,
    viewModel: PermissionsViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val viewData by viewModel.viewData.collectAsStateWithLifecycle()
    PermissionsContent(
        viewData = viewData,
        onViewAction = {
            with(viewModel) {
                onViewAction(it)
            }
        }
    )

    val requestPermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted: Boolean ->
                with(viewModel) {
                    if (isGranted) {
                        onViewAction(PermissionsViewAction.OnPermissionGranted)
                    } else {
                        onViewAction(PermissionsViewAction.OnTogglePermissionDeniedBottomsheet(true))
                    }
                }
            }
        )

    LaunchedEffect(Unit) {
        with(viewModel) {
            if (context.hasCameraPermission()) {
                val navOptions = NavOptions.Builder().apply {
                    setPopUpTo<AppRoute.Home>(inclusive = false)
                }.build()
                navHostController.navigate(AppRoute.CreateCollage, navOptions)
            }
            viewEvent.collect { event ->
                when (event.consume()) {
                    PermissionsViewEvent.NavigateToCreateCollageScreen -> {
                        val navOptions = NavOptions.Builder().apply {
                            setPopUpTo<AppRoute.Home>(inclusive = false)
                        }.build()
                        navHostController.navigate(AppRoute.CreateCollage, navOptions)
                    }

                    PermissionsViewEvent.CheckPermissions ->
                        when {
                            ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.CAMERA
                            ) == PackageManager.PERMISSION_GRANTED -> {
                                val navOptions = NavOptions.Builder().apply {
                                    setPopUpTo<AppRoute.Home>(inclusive = false)
                                }.build()
                                navHostController.navigate(AppRoute.CreateCollage, navOptions)
                            }

                            ActivityCompat.shouldShowRequestPermissionRationale(
                                context as Activity,
                                Manifest.permission.CAMERA
                            ) -> {
                                with(viewModel) {
                                    onViewAction(
                                        PermissionsViewAction.OnTogglePermissionRationaleBottomsheet(
                                            true
                                        )
                                    )
                                }
                            }

                            else -> {
                                requestPermissionLauncher.launch(
                                    Manifest.permission.CAMERA
                                )
                            }
                        }

                    PermissionsViewEvent.RequestPermissions -> {
                        requestPermissionLauncher.launch(
                            Manifest.permission.CAMERA
                        )
                    }

                    PermissionsViewEvent.OpenAppSettings -> {
                        context.openAppSettings()
                    }

                    null -> Unit
                }
            }
        }
    }
}

context(PermissionsViewModel)
private fun onViewAction(viewAction: PermissionsViewAction) {
    when (viewAction) {
        PermissionsViewAction.OnClickPrimaryCta -> {
            checkPermissions()
        }

        is PermissionsViewAction.OnTogglePermissionRationaleBottomsheet -> {
            togglePermissionRationaleBottomsheet(viewAction.isDisplayed)
        }

        is PermissionsViewAction.OnTogglePermissionDeniedBottomsheet -> {
            togglePermissionDeniedBottomsheet(viewAction.isDisplayed)
        }

        PermissionsViewAction.OnPermissionGranted -> onPermissionGranted()
        PermissionsViewAction.OnClickPermissionRationalePrimaryCta -> {
            togglePermissionRationaleBottomsheet(false)
            requestPermissions()
        }

        PermissionsViewAction.OnClickPermissionRationaleSecondaryCta -> {
            togglePermissionRationaleBottomsheet(false)
        }

        PermissionsViewAction.OnClickPermissionDeniedPrimaryCta -> {
            togglePermissionDeniedBottomsheet(false)
            openAppSettings()
        }

        PermissionsViewAction.OnClickPermissionDeniedSecondaryCta -> {
            togglePermissionDeniedBottomsheet(false)
        }
    }
}
