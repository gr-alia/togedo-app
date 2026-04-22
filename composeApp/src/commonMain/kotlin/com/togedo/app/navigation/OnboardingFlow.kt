package com.togedo.app.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.togedo.app.ui.onboarding.OnboardingScreen

@Composable
fun OnboardingFlow() {
    Navigator(OnboardingScreen()) { navigator ->
        SlideTransition(navigator)
    }
}