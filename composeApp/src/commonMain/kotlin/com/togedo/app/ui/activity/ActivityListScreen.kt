package com.togedo.app.ui.activity

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.togedo.app.designsystem.AppTheme
import com.togedo.app.designsystem.components.Text
import com.togedo.app.designsystem.components.card.Card
import com.togedo.app.ui.activity.ActivityDetailsScreen

class ActivityListScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val sampleActivities = listOf(
            "Movie Night",
            "Dinner at Italian Restaurant",
            "Hiking Trip",
            "Board Game Night",
            "Concert"
        )

        Column(
            modifier = Modifier.Companion
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Activity List Screen",
                style = AppTheme.typography.h2,
                modifier = Modifier.Companion.padding(bottom = 16.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(sampleActivities) { activity ->
                    Card(
                        modifier = Modifier.Companion
                            .fillMaxWidth()
                            .clickable {
                                navigator.push(ActivityDetailsScreen(activity))
                            }
                    ) {
                        Text(
                            text = activity,
                            modifier = Modifier.Companion.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}