package com.togedo.app.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.togedo.app.ui.auth.LoginScreen

@Composable
fun AuthFlow() {
    Navigator(LoginScreen())
}
