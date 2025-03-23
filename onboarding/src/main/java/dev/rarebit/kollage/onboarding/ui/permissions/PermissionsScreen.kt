package dev.rarebit.kollage.onboarding.ui.permissions

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import dev.rarebit.kollage.navigation.AppRoute
import dev.rarebit.kollage.onboarding.ui.permissions.data.PermissionsViewEvent
import dev.rarebit.kollage.onboarding.ui.permissions.data.PermissionsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun PermissionsScreen(
    navHostController: NavHostController,
    viewModel: PermissionsViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val viewData by viewModel.viewData.collectAsState()
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
                if (isGranted) {
                    viewModel.onPermissionGranted()
                } else {
                    // Explain to the user that the feature is unavailable because the
                    // feature requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                    viewModel.togglePermissionDeniedBottomsheet(true)
                }
            }
        )

    LaunchedEffect(true) {
        viewModel.viewEvent.collect { event ->
            when (event.consume()) {
                PermissionsViewEvent.NavigateToHomeScreen -> {
                    navHostController.navigate(AppRoute.Home)
                }

                PermissionsViewEvent.CheckPermissions ->
                    when {
                        ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.CAMERA
                        ) == PackageManager.PERMISSION_GRANTED -> {
                            navHostController.navigate(AppRoute.Home)
                        }

                        ActivityCompat.shouldShowRequestPermissionRationale(
                            context as Activity, Manifest.permission.CAMERA
                        ) -> {
                            // In an educational UI, explain to the user why your app requires this
                            // permission for a specific feature to behave as expected, and what
                            // features are disabled if it's declined. In this UI, include a
                            // "cancel" or "no thanks" button that lets the user continue
                            // using your app without granting the permission.
                            viewModel.togglePermissionRationaleBottomsheet(true)
                        }

                        else -> {
                            // You can directly ask for the permission.
                            // The registered ActivityResultCallback gets the result of this request.
                            requestPermissionLauncher.launch(
                                Manifest.permission.CAMERA
                            )
                        }
                    }

                else -> {}
            }
        }
    }
}

context(PermissionsViewModel)
private fun onViewAction(viewAction: PermissionsViewAction) {
    when (viewAction) {
        PermissionsViewAction.OnClickPrimaryCta -> {
            onPrimaryCtaClicked()
        }
    }
}