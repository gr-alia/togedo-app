package com.togedo.app.ui.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.togedo.app.designsystem.AppTheme
import com.togedo.app.designsystem.components.Button
import com.togedo.app.designsystem.components.Text

class RegisterScreen : Screen {
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
                text = "Register Screen",
                style = AppTheme.typography.h2
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { navigator.pop() }
            ) {
                Text("Back to Login")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { /* TODO: Implement register logic */ }
            ) {
                Text("Register")
            }
        }
    }
}
