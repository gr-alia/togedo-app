package com.togedo.app.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.togedo.app.designsystem.AppTheme
import com.togedo.app.designsystem.BorderRadius
import com.togedo.app.designsystem.Spacing
import com.togedo.app.designsystem.components.Icon
import com.togedo.app.designsystem.components.Surface
import com.togedo.app.designsystem.components.Text
import compose.icons.FeatherIcons
import compose.icons.feathericons.Award
import compose.icons.feathericons.Calendar
import compose.icons.feathericons.Check
import compose.icons.feathericons.ChevronRight
import compose.icons.feathericons.Edit2
import compose.icons.feathericons.Heart
import compose.icons.feathericons.MapPin
import compose.icons.feathericons.Plus
import compose.icons.feathericons.Settings
import compose.icons.feathericons.Star
import compose.icons.feathericons.User

class ProfileScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = koinScreenModel<ProfileScreenModel>()
        val state by screenModel.state.collectAsState()
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colors.background)
                .verticalScroll(scrollState)
                .padding(horizontal = Spacing.spacing4, vertical = Spacing.spacing6)
        ) {
            ProfileHeader(user = state.user)

            Spacer(modifier = Modifier.height(Spacing.spacing6))

            StatisticsSection(statistics = state.statistics)

            Spacer(modifier = Modifier.height(Spacing.spacing6))

            ActionButtons(
                onEditProfile = { },
                onSettings = { navigator.push(SettingsScreen()) }
            )

            Spacer(modifier = Modifier.height(Spacing.spacing6))

            state.partner?.let { partner ->
                PartnerSection(partner = partner)
                Spacer(modifier = Modifier.height(Spacing.spacing6))
            }

            if (state.badges.isNotEmpty()) {
                BadgesSection(badges = state.badges)
                Spacer(modifier = Modifier.height(Spacing.spacing6))
            }

            if (state.recentActivities.isNotEmpty()) {
                RecentActivitySection(activities = state.recentActivities)
                Spacer(modifier = Modifier.height(Spacing.spacing8))
            }
        }
    }
}

@Composable
private fun ProfileHeader(user: UserProfile) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(AppTheme.colors.secondary),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = FeatherIcons.User,
                contentDescription = "Profile picture",
                tint = AppTheme.colors.onSecondary,
                modifier = Modifier.size(64.dp)
            )
        }

        Spacer(modifier = Modifier.height(Spacing.spacing4))

        Text(
            text = user.name,
            style = AppTheme.typography.h1,
            color = AppTheme.colors.text
        )

        Spacer(modifier = Modifier.height(Spacing.spacing1))

        Text(
            text = user.email,
            style = AppTheme.typography.body2,
            color = AppTheme.colors.textSecondary
        )
    }
}

@Composable
private fun StatisticsSection(statistics: ProfileStatistics) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(BorderRadius.roundedLg),
        color = AppTheme.colors.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.spacing6),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatItem(
                value = statistics.sharedListsCount.toString(),
                label = "Shared Lists",
                modifier = Modifier.weight(1f)
            )

            Box(
                modifier = Modifier
                    .width(1.dp)
                    .height(48.dp)
                    .background(AppTheme.colors.outline)
            )

            StatItem(
                value = statistics.activitiesCount.toString(),
                label = "Activities",
                modifier = Modifier.weight(1f)
            )

            Box(
                modifier = Modifier
                    .width(1.dp)
                    .height(48.dp)
                    .background(AppTheme.colors.outline)
            )

            StatItem(
                value = statistics.completedCount.toString(),
                label = "Completed",
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun StatItem(
    value: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = AppTheme.typography.h1,
            color = AppTheme.colors.text
        )

        Spacer(modifier = Modifier.height(Spacing.spacing1))

        Text(
            text = label,
            style = AppTheme.typography.body3,
            color = AppTheme.colors.textSecondary
        )
    }
}

@Composable
private fun ActionButtons(
    onEditProfile: () -> Unit,
    onSettings: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Spacing.spacing2)
    ) {
        Surface(
            onClick = onEditProfile,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(BorderRadius.roundedMd),
            color = AppTheme.colors.primary
        ) {
            Row(
                modifier = Modifier.padding(Spacing.spacing4),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = FeatherIcons.Edit2,
                    contentDescription = null,
                    tint = AppTheme.colors.onPrimary,
                    modifier = Modifier.size(20.dp)
                )

                Spacer(modifier = Modifier.width(Spacing.spacing2))

                Text(
                    text = "Edit Profile",
                    style = AppTheme.typography.button,
                    color = AppTheme.colors.onPrimary
                )
            }
        }

        Surface(
            onClick = onSettings,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(BorderRadius.roundedMd),
            color = AppTheme.colors.surface
        ) {
            Row(
                modifier = Modifier.padding(Spacing.spacing4),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = FeatherIcons.Settings,
                    contentDescription = null,
                    tint = AppTheme.colors.text,
                    modifier = Modifier.size(20.dp)
                )

                Spacer(modifier = Modifier.width(Spacing.spacing2))

                Text(
                    text = "Settings",
                    style = AppTheme.typography.button,
                    color = AppTheme.colors.text
                )
            }
        }
    }
}

