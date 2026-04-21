package com.togedo.app.ui.activity.list

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.togedo.app.designsystem.AppTheme
import com.togedo.app.designsystem.BorderRadius
import com.togedo.app.designsystem.Spacing
import com.togedo.app.designsystem.components.Icon
import com.togedo.app.designsystem.components.Surface
import com.togedo.app.designsystem.components.Text
import compose.icons.FeatherIcons
import compose.icons.feathericons.ChevronRight
import compose.icons.feathericons.Edit2
import compose.icons.feathericons.Plus
import compose.icons.feathericons.Trash2
import compose.icons.feathericons.X
import org.jetbrains.compose.ui.tooling.preview.Preview

class ActivityListSettingsScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        ActivityListSettingsContent(onDoneClick = { navigator.pop() })
    }
}

@Composable
private fun ActivityListSettingsContent(onDoneClick: () -> Unit) {
    var selectedSort by remember { mutableStateOf("Recent") }
    var showIdeas by remember { mutableStateOf(true) }
    var showPlanned by remember { mutableStateOf(true) }
    var showDone by remember { mutableStateOf(false) }
    var showCanceled by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Spacing.spacing4),
    ) {
        Spacer(modifier = Modifier.height(Spacing.spacing3))

        ListSettingsTopBar(onDoneClick = onDoneClick)

        Spacer(modifier = Modifier.height(Spacing.spacing5))

        ListIdentityCard()

        Spacer(modifier = Modifier.height(Spacing.spacing5))

        SortSection(selectedSort = selectedSort, onSortSelected = { selectedSort = it })

        Spacer(modifier = Modifier.height(Spacing.spacing5))

        ShowByDefaultSection(
            showIdeas = showIdeas,
            showPlanned = showPlanned,
            showDone = showDone,
            showCanceled = showCanceled,
            onShowIdeasChange = { showIdeas = it },
            onShowPlannedChange = { showPlanned = it },
            onShowDoneChange = { showDone = it },
            onShowCanceledChange = { showCanceled = it },
        )

        Spacer(modifier = Modifier.height(Spacing.spacing5))

        SharedWithSection()

        Spacer(modifier = Modifier.height(Spacing.spacing5))

        DangerZoneSection()

        Spacer(modifier = Modifier.height(Spacing.spacing8))
    }
}

@Composable
private fun ListSettingsTopBar(onDoneClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Surface(
            onClick = onDoneClick,
            shape = CircleShape,
            color = AppTheme.colors.surface,
            modifier = Modifier.size(40.dp),
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = FeatherIcons.X,
                    contentDescription = "Close",
                    tint = AppTheme.colors.text,
                    modifier = Modifier.size(18.dp),
                )
            }
        }

        Text(
            text = "List settings",
            style = AppTheme.typography.h2,
            color = AppTheme.colors.text,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = Spacing.spacing3),
        )

        Text(
            text = "Done",
            style = AppTheme.typography.label1,
            color = AppTheme.colors.primary,
            modifier = Modifier.clickable(onClick = onDoneClick),
        )
    }
}

@Composable
private fun ListIdentityCard() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(BorderRadius.roundedXl))
            .background(AppTheme.colors.surface)
            .padding(Spacing.spacing4),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.spacing3),
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(BorderRadius.roundedMd))
                .background(AppTheme.colors.primaryContainer),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "✿",
                style = AppTheme.typography.h2,
                color = AppTheme.colors.primary,
            )
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Our list",
                style = AppTheme.typography.h2,
                color = AppTheme.colors.text,
            )
            Text(
                text = "Shared with Jack · 47 ideas",
                style = AppTheme.typography.body3,
                color = AppTheme.colors.textSecondary,
            )
        }

        Icon(
            imageVector = FeatherIcons.Edit2,
            contentDescription = "Edit",
            tint = AppTheme.colors.textDisabled,
            modifier = Modifier.size(18.dp),
        )
    }
}

@Composable
private fun SortSection(selectedSort: String, onSortSelected: (String) -> Unit) {
    val sortOptions = listOf("Recent", "Alphabetical", "Status", "Date added")

    SectionHeader(title = "SORT BY")

    Spacer(modifier = Modifier.height(Spacing.spacing2))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(BorderRadius.roundedLg))
            .background(AppTheme.colors.surface)
            .padding(Spacing.spacing3),
        verticalArrangement = Arrangement.spacedBy(Spacing.spacing2),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.spacing2),
        ) {
            sortOptions.take(2).forEach { option ->
                SortPill(
                    label = option,
                    selected = selectedSort == option,
                    onClick = { onSortSelected(option) },
                    modifier = Modifier.weight(1f),
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.spacing2),
        ) {
            sortOptions.drop(2).forEach { option ->
                SortPill(
                    label = option,
                    selected = selectedSort == option,
                    onClick = { onSortSelected(option) },
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
private fun SortPill(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val bgColor by animateColorAsState(
        targetValue = if (selected) AppTheme.colors.primary else AppTheme.colors.transparent,
    )
    val textColor by animateColorAsState(
        targetValue = if (selected) AppTheme.colors.onPrimary else AppTheme.colors.textSecondary,
    )

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(BorderRadius.roundedMd))
            .background(bgColor)
            .clickable(onClick = onClick)
            .padding(vertical = Spacing.spacing2, horizontal = Spacing.spacing3),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = label,
            style = AppTheme.typography.label2.copy(fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal),
            color = textColor,
        )
    }
}

@Composable
private fun ShowByDefaultSection(
    showIdeas: Boolean,
    showPlanned: Boolean,
    showDone: Boolean,
    showCanceled: Boolean,
    onShowIdeasChange: (Boolean) -> Unit,
    onShowPlannedChange: (Boolean) -> Unit,
    onShowDoneChange: (Boolean) -> Unit,
    onShowCanceledChange: (Boolean) -> Unit,
) {
    SectionHeader(title = "SHOW BY DEFAULT")

    Spacer(modifier = Modifier.height(Spacing.spacing2))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(BorderRadius.roundedLg))
            .background(AppTheme.colors.surface)
            .padding(horizontal = Spacing.spacing4),
    ) {
        FilterToggleRow(
            title = "Ideas",
            subtitle = "32 ideas",
            checked = showIdeas,
            onCheckedChange = onShowIdeasChange
        )
        RowDivider()
        FilterToggleRow(
            title = "Planned",
            subtitle = "8 planned",
            checked = showPlanned,
            onCheckedChange = onShowPlannedChange
        )
        RowDivider()
        FilterToggleRow(
            title = "Done",
            subtitle = "23 done",
            checked = showDone,
            onCheckedChange = onShowDoneChange
        )
        RowDivider()
        FilterToggleRow(
            title = "Canceled",
            subtitle = "4 canceled",
            checked = showCanceled,
            onCheckedChange = onShowCanceledChange
        )
    }
}

