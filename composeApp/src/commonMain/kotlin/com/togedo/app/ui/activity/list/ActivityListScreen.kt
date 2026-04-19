package com.togedo.app.ui.activity.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.togedo.app.designsystem.AppTheme
import com.togedo.app.designsystem.BorderRadius
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
import compose.icons.feathericons.Settings
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
        val screenModel = koinScreenModel<ActivityListScreenModel>()
        val state by screenModel.state.collectAsState()

        ActivityListContent(
            state = state,
            onActivityClick = { activityId -> navigator.push(ActivityDetailsScreen(activityId)) },
            onSearchQueryChanged = screenModel::onSearchQueryChanged,
            onFilterByStatus = screenModel::filterByStatus,
            onDeleteActivity = screenModel::deleteActivity,
            onRefresh = screenModel::loadActivities,
            onSettingsClick = { navigator.push(ActivityListSettingsScreen()) },
        )
    }

    @OptIn(ExperimentalTime::class)
    @Composable
    private fun getGreeting(): String {
        val hour = Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault()).hour
        return when (hour) {
            in 0..11 -> "Good morning,"
            in 12..17 -> "Good afternoon,"
            else -> "Good evening,"
        }
    }

    @Composable
    fun ActivityListContent(
        state: ActivityListState,
        onActivityClick: (String) -> Unit,
        onSearchQueryChanged: (String) -> Unit,
        onFilterByStatus: (ActivityUiModel.ActivityStatus?) -> Unit,
        onDeleteActivity: (String) -> Unit,
        onRefresh: () -> Unit,
        onSettingsClick: () -> Unit = {},
    ) {
        val filteredActivities = state.activities.let { activities ->
            var filtered = activities

            if (state.searchQuery.isNotBlank()) {
                filtered = filtered.filter {
                    it.title.contains(state.searchQuery, ignoreCase = true) ||
                        it.description.contains(state.searchQuery, ignoreCase = true) ||
                        it.tags.any { tag -> tag.name.contains(state.searchQuery, ignoreCase = true) }
                }
            }

            state.selectedFilter?.let { filter ->
                filtered = filtered.filter { activity ->
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
                .background(AppTheme.colors.background),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.spacing4)
                    .padding(top = Spacing.spacing8, bottom = Spacing.spacing4),
            ) {
                GreetingHeader(
                    greeting = getGreeting(),
                    onSettingsClick = onSettingsClick,
                )

                Spacer(modifier = Modifier.height(Spacing.spacing4))

                StreakCard()

                Spacer(modifier = Modifier.height(Spacing.spacing4))

                TextField(
                    value = state.searchQuery,
                    onValueChange = onSearchQueryChanged,
                    placeholder = {
                        Text(
                            text = "Search by title, description, or tags…",
                            style = AppTheme.typography.body2,
                        )
                    },
                    leadingIcon = {
                        Icon(
                            modifier = Modifier.size(18.dp),
                            imageVector = FeatherIcons.Search,
                            tint = AppTheme.colors.textSecondary,
                        )
                    },
                    trailingIcon = if (state.searchQuery.isNotBlank()) {
                        {
                            Icon(
                                imageVector = FeatherIcons.X,
                                contentDescription = "Clear search",
                                modifier = Modifier
                                    .size(26.dp)
                                    .clip(CircleShape)
                                    .clickable { onSearchQueryChanged("") }
                                    .padding(Spacing.spacing1),
                            )
                        }
                    } else {
                        null
                    },
                )

                Spacer(modifier = Modifier.height(Spacing.spacing3))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(Spacing.spacing2),
                ) {
                    val filters = listOf(
                        null to "All · ${state.activities.size}",
                        ActivityUiModel.ActivityStatus.Idea to "Idea",
                        ActivityUiModel.ActivityStatus.Planned to "Planned",
                        ActivityUiModel.ActivityStatus.Canceled to "Canceled",
                        ActivityUiModel.ActivityStatus.Done to "Done",
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
                                selectedContentColor = AppTheme.colors.white,
                            ),
                            onClick = { onFilterByStatus(filter) },
                            selected = isSelected,
                            contentPadding = PaddingValues(
                                start = 10.dp,
                                end = 10.dp,
                                top = 6.dp,
                                bottom = 6.dp,
                            ),
                        ) {
                            Text(text = label, style = AppTheme.typography.label2)
                        }
                    }
                }
            }

            when {
                state.isLoading -> SkeletonLoadingState()
                state.error != null -> ErrorState(errorMessage = state.error, onRetry = onRefresh)
                filteredActivities.isEmpty() -> EmptyState(
                    hasActivities = state.activities.isNotEmpty(),
                    hasSearch = state.searchQuery.isNotBlank() || state.selectedFilter != null,
                )
                else -> ActivityList(activities = filteredActivities, onActivityClick = onActivityClick)
            }
        }
    }

    @Composable
    private fun GreetingHeader(
        greeting: String,
        onSettingsClick: () -> Unit,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                Text(
                    text = greeting,
                    style = AppTheme.typography.label1,
                    color = AppTheme.colors.textSecondary,
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Emma ",
                        style = AppTheme.typography.display1,
                        color = AppTheme.colors.text,
                    )
                    Text(
                        text = "✿",
                        style = AppTheme.typography.display1,
                        color = AppTheme.colors.tertiary,
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                AvatarPair()
                Spacer(modifier = Modifier.width(Spacing.spacing2))
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(AppTheme.colors.surface)
                        .clickable(onClick = onSettingsClick),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = FeatherIcons.Settings,
                        contentDescription = "List settings",
                        tint = AppTheme.colors.textSecondary,
                        modifier = Modifier.size(18.dp),
                    )
                }
            }
        }
    }

    @Composable
    private fun AvatarPair() {
        Box {
            UserAvatar(
                initial = "E",
                size = 36.dp,
                bgColor = AppTheme.colors.primary,
                textColor = AppTheme.colors.onPrimary,
            )
            Box(modifier = Modifier.offset(x = 22.dp)) {
                UserAvatar(
                    initial = "J",
                    size = 36.dp,
                    bgColor = AppTheme.colors.tertiary,
                    textColor = AppTheme.colors.onTertiary,
                )
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .align(Alignment.BottomEnd)
                        .clip(CircleShape)
                        .background(AppTheme.colors.surface)
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(AppTheme.colors.secondary),
                )
            }
        }
    }

    @Composable
    private fun UserAvatar(
        initial: String,
        size: androidx.compose.ui.unit.Dp,
        bgColor: androidx.compose.ui.graphics.Color,
        textColor: androidx.compose.ui.graphics.Color,
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .background(bgColor),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = initial,
                style = AppTheme.typography.label1.copy(fontWeight = FontWeight.Bold),
                color = textColor,
            )
        }
    }

    @Composable
    private fun StreakCard() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(BorderRadius.roundedLg))
                .background(AppTheme.colors.primaryContainer)
                .padding(horizontal = Spacing.spacing4, vertical = Spacing.spacing3),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.spacing3),
        ) {
            Text(
                text = "14",
                style = AppTheme.typography.h2.copy(fontWeight = FontWeight.ExtraBold),
                color = AppTheme.colors.primary,
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "day streak with Jack",
                    style = AppTheme.typography.label1,
                    color = AppTheme.colors.text,
                )
                Text(
                    text = "4 ideas added this week · 2 planned",
                    style = AppTheme.typography.body3,
                    color = AppTheme.colors.textSecondary,
                )
            }

            MiniBarChart()
        }
    }

    @Composable
    private fun MiniBarChart() {
        val bars = listOf(true, true, true, true, true, true, false)
        Row(
            horizontalArrangement = Arrangement.spacedBy(3.dp),
            verticalAlignment = Alignment.Bottom,
        ) {
            bars.forEach { active ->
                Box(
                    modifier = Modifier
                        .width(6.dp)
                        .height(18.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(if (active) AppTheme.colors.tertiary else AppTheme.colors.outline),
                )
            }
        }
    }

    @Composable
    private fun ErrorState(errorMessage: String, onRetry: () -> Unit) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(Spacing.spacing2),
            ) {
                Icon(
                    imageVector = FeatherIcons.AlertCircle,
                    contentDescription = null,
                    tint = AppTheme.colors.error,
                    modifier = Modifier.size(Spacing.spacing8),
                )
                Text(text = "Something went wrong", style = AppTheme.typography.h4, color = AppTheme.colors.text)
                Text(text = errorMessage, style = AppTheme.typography.body2, color = AppTheme.colors.textSecondary)
                Text(
                    text = "Retry",
                    style = AppTheme.typography.button,
                    color = AppTheme.colors.primary,
                    modifier = Modifier.padding(top = Spacing.spacing2).clickable { onRetry() },
                )
            }
        }
    }

    @Composable
    private fun SkeletonLoadingState() {
        LazyColumn(
            modifier = Modifier.padding(horizontal = Spacing.spacing5),
            verticalArrangement = Arrangement.spacedBy(Spacing.spacing3),
        ) {
            items(4) { SkeletonCard() }
        }
    }

    @Composable
    private fun SkeletonCard() {
        Card(modifier = Modifier.fillMaxWidth().height(128.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .width(4.dp)
                        .fillMaxHeight()
                        .background(AppTheme.colors.disabled),
                )
                Column(
                    modifier = Modifier.weight(1f).padding(Spacing.spacing4),
                    verticalArrangement = Arrangement.spacedBy(Spacing.spacing2),
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth(0.6f).height(Spacing.spacing6)
                            .background(AppTheme.colors.disabled, RoundedCornerShape(Spacing.spacing1)),
                    )
                    Box(
                        modifier = Modifier.fillMaxWidth(0.9f).height(Spacing.spacing4)
                            .background(AppTheme.colors.disabled.copy(alpha = 0.5f), RoundedCornerShape(Spacing.spacing1)),
                    )
                }
            }
        }
    }

    @Composable
    private fun EmptyState(hasActivities: Boolean, hasSearch: Boolean) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(Spacing.spacing2),
            ) {
                Icon(
                    imageVector = if (hasSearch) FeatherIcons.Search else FeatherIcons.Circle,
                    contentDescription = null,
                    tint = AppTheme.colors.textSecondary,
                    modifier = Modifier.size(Spacing.spacing12),
                )
                Text(
                    text = if (hasSearch) "No activities found" else "No activities yet",
                    style = AppTheme.typography.h3,
                    color = AppTheme.colors.text,
                )
                Text(
                    text = if (hasSearch) "Try adjusting your search or filters" else "Create your first activity to get started",
                    style = AppTheme.typography.body2,
                    color = AppTheme.colors.textSecondary,
                )
            }
        }
    }

    @Composable
    private fun ActivityList(activities: List<ActivityUiModel>, onActivityClick: (String) -> Unit) {
        LazyColumn(
            modifier = Modifier.padding(horizontal = Spacing.spacing5),
            verticalArrangement = Arrangement.spacedBy(Spacing.spacing3),
            contentPadding = PaddingValues(bottom = Spacing.spacing6),
        ) {
            items(items = activities, key = { it.id }) { activity ->
                ActivityCard(activity = activity, onClick = { onActivityClick(activity.id) })
            }
        }
    }

    @Composable
    private fun ActivityCard(activity: ActivityUiModel, onClick: () -> Unit) {
        val ownerColor = when (activity.owner) {
            ActivityUiModel.ActivityOwner.User -> AppTheme.colors.primary
            ActivityUiModel.ActivityOwner.Partner -> AppTheme.colors.secondary
        }
        val ownerLabel = when (activity.owner) {
            ActivityUiModel.ActivityOwner.User -> "You"
            ActivityUiModel.ActivityOwner.Partner -> "Jack"
        }
        val statusText = when (activity.status) {
            ActivityUiModel.ActivityStatus.Idea -> "Idea"
            ActivityUiModel.ActivityStatus.Planned -> "Planned"
            ActivityUiModel.ActivityStatus.Canceled -> "Canceled"
            ActivityUiModel.ActivityStatus.Done -> "Done"
        }

        Card(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
            ) {
                Box(
                    modifier = Modifier
                        .width(4.dp)
                        .fillMaxHeight()
                        .background(ownerColor),
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(
                            start = Spacing.spacing3,
                            end = Spacing.spacing4,
                            top = Spacing.spacing4,
                            bottom = Spacing.spacing4,
                        ),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top,
                    ) {
                        Text(
                            text = activity.title,
                            style = AppTheme.typography.h3,
                            color = AppTheme.colors.text,
                            modifier = Modifier.weight(1f).padding(end = Spacing.spacing2),
                        )
                        OutlinedChip(
                            colors = ChipDefaults.chipColors().copy(
                                containerColor = activity.status.statusBackgroundColor,
                                outlineColor = activity.status.statusColor,
                                contentColor = activity.status.statusColor,
                                selectedContainerColor = activity.status.statusColor,
                                selectedContentColor = AppTheme.colors.white,
                            ),
                            onClick = {},
                            selected = true,
                            contentPadding = PaddingValues(
                                start = 10.dp,
                                end = 10.dp,
                                top = 6.dp,
                                bottom = 6.dp,
                            ),
                        ) {
                            Text(text = statusText, style = AppTheme.typography.label2)
                        }
                    }

                    if (activity.description.isNotBlank()) {
                        Text(
                            text = activity.description,
                            style = AppTheme.typography.body2,
                            color = AppTheme.colors.textSecondary,
                            maxLines = 2,
                            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                            modifier = Modifier.fillMaxWidth().padding(top = Spacing.spacing1),
                        )
                    }

                    Spacer(modifier = Modifier.height(Spacing.spacing3))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(Spacing.spacing1),
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(AppTheme.colors.tertiary),
                        )
                        Text(
                            text = ownerLabel,
                            style = AppTheme.typography.label2.copy(fontWeight = FontWeight.SemiBold),
                            color = AppTheme.colors.textSecondary,
                        )
                        if (activity.date != null) {
                            Text(
                                text = "· ${activity.date}",
                                style = AppTheme.typography.label2,
                                color = AppTheme.colors.textDisabled,
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        activity.tags.take(2).forEach { tag ->
                            Text(
                                text = tag.name,
                                style = AppTheme.typography.label2,
                                color = AppTheme.colors.textSecondary,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(BorderRadius.roundedFull))
                                    .background(AppTheme.colors.background)
                                    .padding(horizontal = Spacing.spacing2, vertical = 3.dp),
                            )
                        }
                        if (activity.tags.size > 2) {
                            Text(
                                text = "+${activity.tags.size - 2}",
                                style = AppTheme.typography.label2,
                                color = AppTheme.colors.textDisabled,
                            )
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
                            title = "Sunset picnic at Eidsvold",
                            description = "Wine, cheese, that blanket we never use.",
                            tags = listOf(ActivityUiModel.ActivityTag.Romantic, ActivityUiModel.ActivityTag.Chill),
                            status = ActivityUiModel.ActivityStatus.Planned,
                            owner = ActivityUiModel.ActivityOwner.User,
                            date = "Fri",
                        ),
                        ActivityUiModel(
                            id = "2",
                            title = "Japanese cooking class",
                            description = "The one near Central Market with handmade udon.",
                            tags = listOf(ActivityUiModel.ActivityTag.Fun, ActivityUiModel.ActivityTag.Fancy),
                            status = ActivityUiModel.ActivityStatus.Idea,
                            owner = ActivityUiModel.ActivityOwner.Partner,
                        ),
                    ),
                ),
                onActivityClick = {},
                onSearchQueryChanged = {},
                onFilterByStatus = {},
                onDeleteActivity = {},
                onRefresh = {},
            )
        }
    }
}
