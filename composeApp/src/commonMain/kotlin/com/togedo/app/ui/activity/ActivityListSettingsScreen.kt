package com.togedo.app.ui.activity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.togedo.app.designsystem.AppTheme
import com.togedo.app.designsystem.Spacing
import com.togedo.app.designsystem.components.Button
import com.togedo.app.designsystem.components.Text
import org.jetbrains.compose.ui.tooling.preview.Preview

class ActivityListSettingsScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        ActivityListSettingsContent(onBackClick = { navigator.pop() })
    }
}

@Composable
private fun ActivityListSettingsContent(onBackClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Spacing.spacing4),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(Spacing.spacing3))

        Text(
            text = "Bucket Settings Screen",
            style = AppTheme.typography.h2,
        )

        Spacer(modifier = Modifier.height(Spacing.spacing5))

        Button(onClick = onBackClick) {
            Text("Back to Activity List")
        }
    }
}

@Preview
@Composable
fun ActivityListSettingsScreenPreview() {
    AppTheme {
        ActivityListSettingsContent(onBackClick = {})
    }
}
