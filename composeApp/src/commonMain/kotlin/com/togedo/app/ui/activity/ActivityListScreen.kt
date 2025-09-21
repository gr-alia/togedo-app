package com.togedo.app.ui.activity

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.togedo.app.designsystem.AppTheme
import com.togedo.app.designsystem.Spacing
import com.togedo.app.designsystem.components.Icon
import com.togedo.app.designsystem.components.Text
import com.togedo.app.designsystem.components.card.Card
import com.togedo.app.designsystem.components.textfield.TextField
import compose.icons.FeatherIcons
import compose.icons.feathericons.Search
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
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = "Hi, Alona",
                    style = AppTheme.typography.h2,
                    modifier = Modifier.Companion.padding(all = Spacing.spacing4)
                )
            }

            var search by remember { mutableStateOf("Search") }


            TextField(
                modifier = Modifier.padding(horizontal = Spacing.spacing4),
                value = search,
                onValueChange = { search = it },
                placeholder = { Text("Placeholder") },
                leadingIcon = {
                    Icon(
                        imageVector = FeatherIcons.Search
                    )
                }
            )

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