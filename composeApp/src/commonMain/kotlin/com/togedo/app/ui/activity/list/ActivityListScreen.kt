package com.togedo.app.ui.activity.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.togedo.app.designsystem.AppTheme
import com.togedo.app.designsystem.Spacing
import com.togedo.app.designsystem.components.Chip
import com.togedo.app.designsystem.components.ChipDefaults
import com.togedo.app.designsystem.components.Icon
import com.togedo.app.designsystem.components.Text
import com.togedo.app.designsystem.components.card.Card
import com.togedo.app.designsystem.components.textfield.TextField
import com.togedo.app.ui.activity.ActivityDetailsScreen
import compose.icons.FeatherIcons
import compose.icons.feathericons.Circle
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
            "Dinner at Italian Restaurant wearing fancy suits",
            "Hiking Trip",
            "Board Game Night",
            "Concert"
        )

        val sampleTags = listOf(
            "Fun",
            "Sport",
            "Sex",
            // "Chill",
            //  "Romantic",
            //   "Fancy",
        )

        val sampleStatuses = listOf(
            "Planning",
            "Planned",
            "Canceled",
            "Done"
        )

        val statusTextColors = listOf(
            AppTheme.colors.onFeatureSpecificStatusPlanning,
            AppTheme.colors.onFeatureSpecificStatusPlanned,
            AppTheme.colors.onFeatureSpecificStatusCanceled,
            AppTheme.colors.onFeatureSpecificStatusDone
        )

        val sampleColors = listOf(
            AppTheme.colors.brandEarthYellow,
            AppTheme.colors.brandVerdigris,
            AppTheme.colors.brandRusset,
            AppTheme.colors.brandUtOrange,
            AppTheme.colors.brandAlabaster
        )

        Column(
            modifier = Modifier.Companion
                .fillMaxSize()
                .background(color = AppTheme.colors.background)
        ) {

            Box(
                modifier = Modifier.Companion.fillMaxWidth()
            ) {

                Text(
                    text = "Hi, Alona",
                    style = AppTheme.typography.h2,
                    modifier = Modifier.Companion.padding(all = Spacing.spacing4)
                )
            }

            var search by remember { mutableStateOf("Search") }


            TextField(
                modifier = Modifier.Companion.padding(horizontal = Spacing.spacing4),
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
                modifier = Modifier.Companion.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(sampleActivities) { activity ->
                    Card(
                        modifier = Modifier.Companion
                            .fillMaxWidth()
                            .clickable {
                                onEventOccurred(activity)
                            }
                    ) {
                        Column() {
                            Row(
                                modifier = Modifier.Companion.fillMaxWidth().padding(16.dp),
                                verticalAlignment = Alignment.Companion.CenterVertically
                            ) {
                                Text(
                                    text = activity,
                                    modifier = Modifier.Companion.weight(1f)
                                        .padding(end = Spacing.spacing2)
                                )
                                Chip(
                                    onClick = { /* Handle click */ },
                                    leadingIcon = {
                                        Icon(
                                            FeatherIcons.Circle,
                                            contentDescription = null,
                                            modifier = Modifier.Companion.size(ChipDefaults.ChipIconSize)
                                        )
                                    }
                                ) {
                                    Text(sampleStatuses.random(), color = statusTextColors.random())
                                }
                            }


                            FlowRow(
                                modifier = Modifier.Companion.padding(
                                    vertical = Spacing.spacing2,
                                    horizontal = Spacing.spacing4
                                ),
                                maxItemsInEachRow = 3
                            ) {
                                sampleTags.forEach { text ->
                                    Chip(
                                        modifier = Modifier.Companion.padding(horizontal = 2.dp),
                                        onClick = { /* Handle click */ }
                                    ) {
                                        Text(text, color = sampleColors.random())
                                    }
                                }

                            }
                        }

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