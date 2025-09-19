package com.togedo.app.navigation.main.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.togedo.app.designsystem.AppTheme
import com.togedo.app.designsystem.components.Button
import com.togedo.app.designsystem.components.Text

class ActivityDetailsScreen(private val activityId: String) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Activity Details Screen",
                style = AppTheme.typography.h2
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Activity: $activityId",
                style = AppTheme.typography.body2
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Button(
                onClick = { navigator.pop() }
            ) {
                Text("Back to List")
            }
        }
    }
}
