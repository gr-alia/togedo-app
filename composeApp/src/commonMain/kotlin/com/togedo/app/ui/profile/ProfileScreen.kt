package com.togedo.app.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.togedo.app.designsystem.AppTheme
import com.togedo.app.designsystem.BorderRadius
import com.togedo.app.designsystem.Spacing
import com.togedo.app.designsystem.components.Button
import com.togedo.app.designsystem.components.ButtonVariant
import com.togedo.app.designsystem.components.Icon
import com.togedo.app.designsystem.components.Surface
import com.togedo.app.designsystem.components.Text
import compose.icons.FeatherIcons
import compose.icons.feathericons.Award
import compose.icons.feathericons.Bell
import compose.icons.feathericons.Calendar
import compose.icons.feathericons.Check
import compose.icons.feathericons.ChevronRight
import compose.icons.feathericons.Edit2
import compose.icons.feathericons.MapPin
import compose.icons.feathericons.Plus
import compose.icons.feathericons.Settings
import compose.icons.feathericons.Star
import compose.icons.feathericons.UserPlus
import org.jetbrains.compose.ui.tooling.preview.Preview

class ProfileScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = koinScreenModel<ProfileScreenModel>()
        val state by screenModel.state.collectAsState()

        ProfileContent(
            state = state,
            onSettingsClick = { navigator.push(SettingsScreen()) },
        )
    }
}

@Composable
private fun ProfileContent(
    state: ProfileState,
    onSettingsClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Spacing.spacing4),
    ) {
        Spacer(modifier = Modifier.height(Spacing.spacing3))

        ProfileTopBar(onSettingsClick = onSettingsClick)

        Spacer(modifier = Modifier.height(Spacing.spacing5))

        PairedHeroCard()

        Spacer(modifier = Modifier.height(Spacing.spacing4))

        StatsGrid(statistics = state.statistics)

        if (state.badges.isNotEmpty()) {
            Spacer(modifier = Modifier.height(Spacing.spacing5))
            AchievementsSection(badges = state.badges)
        }

        if (state.recentActivities.isNotEmpty()) {
            Spacer(modifier = Modifier.height(Spacing.spacing5))
            RecentTogetherSection(activities = state.recentActivities)
        }

        Spacer(modifier = Modifier.height(Spacing.spacing8))
    }
}

@Composable
private fun ProfileTopBar(onSettingsClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = "Profile",
            style = AppTheme.typography.h2,
            color = AppTheme.colors.text,
        )

        Row(horizontalArrangement = Arrangement.spacedBy(Spacing.spacing2)) {
            RoundIconButton(onClick = {}) {
                Icon(
                    imageVector = FeatherIcons.Bell,
                    contentDescription = "Notifications",
                    tint = AppTheme.colors.text,
                    modifier = Modifier.size(18.dp),
                )
            }
            RoundIconButton(onClick = onSettingsClick) {
                Icon(
                    imageVector = FeatherIcons.Settings,
                    contentDescription = "Settings",
                    tint = AppTheme.colors.text,
                    modifier = Modifier.size(18.dp),
                )
            }
        }
    }
}

@Composable
private fun RoundIconButton(
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    Surface(
        onClick = onClick,
        shape = CircleShape,
        color = AppTheme.colors.surface,
        modifier = Modifier.size(40.dp),
    ) {
        Box(contentAlignment = Alignment.Center) {
            content()
        }
    }
}

