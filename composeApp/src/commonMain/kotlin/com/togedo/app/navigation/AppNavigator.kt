package com.togedo.app.navigation

import androidx.compose.runtime.Composable

@Composable
fun AppNavigator() {
    val isLoggedIn = true // TODO: Replace with actual auth state
    
    if (isLoggedIn) {
        MainFlow()
    } else {
        AuthFlow()
    }
}
