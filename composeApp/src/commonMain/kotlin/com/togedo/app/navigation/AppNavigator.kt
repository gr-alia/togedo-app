package com.togedo.app.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.togedo.app.navigation.auth.AuthFlow
import com.togedo.app.navigation.main.MainFlow

@Composable
fun AppNavigator() {
    val isLoggedIn = true // TODO: Replace with actual auth state
    
    if (isLoggedIn) {
        MainFlow()
    } else {
        AuthFlow()
    }
}