@Composable
private fun PairedHeroCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(BorderRadius.roundedXl))
            .background(AppTheme.colors.surface),
    ) {
        Box(
            modifier = Modifier
                .size(140.dp)
                .align(Alignment.TopEnd)
                .offset(x = 40.dp, y = (-40).dp)
                .clip(CircleShape)
                .background(AppTheme.colors.primaryContainer),
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.spacing5),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ProfileAvatar(
                    initial = "E",
                    bgColor = AppTheme.colors.primary,
                    textColor = AppTheme.colors.onPrimary,
                    size = 64.dp,
                )

                Text(
                    text = "+",
                    style = AppTheme.typography.h1,
                    color = AppTheme.colors.tertiary,
                    modifier = Modifier.padding(horizontal = Spacing.spacing3),
                )

                Box {
                    ProfileAvatar(
                        initial = "J",
                        bgColor = AppTheme.colors.tertiary,
                        textColor = AppTheme.colors.onTertiary,
                        size = 64.dp,
                    )
                    Box(
                        modifier = Modifier
                            .size(14.dp)
                            .align(Alignment.BottomEnd)
                            .background(AppTheme.colors.success, shape = CircleShape)
                            .border(shape = CircleShape, width = 2.dp, color = AppTheme.colors.surface)
                        ,
                    )
                }
            }

            Spacer(modifier = Modifier.height(Spacing.spacing4))

            Text(
                text = "Emma & Jack",
                style = AppTheme.typography.display2,
                color = AppTheme.colors.text,
            )

            Spacer(modifier = Modifier.height(Spacing.spacing1))

            Text(
                text = "Together on Togedo since March 2024",
                style = AppTheme.typography.body2,
                color = AppTheme.colors.textSecondary,
            )

            Spacer(modifier = Modifier.height(Spacing.spacing5))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacing.spacing2),
            ) {
                Button(
                    modifier = Modifier.weight(1f),
                    variant = ButtonVariant.Primary,
                    onClick = {},
                ) {
                    Row {
                        Icon(
                            imageVector = FeatherIcons.Edit2,
                            contentDescription = null,
                            tint = AppTheme.colors.onPrimary,
                            modifier = Modifier.size(16.dp),
                        )
                        Spacer(modifier = Modifier.width(Spacing.spacing2))
                        Text(
                            text = "Edit profile",
                            style = AppTheme.typography.button,
                            color = AppTheme.colors.onPrimary,
                        )
                    }
                }

                Button(
                    modifier = Modifier.weight(1f),
                    variant = ButtonVariant.SecondaryOutlined,
                    onClick = {},
                ) {
                    Row {
                        Icon(
                            imageVector = FeatherIcons.UserPlus,
                            contentDescription = null,
                            tint = AppTheme.colors.text,
                            modifier = Modifier.size(16.dp),
                        )
                        Spacer(modifier = Modifier.width(Spacing.spacing2))
                        Text(
                            text = "Invite",
                            style = AppTheme.typography.button,
                            color = AppTheme.colors.text,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfileAvatar(
    initial: String,
    bgColor: Color,
    textColor: Color,
    size: Dp = 48.dp,
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
            style = AppTheme.typography.h2.copy(fontWeight = FontWeight.ExtraBold),
            color = textColor,
        )
    }
}

@Composable
private fun StatsGrid(statistics: ProfileStatistics) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Spacing.spacing2),
    ) {
        StatTile(
            value = statistics.sharedListsCount.toString(),
            label = "Lists",
            valueColor = AppTheme.colors.primary,
            modifier = Modifier.weight(1f),
        )
        StatTile(
            value = statistics.activitiesCount.toString(),
            label = "Ideas",
            valueColor = AppTheme.colors.secondary,
            modifier = Modifier.weight(1f),
        )
        StatTile(
            value = statistics.completedCount.toString(),
            label = "Done",
            valueColor = AppTheme.colors.tertiary,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun StatTile(
    value: String,
    label: String,
    valueColor: Color,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(BorderRadius.roundedLg))
            .background(AppTheme.colors.surface)
            .padding(vertical = Spacing.spacing4, horizontal = Spacing.spacing3),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = value,
            style = AppTheme.typography.display1.copy(fontSize = 28.sp, lineHeight = 34.sp),
            color = valueColor,
        )
        Spacer(modifier = Modifier.height(Spacing.spacing1))
        Text(
            text = label,
            style = AppTheme.typography.label2,
            color = AppTheme.colors.textSecondary,
        )
    }
}

