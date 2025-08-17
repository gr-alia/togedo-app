package com.togedo.app.navigation.auth

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.togedo.app.navigation.auth.screens.LoginScreen

@Composable
fun AuthFlow() {
    Navigator(LoginScreen()) { navigator ->
        // Auth flow content
    }
}
