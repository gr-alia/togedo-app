package com.togedo.app.ui.activity.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.togedo.app.designsystem.components.OutlinedChip
import com.togedo.app.designsystem.components.Text
import com.togedo.app.designsystem.components.card.Card
import com.togedo.app.designsystem.components.textfield.TextField
import com.togedo.app.ui.activity.ActivityDetailsScreen
import compose.icons.FeatherIcons
import compose.icons.feathericons.AlertCircle
import compose.icons.feathericons.Circle
import compose.icons.feathericons.Search
import compose.icons.feathericons.X
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

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

    @OptIn(ExperimentalTime::class)
    @Composable
    private fun getGreeting(): String {
        val hour = Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault()).hour
        return when (hour) {
            in 0..11 -> "Good morning, Alona"
            in 12..17 -> "Good afternoon, Alona"
            else -> "Good evening, Alona"
        }
    }

    @Composable
    fun ActivityListContent(
        state: ActivityListState,
        onActivityClick: (String) -> Unit,
        onSearchQueryChanged: (String) -> Unit,
        onFilterByStatus: (ActivityUiModel.ActivityStatus?) -> Unit,
        onDeleteActivity: (String) -> Unit,
        onRefresh: () -> Unit
    ) {

        val filteredActivities = state.activities.let { activities ->
            var filtered = activities

            if (state.searchQuery.isNotBlank()) {
                filtered = filtered.filter {
                    it.title.contains(state.searchQuery, ignoreCase = true) ||
                            it.description.contains(state.searchQuery, ignoreCase = true) ||
                            it.tags.any { tag ->
                                tag.name.contains(
                                    state.searchQuery,
                                    ignoreCase = true
                                )
                            }
                }
            }

            state.selectedFilter?.let { filter ->
                filtered = filtered.filter { activity ->
                    // todo
                    when (filter) {
                        ActivityUiModel.ActivityStatus.Idea -> activity.status == ActivityUiModel.ActivityStatus.Idea
                        ActivityUiModel.ActivityStatus.Planned -> activity.status == ActivityUiModel.ActivityStatus.Planned
                        ActivityUiModel.ActivityStatus.Canceled -> activity.status == ActivityUiModel.ActivityStatus.Canceled
                        ActivityUiModel.ActivityStatus.Done -> activity.status == ActivityUiModel.ActivityStatus.Done
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

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.spacing4)
                    .padding(top = Spacing.spacing8, bottom = Spacing.spacing2)
            ) {
                Text(
                    text = getGreeting(),
                    style = AppTheme.typography.h1,
                    color = AppTheme.colors.text
                )
                Text(
                    text = "${filteredActivities.size} ${if (filteredActivities.size == 1) "activity" else "activities"}",
                    style = AppTheme.typography.body2,
                    color = AppTheme.colors.textSecondary,
                    modifier = Modifier.padding(top = Spacing.spacing1)
                )
            }


            TextField(
                modifier = Modifier.padding(horizontal = Spacing.spacing4),
                value = state.searchQuery,
                onValueChange = onSearchQueryChanged,
                placeholder = {
                    Text(
                        text = "Search by title, description, or tags...",
                        style = AppTheme.typography.body2
                    )
                },
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(18.dp),
                        imageVector = FeatherIcons.Search,
                        tint = AppTheme.colors.textSecondary
                    )
                },
                trailingIcon = if (state.searchQuery.isNotBlank()) {
                    {
                        Icon(
                            imageVector = FeatherIcons.X,
                            contentDescription = "Clear search",
                            modifier = Modifier.size(26.dp)
                                .clip(CircleShape)
                                .clickable { onSearchQueryChanged("") }
                                .padding(Spacing.spacing1)
                        )
                    }
                } else null
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = Spacing.spacing4)
                    .padding(top = Spacing.spacing4, bottom = Spacing.spacing5),
                horizontalArrangement = Arrangement.spacedBy(Spacing.spacing2)
            ) {
                val filters = listOf(
                    null to "All",
                    ActivityUiModel.ActivityStatus.Idea to "Idea",
                    ActivityUiModel.ActivityStatus.Planned to "Planned",
                    ActivityUiModel.ActivityStatus.Canceled to "Canceled",
                    ActivityUiModel.ActivityStatus.Done to "Done"
                )

                filters.forEach { (filter, label) ->
                    val isSelected = state.selectedFilter == filter

                    OutlinedChip(
                        colors = ChipDefaults.chipColors().copy(
                            containerColor = filter?.statusBackgroundColor
                                ?: AppTheme.colors.transparent,
                            outlineColor = filter?.statusColor ?: AppTheme.colors.primary,
                            contentColor = filter?.statusColor ?: AppTheme.colors.textSecondary,
                            selectedContainerColor = filter?.statusColor ?: AppTheme.colors.primary,
                            selectedContentColor = AppTheme.colors.white
                        ),
                        onClick = { onFilterByStatus(filter) },
                        selected = isSelected,
                        contentPadding = PaddingValues(
                            start = 10.dp,
                            end = 10.dp,
                            top = 6.dp,
                            bottom = 6.dp,
                        )
                        /* leadingIcon = if (filter != null) {
                             {
                                 Icon(
                                     imageVector = FeatherIcons.Circle,
                                     contentDescription = null,
                                     tint = filter.statusColor,
                                     modifier = Modifier.size(ChipDefaults.ChipIconSize)
                                 )
                             }
                         } else null*/
                    ) {
                        Text(
                            text = label,
                            style = AppTheme.typography.label2
                        )
                    }
                }
            }

            when {
                state.isLoading -> {
                    SkeletonLoadingState()
                }

                state.error != null -> {
                    ErrorState(
                        errorMessage = state.error,
                        onRetry = onRefresh
                    )
                }

                filteredActivities.isEmpty() -> {
                    EmptyState(
                        hasActivities = state.activities.isNotEmpty(),
                        hasSearch = state.searchQuery.isNotBlank() || state.selectedFilter != null
                    )
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
    private fun ErrorState(
        errorMessage: String,
        onRetry: () -> Unit
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(Spacing.spacing2)
            ) {
                Icon(
                    imageVector = FeatherIcons.AlertCircle,
                    contentDescription = null,
                    tint = AppTheme.colors.error,
                    modifier = Modifier.size(Spacing.spacing8)
                )
                Text(
                    text = "Something went wrong",
                    style = AppTheme.typography.h4,
                    color = AppTheme.colors.text
                )
                Text(
                    text = errorMessage,
                    style = AppTheme.typography.body2,
                    color = AppTheme.colors.textSecondary
                )
                Text(
                    text = "Retry",
                    style = AppTheme.typography.button,
                    color = AppTheme.colors.primary,
                    modifier = Modifier
                        .padding(top = Spacing.spacing2)
                        .clickable { onRetry() }
                )
            }
        }
    }

    @Composable
    private fun SkeletonLoadingState() {
        LazyColumn(
            modifier = Modifier.padding(horizontal = Spacing.spacing4),
            verticalArrangement = Arrangement.spacedBy(Spacing.spacing3)
        ) {
            items(4) {
                SkeletonCard()
            }
        }
    }

    @Composable
    private fun SkeletonCard() {
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .width(Spacing.spacing1)
                        .height(Spacing.spacing20)
                        .background(AppTheme.colors.disabled)
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(Spacing.spacing4),
                    verticalArrangement = Arrangement.spacedBy(Spacing.spacing2)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .height(Spacing.spacing6)
                            .background(
                                AppTheme.colors.disabled,
                                shape = androidx.compose.foundation.shape.RoundedCornerShape(Spacing.spacing1)
                            )
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(Spacing.spacing4)
                            .background(
                                AppTheme.colors.disabled.copy(alpha = 0.5f),
                                shape = androidx.compose.foundation.shape.RoundedCornerShape(Spacing.spacing1)
                            )
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(Spacing.spacing4)
                            .background(
                                AppTheme.colors.disabled.copy(alpha = 0.5f),
                                shape = androidx.compose.foundation.shape.RoundedCornerShape(Spacing.spacing1)
                            )
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(Spacing.spacing1)
                    ) {
                        repeat(3) {
                            Box(
                                modifier = Modifier
                                    .width(Spacing.spacing16)
                                    .height(Spacing.spacing6)
                                    .background(
                                        AppTheme.colors.disabled.copy(alpha = 0.3f),
                                        shape = androidx.compose.foundation.shape.RoundedCornerShape(
                                            Spacing.spacing2
                                        )
                                    )
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun EmptyState(
        hasActivities: Boolean,
        hasSearch: Boolean
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(Spacing.spacing2)
            ) {
                Icon(
                    imageVector = if (hasSearch) FeatherIcons.Search else FeatherIcons.Circle,
                    contentDescription = null,
                    tint = AppTheme.colors.textSecondary,
                    modifier = Modifier.size(Spacing.spacing12)
                )
                Text(
                    text = if (hasSearch) "No activities found" else "No activities yet",
                    style = AppTheme.typography.h3,
                    color = AppTheme.colors.text
                )
                Text(
                    text = if (hasSearch) "Try adjusting your search or filters" else "Create your first activity to get started",
                    style = AppTheme.typography.body2,
                    color = AppTheme.colors.textSecondary
                )
            }
        }
    }

    @Composable
    private fun ActivityList(
        activities: List<ActivityUiModel>,
        onActivityClick: (String) -> Unit
    ) {

        LazyColumn(
            modifier = Modifier.padding(horizontal = Spacing.spacing4),
            verticalArrangement = Arrangement.spacedBy(Spacing.spacing3)
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

        Card(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .width(Spacing.spacing1)
                        .fillMaxHeight()
                        .background(activity.status.statusColor)
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(Spacing.spacing4)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = activity.title,
                            style = AppTheme.typography.h3,
                            color = AppTheme.colors.text,
                            modifier = Modifier.weight(1f).padding(end = Spacing.spacing2)
                        )

                        OutlinedChip(
                            colors = ChipDefaults.chipColors().copy(
                                containerColor = activity.status.statusBackgroundColor,
                                outlineColor = activity.status.statusColor,
                                contentColor = activity.status.statusColor,
                                selectedContainerColor = activity.status.statusColor,
                                selectedContentColor = AppTheme.colors.white
                            ),
                            onClick = { },
                            selected = true,
                            contentPadding = PaddingValues(
                                start = 10.dp,
                                end = 10.dp,
                                top = 6.dp,
                                bottom = 6.dp,
                            )
                            /* leadingIcon = if (filter != null) {
                                 {
                                     Icon(
                                         imageVector = FeatherIcons.Circle,
                                         contentDescription = null,
                                         tint = filter.statusColor,
                                         modifier = Modifier.size(ChipDefaults.ChipIconSize)
                                     )
                                 }
                             } else null*/
                        ) {
                            Text(
                                text = statusText,
                                style = AppTheme.typography.label2
                            )
                        }
                      /*  Chip(
                            onClick = { },
                            leadingIcon = {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .background(
                                            activity.status.statusColor,
                                            shape = androidx.compose.foundation.shape.CircleShape
                                        )
                                )
                            }
                        ) {
                            Text(
                                text = statusText,
                                style = AppTheme.typography.label2,
                                color = activity.status.statusColor
                            )
                        }*/
                    }

                    if (activity.description.isNotBlank()) {
                        Text(
                            text = activity.description,
                            style = AppTheme.typography.body2,
                            color = AppTheme.colors.textSecondary,
                            maxLines = 2,
                            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = Spacing.spacing1)
                        )
                    }

                    if (activity.tags.isNotEmpty()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = Spacing.spacing3),
                            horizontalArrangement = Arrangement.spacedBy(Spacing.spacing1)
                        ) {
                            activity.tags.take(3).forEach { tag ->
                                Chip(
                                    onClick = { }
                                ) {
                                    Text(
                                        text = tag.name,
                                        style = AppTheme.typography.label3
                                    )
                                }
                            }
                            if (activity.tags.size > 3) {
                                Chip(
                                    onClick = { }
                                ) {
                                    Text(
                                        text = "+${activity.tags.size - 3}",
                                        style = AppTheme.typography.label3
                                    )
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
            ActivityListContent(
                state = ActivityListState(
                    activities = listOf(
                        ActivityUiModel(
                            id = "1",
                            title = "Movie Night",
                            description = "Watch a classic film",
                            tags = listOf(
                                ActivityUiModel.ActivityTag.Fun,
                                ActivityUiModel.ActivityTag.Chill,
                                ActivityUiModel.ActivityTag.Romantic
                            ),
                            status = ActivityUiModel.ActivityStatus.Planned
                        ),
                        ActivityUiModel(
                            id = "2",
                            title = "Fancy Dinner at Italian Restaurant wearing suits",
                            description = "Watch a classic film",
                            tags = listOf(
                                ActivityUiModel.ActivityTag.Fancy,
                                ActivityUiModel.ActivityTag.Romantic
                            ),
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

    @Preview
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