@Composable
private fun AchievementsSection(badges: List<Badge>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Achievements",
            style = AppTheme.typography.h3,
            color = AppTheme.colors.text,
        )

        Spacer(modifier = Modifier.height(Spacing.spacing3))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.spacing2),
        ) {
            badges.forEach { badge ->
                val (icon, color) = when (badge.type) {
                    BadgeType.Dreamer -> FeatherIcons.Star to AppTheme.colors.primary
                    BadgeType.Explorer -> FeatherIcons.MapPin to AppTheme.colors.secondary
                    BadgeType.Achiever -> FeatherIcons.Award to AppTheme.colors.tertiary
                }

                BadgeTile(
                    icon = icon,
                    title = badge.title,
                    level = "Level ${badge.level}",
                    color = color,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
private fun BadgeTile(
    icon: ImageVector,
    title: String,
    level: String,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(BorderRadius.roundedMd))
            .background(AppTheme.colors.surface)
            .padding(Spacing.spacing3),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(color.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(20.dp),
            )
        }

        Spacer(modifier = Modifier.height(Spacing.spacing2))

        Text(
            text = title,
            style = AppTheme.typography.label1,
            color = AppTheme.colors.text,
        )

        Text(
            text = level,
            style = AppTheme.typography.body3,
            color = AppTheme.colors.textSecondary,
        )
    }
}

@Composable
private fun RecentTogetherSection(activities: List<RecentActivity>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Recent together",
                style = AppTheme.typography.h3,
                color = AppTheme.colors.text,
            )

            Surface(
                onClick = {},
                shape = RoundedCornerShape(BorderRadius.roundedSm),
                color = AppTheme.colors.transparent,
            ) {
                Row(
                    modifier = Modifier.padding(
                        horizontal = Spacing.spacing2,
                        vertical = Spacing.spacing1
                    ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "View all",
                        style = AppTheme.typography.label2,
                        color = AppTheme.colors.primary,
                    )
                    Icon(
                        imageVector = FeatherIcons.ChevronRight,
                        contentDescription = null,
                        tint = AppTheme.colors.primary,
                        modifier = Modifier.size(16.dp),
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(Spacing.spacing3))

        Column(verticalArrangement = Arrangement.spacedBy(Spacing.spacing2)) {
            activities.forEach { activity ->
                val (icon, iconColor) = when (activity.type) {
                    RecentActivityType.Completed -> FeatherIcons.Check to AppTheme.colors.success
                    RecentActivityType.Added -> FeatherIcons.Plus to AppTheme.colors.secondary
                    RecentActivityType.Planned -> FeatherIcons.Calendar to AppTheme.colors.tertiary
                }

                RecentActivityRow(
                    title = activity.title,
                    date = activity.date,
                    icon = icon,
                    iconColor = iconColor,
                )
            }
        }
    }
}

@Composable
private fun RecentActivityRow(
    title: String,
    date: String,
    icon: ImageVector,
    iconColor: Color,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(BorderRadius.roundedMd))
            .background(AppTheme.colors.surface)
            .padding(Spacing.spacing4),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(iconColor.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(20.dp),
            )
        }

        Spacer(modifier = Modifier.width(Spacing.spacing3))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = AppTheme.typography.body1,
                color = AppTheme.colors.text,
            )
            Spacer(modifier = Modifier.height(Spacing.spacing1))
            Text(
                text = date,
                style = AppTheme.typography.body3,
                color = AppTheme.colors.textSecondary,
            )
        }

        Icon(
            imageVector = FeatherIcons.ChevronRight,
            contentDescription = null,
            tint = AppTheme.colors.textSecondary,
        )
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    AppTheme {
        ProfileContent(
            state = ProfileState(
                badges = listOf(
                    Badge(id = "1", title = "Dreamer", level = 3, type = BadgeType.Dreamer),
                    Badge(id = "2", title = "Explorer", level = 2, type = BadgeType.Explorer),
                    Badge(id = "3", title = "Achiever", level = 1, type = BadgeType.Achiever),
                ),
                recentActivities = listOf(
                    RecentActivity(
                        id = "1",
                        title = "Sunset Picnic at the Beach",
                        date = "Completed 2 days ago",
                        type = RecentActivityType.Completed
                    ),
                    RecentActivity(
                        id = "2",
                        title = "Japanese cooking class",
                        date = "Added 5 days ago",
                        type = RecentActivityType.Added
                    ),
                ),
            ),
            onSettingsClick = {},
        )
    }
}