@Composable
private fun PartnerSection(partner: Partner) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(BorderRadius.roundedLg),
        color = AppTheme.colors.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.spacing4),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(AppTheme.colors.tertiary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = FeatherIcons.Heart,
                    contentDescription = null,
                    tint = AppTheme.colors.onTertiary,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(Spacing.spacing3))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Together with",
                    style = AppTheme.typography.body3,
                    color = AppTheme.colors.textSecondary
                )

                Spacer(modifier = Modifier.height(Spacing.spacing1))

                Text(
                    text = "${partner.name} 💕",
                    style = AppTheme.typography.h4,
                    color = AppTheme.colors.text
                )
            }

            Icon(
                imageVector = FeatherIcons.ChevronRight,
                contentDescription = null,
                tint = AppTheme.colors.textSecondary
            )
        }
    }
}

@Composable
private fun BadgesSection(badges: List<Badge>) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Achievements",
            style = AppTheme.typography.h3,
            color = AppTheme.colors.text,
            modifier = Modifier.padding(horizontal = Spacing.spacing1)
        )

        Spacer(modifier = Modifier.height(Spacing.spacing3))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.spacing2)
        ) {
            badges.forEach { badge ->
                val (icon, color) = when (badge.type) {
                    BadgeType.Dreamer -> FeatherIcons.Star to AppTheme.colors.brandEarthYellow
                    BadgeType.Explorer -> FeatherIcons.MapPin to AppTheme.colors.brandVerdigris
                    BadgeType.Achiever -> FeatherIcons.Award to AppTheme.colors.brandUtOrange
                }

                BadgeCard(
                    icon = icon,
                    title = badge.title,
                    level = "Level ${badge.level}",
                    color = color
                )
            }
        }
    }
}

@Composable
private fun RowScope.BadgeCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    level: String,
    color: androidx.compose.ui.graphics.Color
) {
    Surface(
        modifier = Modifier.weight(1f),
        shape = RoundedCornerShape(BorderRadius.roundedMd),
        color = AppTheme.colors.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.spacing3),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(Spacing.spacing2))

            Text(
                text = title,
                style = AppTheme.typography.label1,
                color = AppTheme.colors.text
            )

            Text(
                text = level,
                style = AppTheme.typography.body3,
                color = AppTheme.colors.textSecondary
            )
        }
    }
}

@Composable
private fun RecentActivitySection(activities: List<RecentActivity>) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.spacing1),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Recent Activity",
                style = AppTheme.typography.h3,
                color = AppTheme.colors.text
            )

            Surface(
                onClick = { },
                shape = RoundedCornerShape(BorderRadius.roundedSm),
                color = AppTheme.colors.transparent
            ) {
                Row(
                    modifier = Modifier.padding(
                        horizontal = Spacing.spacing2,
                        vertical = Spacing.spacing1
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "View All",
                        style = AppTheme.typography.label2,
                        color = AppTheme.colors.primary
                    )

                    Icon(
                        imageVector = FeatherIcons.ChevronRight,
                        contentDescription = null,
                        tint = AppTheme.colors.primary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(Spacing.spacing3))

        Column(
            verticalArrangement = Arrangement.spacedBy(Spacing.spacing2)
        ) {
            activities.forEach { activity ->
                val (icon, iconColor) = when (activity.type) {
                    RecentActivityType.Completed -> FeatherIcons.Check to AppTheme.colors.success
                    RecentActivityType.Added -> FeatherIcons.Plus to AppTheme.colors.secondary
                    RecentActivityType.Planned -> FeatherIcons.Calendar to AppTheme.colors.tertiary
                }

                ActivityCard(
                    title = activity.title,
                    date = activity.date,
                    icon = icon,
                    iconColor = iconColor
                )
            }
        }
    }
}

@Composable
private fun ActivityCard(
    title: String,
    date: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: androidx.compose.ui.graphics.Color
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(BorderRadius.roundedMd),
        color = AppTheme.colors.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.spacing4),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(iconColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(Spacing.spacing3))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = AppTheme.typography.body1,
                    color = AppTheme.colors.text
                )

                Spacer(modifier = Modifier.height(Spacing.spacing1))

                Text(
                    text = date,
                    style = AppTheme.typography.body3,
                    color = AppTheme.colors.textSecondary
                )
            }

            Icon(
                imageVector = FeatherIcons.ChevronRight,
                contentDescription = null,
                tint = AppTheme.colors.textSecondary
            )
        }
    }
}
