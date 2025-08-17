package com.togedo.app.navigation.main.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

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
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Activity List Screen",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(sampleActivities) { activity ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navigator.push(ActivityDetailsScreen(activity))
                            }
                    ) {
                        Text(
                            text = activity,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}
