package com.togedo.app

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.togedo.app.navigation.AppNavigator
import com.togedo.app.theme.AppTheme

@Composable
internal fun App() = AppTheme {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        AppNavigator()
    }
}
