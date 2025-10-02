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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
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
        val screenModel = rememberScreenModel { ActivityListScreenModel() }
        val state by screenModel.state.collectAsState()

        ActivityListContent(
            state = state,
            onActivityClick = { activityId ->
                navigator.push(ActivityDetailsScreen(activityId))
            },
            onSearchQueryChanged = screenModel::onSearchQueryChanged,
            onFilterByStatus = screenModel::filterByStatus,
            onDeleteActivity = screenModel::deleteActivity,
            onRefresh = screenModel::loadActivities
        )
    }

    @Composable
    fun ActivityListContent(
        state: ActivityListState,
        onActivityClick: (String) -> Unit,
        onSearchQueryChanged: (String) -> Unit,
        onFilterByStatus: (ActivityFilter?) -> Unit,
        onDeleteActivity: (String) -> Unit,
        onRefresh: () -> Unit
    ) {

        val filteredActivities = state.activities.let { activities ->
            var filtered = activities

            if (state.searchQuery.isNotBlank()) {
                filtered = filtered.filter {
                    it.title.contains(state.searchQuery, ignoreCase = true) ||
                            it.description.contains(state.searchQuery, ignoreCase = true)
                }
            }

            state.selectedFilter?.let { filter ->
                filtered = filtered.filter { activity ->
                    when (filter) {
                        ActivityFilter.Idea -> activity.status == ActivityUiModel.ActivityStatus.Idea
                        ActivityFilter.Planned -> activity.status == ActivityUiModel.ActivityStatus.Planned
                        ActivityFilter.Canceled -> activity.status == ActivityUiModel.ActivityStatus.Canceled
                        ActivityFilter.Done -> activity.status == ActivityUiModel.ActivityStatus.Done
                    }
                }
            }

            filtered
        }

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
                    modifier = Modifier.padding(all = Spacing.spacing4)
                )
            }

            TextField(
                modifier = Modifier.padding(horizontal = Spacing.spacing4),
                value = state.searchQuery,
                onValueChange = onSearchQueryChanged,
                placeholder = { Text("Search activities...") },
                leadingIcon = {
                    Icon(
                        imageVector = FeatherIcons.Search
                    )
                }
            )

            when {
                state.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Loading...", style = AppTheme.typography.body1)
                    }
                }

                state.error != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Error: ${state.error}", style = AppTheme.typography.body1)
                            Text(
                                text = "Tap to retry",
                                style = AppTheme.typography.body2,
                                modifier = Modifier
                                    .padding(top = Spacing.spacing2)
                                    .clickable { onRefresh() }
                            )
                        }
                    }
                }

                else -> {
                    ActivityList(
                        activities = filteredActivities,
                        onActivityClick = onActivityClick
                    )
                }
            }
        }
    }

    @Composable
    private fun ActivityList(
        activities: List<ActivityUiModel>,
        onActivityClick: (String) -> Unit
    ) {

        LazyColumn(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = activities,
                key = { it.id }
            ) { activity ->
                ActivityCard(
                    activity = activity,
                    onClick = { onActivityClick(activity.id) }
                )
            }
        }
    }

    @Composable
    private fun ActivityCard(
        activity: ActivityUiModel,
        onClick: () -> Unit
    ) {
        val statusText = when (activity.status) {
            ActivityUiModel.ActivityStatus.Idea -> "Idea"
            ActivityUiModel.ActivityStatus.Planned -> "Planned"
            ActivityUiModel.ActivityStatus.Canceled -> "Canceled"
            ActivityUiModel.ActivityStatus.Done -> "Done"
        }

        val statusColor = when (activity.status) {
            ActivityUiModel.ActivityStatus.Idea -> AppTheme.colors.onFeatureSpecificStatusPlanning
            ActivityUiModel.ActivityStatus.Planned -> AppTheme.colors.onFeatureSpecificStatusPlanned
            ActivityUiModel.ActivityStatus.Canceled -> AppTheme.colors.onFeatureSpecificStatusCanceled
            ActivityUiModel.ActivityStatus.Done -> AppTheme.colors.onFeatureSpecificStatusDone
        }

        Card(
            modifier = Modifier.fillMaxWidth()
                .clickable { onClick() }
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = Spacing.spacing2)
                    ) {
                        Text(
                            text = activity.title,
                            style = AppTheme.typography.h4
                        )
                        if (activity.description.isNotBlank()) {
                            Text(
                                text = activity.description,
                                style = AppTheme.typography.body2,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                    Chip(
                        onClick = { /* Handle status click */ },
                        leadingIcon = {
                            Icon(
                                FeatherIcons.Circle,
                                contentDescription = null,
                                modifier = Modifier.size(ChipDefaults.ChipIconSize)
                            )
                        }
                    ) {
                        Text(statusText, color = statusColor)
                    }
                }

                FlowRow(
                    modifier = Modifier.padding(
                        vertical = Spacing.spacing2,
                        horizontal = Spacing.spacing4
                    ),
                    maxItemsInEachRow = 3
                ) {
                    Chip(
                        modifier = Modifier.padding(horizontal = 2.dp),
                        onClick = { /* Handle tag click */ }
                    ) {
                        Text(activity.tag.name)
                    }
                }
            }
        }
    }

    @Preview
    @Composable
    fun ActivityListContentPreview() {
        AppTheme {
            ActivityListContent(
                state = ActivityListState(
                    activities = listOf(
                        ActivityUiModel(
                            id = "1",
                            title = "Movie Night",
                            description = "Watch a classic film",
                            tag = ActivityUiModel.ActivityTag.Fun,
                            status = ActivityUiModel.ActivityStatus.Planned
                        ),
                        ActivityUiModel(
                            id = "2",
                            title = "Fancy Dinner",
                            description = "Watch a classic film",
                            tag = ActivityUiModel.ActivityTag.Fun,
                            status = ActivityUiModel.ActivityStatus.Planned
                        )
                    )
                ),
                onActivityClick = {},
                onSearchQueryChanged = {},
                onFilterByStatus = {},
                onDeleteActivity = {},
                onRefresh = {}
            )
        }
    }

    @Preview
    @Composable
    fun ActivityListErrorPreview() {
        AppTheme {
            ActivityListContent(
                state = ActivityListState(
                    error = "Error"
                ),
                onActivityClick = {},
                onSearchQueryChanged = {},
                onFilterByStatus = {},
                onDeleteActivity = {},
                onRefresh = {}
            )
        }
    }

    @Composable
    fun ActivityListLoadingPreview() {
        AppTheme {
            ActivityListContent(
                state = ActivityListState(
                    isLoading = true
                ),
                onActivityClick = {},
                onSearchQueryChanged = {},
                onFilterByStatus = {},
                onDeleteActivity = {},
                onRefresh = {}
            )
        }
    }
}