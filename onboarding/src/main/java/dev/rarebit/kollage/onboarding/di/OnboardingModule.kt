package dev.rarebit.kollage.onboarding.di

import dev.rarebit.kollage.onboarding.ui.permissions.data.PermissionsViewModel
import dev.rarebit.kollage.onboarding.ui.welcome.data.WelcomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val onboardingModule = module {
    viewModel { WelcomeViewModel(get()) }
    viewModel { PermissionsViewModel(get()) }
}