@Composable
private fun FilterToggleRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(vertical = Spacing.spacing3),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = AppTheme.typography.body1,
                color = AppTheme.colors.text,
            )
            Text(
                text = subtitle,
                style = AppTheme.typography.body3,
                color = AppTheme.colors.textSecondary,
            )
        }

        MiniToggle(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@Composable
private fun SharedWithSection() {
    SectionHeader(title = "SHARED WITH")

    Spacer(modifier = Modifier.height(Spacing.spacing2))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(BorderRadius.roundedLg))
            .background(AppTheme.colors.surface)
            .padding(horizontal = Spacing.spacing4),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Spacing.spacing3),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.spacing3),
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(AppTheme.colors.tertiary),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "J",
                    style = AppTheme.typography.label1.copy(fontWeight = FontWeight.Bold),
                    color = AppTheme.colors.onTertiary,
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Jack",
                    style = AppTheme.typography.body1,
                    color = AppTheme.colors.text,
                )
                Text(
                    text = "Can add & plan",
                    style = AppTheme.typography.body3,
                    color = AppTheme.colors.textSecondary,
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(BorderRadius.roundedFull))
                    .background(AppTheme.colors.secondaryContainer)
                    .padding(horizontal = 10.dp, vertical = 4.dp),
            ) {
                Text(
                    text = "Partner",
                    style = AppTheme.typography.label2,
                    color = AppTheme.colors.secondary,
                )
            }
        }

        RowDivider()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {}
                .padding(vertical = Spacing.spacing3),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.spacing3),
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(AppTheme.colors.primaryContainer),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = FeatherIcons.Plus,
                    contentDescription = null,
                    tint = AppTheme.colors.primary,
                    modifier = Modifier.size(18.dp),
                )
            }

            Text(
                text = "Invite someone",
                style = AppTheme.typography.body1,
                color = AppTheme.colors.primary,
                modifier = Modifier.weight(1f),
            )

            Icon(
                imageVector = FeatherIcons.ChevronRight,
                contentDescription = null,
                tint = AppTheme.colors.textDisabled,
                modifier = Modifier.size(16.dp),
            )
        }
    }
}

@Composable
private fun DangerZoneSection() {
    SectionHeader(title = "DANGER ZONE")

    Spacer(modifier = Modifier.height(Spacing.spacing2))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(BorderRadius.roundedLg))
            .background(AppTheme.colors.surface)
            .clickable {}
            .padding(horizontal = Spacing.spacing4, vertical = Spacing.spacing3),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.spacing3),
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(BorderRadius.roundedMd))
                .background(AppTheme.colors.error.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = FeatherIcons.Trash2,
                contentDescription = null,
                tint = AppTheme.colors.error,
                modifier = Modifier.size(16.dp),
            )
        }

        Text(
            text = "Archive list",
            style = AppTheme.typography.body1,
            color = AppTheme.colors.error,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        style = AppTheme.typography.label2,
        color = AppTheme.colors.textSecondary,
        modifier = Modifier.padding(start = Spacing.spacing2),
    )
}

@Composable
private fun RowDivider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(AppTheme.colors.outlineVariant),
    )
}

@Composable
private fun MiniToggle(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    val trackColor by animateColorAsState(
        targetValue = if (checked) AppTheme.colors.secondary else AppTheme.colors.outline,
    )

    Box(
        modifier = Modifier
            .size(width = 44.dp, height = 26.dp)
            .clip(RoundedCornerShape(13.dp))
            .background(trackColor)
            .clickable { onCheckedChange(!checked) },
        contentAlignment = Alignment.CenterStart,
    ) {
        Box(
            modifier = Modifier
                .padding(3.dp)
                .size(20.dp)
                .offset(x = if (checked) 18.dp else 0.dp)
                .clip(CircleShape)
                .background(Color.White),
        )
    }
}

@Preview
@Composable
fun ActivityListSettingsScreenPreview() {
    AppTheme {
        ActivityListSettingsContent(onDoneClick = {})
    }
}
