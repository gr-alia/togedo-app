package com.togedo.app

import androidx.compose.foundation.layout.*

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.togedo.app.navigation.AppNavigator
import com.togedo.app.designsystem.AppTheme
import com.togedo.app.designsystem.components.Surface

@Composable
internal fun App() = AppTheme {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = AppTheme.colors.background
    ) {
        AppNavigator()
    }
}
