package com.togedo.app.ui.activity

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.togedo.app.designsystem.AppTheme
import com.togedo.app.designsystem.BorderRadius
import com.togedo.app.designsystem.components.Text
import com.togedo.app.designsystem.components.card.Card
import org.jetbrains.compose.ui.tooling.preview.Preview

class ActivityListScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        ActivityListContent(onEventOccurred = {
            navigator.push(ActivityDetailsScreen(it))
        })
    }

    @Composable
    fun ActivityListContent(onEventOccurred: (activity: String) -> Unit) {


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
                .background(color = AppTheme.colors.background)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(
                        shape = RoundedCornerShape(
                            topEnd = BorderRadius.roundedNone,
                            topStart = BorderRadius.roundedNone,
                            bottomEnd = BorderRadius.roundedNone,
                            bottomStart = BorderRadius.roundedXl,
                        ),
                        brush = Brush.linearGradient(
                            colors = listOf(AppTheme.colors.secondary, AppTheme.colors.brand)
                        )
                    )
            ) {

                Text(
                    text = "Hi, Alona",
                    style = AppTheme.typography.h2,
                    modifier = Modifier.Companion.padding(all = 16.dp)
                )
            }

            LazyColumn(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(sampleActivities) { activity ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onEventOccurred(activity)
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

    @Preview
    @Composable
    fun ActivityListContentPreview() {
        AppTheme {
            ActivityListContent(onEventOccurred = {})
        }
    }
}