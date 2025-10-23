package com.togedo.app.navigation

import androidx.compose.runtime.Composable

@Composable
fun AppNavigator() {
    val isLoggedIn = false // TODO: Replace with actual auth state
    val isOnboardingShown = false // TODO: Replace with actual auth state

    when {
        isLoggedIn -> MainFlow()
        isOnboardingShown -> AuthFlow()
        else -> OnboardingFlow()
    }
}
