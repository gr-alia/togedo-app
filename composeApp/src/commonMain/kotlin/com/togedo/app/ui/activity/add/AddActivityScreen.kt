package com.togedo.app.ui.activity.add

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.SolidColor
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
import com.togedo.app.designsystem.components.Surface
import com.togedo.app.designsystem.components.Text
import com.togedo.app.ui.activity.list.ActivityUiModel
import compose.icons.FeatherIcons
import compose.icons.feathericons.ArrowLeft
import org.jetbrains.compose.ui.tooling.preview.Preview
import compose.icons.feathericons.Calendar
import compose.icons.feathericons.Check
import compose.icons.feathericons.Heart
import compose.icons.feathericons.Image
import compose.icons.feathericons.MapPin
import compose.icons.feathericons.Users

class AddActivityScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = koinScreenModel<AddActivityScreenModel>()
        val state by screenModel.state.collectAsState()

        LaunchedEffect(state.showSuccessMessage) {
            if (state.showSuccessMessage) {
                screenModel.clearSuccessMessage()
            }
        }

        AddActivityContent(
            state = state,
            onBackClick = { navigator.pop() },
            onTitleChanged = screenModel::onTitleChanged,
            onDescriptionChanged = screenModel::onDescriptionChanged,
            onStatusSelected = screenModel::onStatusSelected,
            onCategoryToggled = screenModel::onCategoryToggled,
            onLocationChanged = screenModel::onLocationChanged,
            onDateClick = { screenModel.onShowDatePicker(true) },
            onSaveClick = { screenModel.validateAndSave { navigator.pop() } },
            onCancelClick = { navigator.pop() },
        )
    }
}

@Composable
private fun AddActivityContent(
    state: AddActivityState,
    onBackClick: () -> Unit,
    onTitleChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onStatusSelected: (ActivityUiModel.ActivityStatus) -> Unit,
    onCategoryToggled: (ActivityUiModel.ActivityTag) -> Unit,
    onLocationChanged: (String) -> Unit,
    onDateClick: () -> Unit,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.background),
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Spacing.spacing4),
        ) {
            Spacer(modifier = Modifier.height(Spacing.spacing3))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Surface(
                    onClick = onBackClick,
                    shape = CircleShape,
                    color = AppTheme.colors.surface,
                    modifier = Modifier.size(40.dp),
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = FeatherIcons.ArrowLeft,
                            contentDescription = "Back",
                            tint = AppTheme.colors.text,
                            modifier = Modifier.size(20.dp),
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = if (state.title.isNotBlank()) "Draft saved" else "",
                    style = AppTheme.typography.label1,
                    color = AppTheme.colors.textSecondary,
                )
            }

            Spacer(modifier = Modifier.height(Spacing.spacing5))

            Text(
                text = "New idea",
                style = AppTheme.typography.display2,
                color = AppTheme.colors.text,
            )
            Text(
                text = "Drop it in — the smallest thought counts.",
                style = AppTheme.typography.body2,
                color = AppTheme.colors.textSecondary,
                modifier = Modifier.padding(top = 4.dp),
            )

            Spacer(modifier = Modifier.height(Spacing.spacing6))

            TitleSection(
                title = state.title,
                onTitleChanged = onTitleChanged,
                isError = state.titleError,
            )

            Spacer(modifier = Modifier.height(Spacing.spacing5))

            DescriptionSection(
                description = state.description,
                onDescriptionChanged = onDescriptionChanged,
            )

            Spacer(modifier = Modifier.height(Spacing.spacing5))

            StatusSection(
                selectedStatus = state.selectedStatus,
                onStatusSelected = onStatusSelected,
            )

            Spacer(modifier = Modifier.height(Spacing.spacing5))

            VibesSection(
                selectedCategories = state.selectedCategories,
                onCategoryToggled = onCategoryToggled,
            )

            Spacer(modifier = Modifier.height(Spacing.spacing5))

            MetaGrid(
                location = state.location,
                date = state.date,
                onLocationChanged = onLocationChanged,
                onDateClick = onDateClick,
            )

            Spacer(modifier = Modifier.height(Spacing.spacing8))
        }

        BottomBar(
            isSaving = state.isSaving,
            onSaveClick = onSaveClick,
            onCancelClick = onCancelClick,
        )
    }
}

@Composable
private fun TitleSection(
    title: String,
    onTitleChanged: (String) -> Unit,
    isError: Boolean,
) {
    val borderColor = when {
        isError -> AppTheme.colors.error
        else -> AppTheme.colors.primary
    }

    Column {
        Text(
            text = "What is it? *",
            style = AppTheme.typography.label1,
            color = if (isError) AppTheme.colors.error else AppTheme.colors.textSecondary,
        )

        Spacer(modifier = Modifier.height(Spacing.spacing2))

        val primaryColor = borderColor
        BasicTextField(
            value = title,
            onValueChange = onTitleChanged,
            textStyle = AppTheme.typography.h2.copy(
                color = AppTheme.colors.text,
                fontWeight = FontWeight.Bold,
            ),
            cursorBrush = SolidColor(AppTheme.colors.primary),
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 48.dp)
                .drawBehind {
                    val y = size.height
                    drawLine(
                        color = primaryColor,
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = 2.dp.toPx(),
                    )
                }
                .padding(bottom = Spacing.spacing2),
            decorationBox = { innerTextField ->
                Box {
                    if (title.isEmpty()) {
                        Text(
                            text = "e.g., Sunset picnic at the park",
                            style = AppTheme.typography.h2.copy(fontWeight = FontWeight.Bold),
                            color = AppTheme.colors.textDisabled,
                        )
                    }
                    innerTextField()
                }
            },
        )

        if (isError) {
            Text(
                text = "Title is required",
                style = AppTheme.typography.body3,
                color = AppTheme.colors.error,
                modifier = Modifier.padding(top = 4.dp),
            )
        }
    }
}

@Composable
private fun DescriptionSection(
    description: String,
    onDescriptionChanged: (String) -> Unit,
) {
    Column {
        Text(
            text = "A little more",
            style = AppTheme.typography.label1,
            color = AppTheme.colors.textSecondary,
        )

        Spacer(modifier = Modifier.height(Spacing.spacing2))

        BasicTextField(
            value = description,
            onValueChange = onDescriptionChanged,
            textStyle = AppTheme.typography.body2.copy(color = AppTheme.colors.text),
            cursorBrush = SolidColor(AppTheme.colors.primary),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(BorderRadius.roundedMd))
                .background(AppTheme.colors.surface)
                .padding(Spacing.spacing3)
                .defaultMinSize(minHeight = 72.dp),
            decorationBox = { innerTextField ->
                Box {
                    if (description.isEmpty()) {
                        Text(
                            text = "Wine, cheese, that blanket we never use…",
                            style = AppTheme.typography.body2,
                            color = AppTheme.colors.textDisabled,
                        )
                    }
                    innerTextField()
                }
            },
        )
    }
}

@Composable
private fun StatusSection(
    selectedStatus: ActivityUiModel.ActivityStatus,
    onStatusSelected: (ActivityUiModel.ActivityStatus) -> Unit,
) {
    Column {
        Text(
            text = "Status",
            style = AppTheme.typography.label1,
            color = AppTheme.colors.textSecondary,
        )

        Spacer(modifier = Modifier.height(Spacing.spacing2))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.spacing2),
        ) {
            listOf(
                ActivityUiModel.ActivityStatus.Idea to "Idea",
                ActivityUiModel.ActivityStatus.Planned to "Planned",
                ActivityUiModel.ActivityStatus.Done to "Done",
            ).forEach { (status, label) ->
                val isSelected = selectedStatus == status
                Surface(
                    onClick = { onStatusSelected(status) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(BorderRadius.roundedMd),
                    color = if (isSelected) status.statusBackgroundColor else AppTheme.colors.surface,
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = Spacing.spacing3),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        if (isSelected) {
                            Icon(
                                imageVector = FeatherIcons.Check,
                                contentDescription = null,
                                tint = status.statusColor,
                                modifier = Modifier.size(14.dp),
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                        Text(
                            text = label,
                            style = AppTheme.typography.label1,
                            color = if (isSelected) status.statusColor else AppTheme.colors.textSecondary,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun VibesSection(
    selectedCategories: Set<ActivityUiModel.ActivityTag>,
    onCategoryToggled: (ActivityUiModel.ActivityTag) -> Unit,
) {
    Column {
        Text(
            text = "Vibes",
            style = AppTheme.typography.label1,
            color = AppTheme.colors.textSecondary,
        )

        Spacer(modifier = Modifier.height(Spacing.spacing2))

        androidx.compose.foundation.layout.FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.spacing2),
            verticalArrangement = Arrangement.spacedBy(Spacing.spacing2),
        ) {
            CategoryOption.entries.forEach { category ->
                val isSelected = selectedCategories.contains(category.tag)
                Chip(
                    onClick = { onCategoryToggled(category.tag) },
                    selected = isSelected,
                    colors = ChipDefaults.chipColors(
                        selectedContainerColor = AppTheme.colors.secondary,
                        selectedContentColor = AppTheme.colors.onSecondary,
                    ),
                ) {
                    Text(
                        text = if (isSelected) "✓ ${category.displayName}" else category.displayName,
                        style = AppTheme.typography.label2,
                    )
                }
            }
        }
    }
}

@Composable
private fun MetaGrid(
    location: String,
    date: String,
    onLocationChanged: (String) -> Unit,
    onDateClick: () -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(Spacing.spacing2)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.spacing2),
        ) {
            MetaTile(
                icon = {
                    Icon(
                        imageVector = FeatherIcons.MapPin,
                        contentDescription = null,
                        tint = AppTheme.colors.secondary,
                        modifier = Modifier.size(16.dp),
                    )
                },
                label = "Location",
                value = location.ifBlank { "Add location" },
                valueColor = if (location.isBlank()) AppTheme.colors.textDisabled else AppTheme.colors.text,
                modifier = Modifier.weight(1f),
                onClick = {},
            )
            MetaTile(
                icon = {
                    Icon(
                        imageVector = FeatherIcons.Calendar,
                        contentDescription = null,
                        tint = AppTheme.colors.primary,
                        modifier = Modifier.size(16.dp),
                    )
                },
                label = "Date",
                value = date.ifBlank { "Pick a date" },
                valueColor = if (date.isBlank()) AppTheme.colors.textDisabled else AppTheme.colors.text,
                containerColor = if (date.isNotBlank()) AppTheme.colors.primaryContainer else AppTheme.colors.surface,
                modifier = Modifier.weight(1f),
                onClick = onDateClick,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.spacing2),
        ) {
            MetaTile(
                icon = {
                    Icon(
                        imageVector = FeatherIcons.Image,
                        contentDescription = null,
                        tint = AppTheme.colors.secondary,
                        modifier = Modifier.size(16.dp),
                    )
                },
                label = "Photo",
                value = "Add one",
                valueColor = AppTheme.colors.textDisabled,
                modifier = Modifier.weight(1f),
                onClick = {},
            )
            MetaTile(
                icon = {
                    Icon(
                        imageVector = FeatherIcons.Users,
                        contentDescription = null,
                        tint = AppTheme.colors.secondary,
                        modifier = Modifier.size(16.dp),
                    )
                },
                label = "Share",
                value = "Just us",
                valueColor = AppTheme.colors.text,
                modifier = Modifier.weight(1f),
                onClick = {},
            )
        }
    }
}

@Composable
private fun MetaTile(
    icon: @Composable () -> Unit,
    label: String,
    value: String,
    valueColor: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier,
    containerColor: androidx.compose.ui.graphics.Color = AppTheme.colors.surface,
    onClick: () -> Unit,
) {
    Surface(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(BorderRadius.roundedMd),
        color = containerColor,
    ) {
        Column(modifier = Modifier.padding(Spacing.spacing3)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Spacing.spacing2),
            ) {
                icon()
                Text(
                    text = label,
                    style = AppTheme.typography.label2,
                    color = AppTheme.colors.textSecondary,
                )
            }
            Spacer(modifier = Modifier.height(Spacing.spacing1))
            Text(
                text = value,
                style = AppTheme.typography.h4,
                color = valueColor,
            )
        }
    }
}

@Composable
private fun BottomBar(
    isSaving: Boolean,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppTheme.colors.background)
            .padding(horizontal = Spacing.spacing5, vertical = Spacing.spacing3),
        horizontalArrangement = Arrangement.spacedBy(Spacing.spacing2),
    ) {
        Surface(
            onClick = onCancelClick,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(BorderRadius.roundedLg),
            color = AppTheme.colors.surface,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Cancel",
                    style = AppTheme.typography.button,
                    color = AppTheme.colors.text,
                )
            }
        }

        Surface(
            onClick = if (isSaving) ({}) else onSaveClick,
            modifier = Modifier.weight(2f),
            shape = RoundedCornerShape(BorderRadius.roundedLg),
            color = if (isSaving) AppTheme.colors.disabledContainer else AppTheme.colors.primary,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = FeatherIcons.Heart,
                    contentDescription = null,
                    tint = if (isSaving) AppTheme.colors.textDisabled else AppTheme.colors.onPrimary,
                    modifier = Modifier.size(18.dp),
                )
                Spacer(modifier = Modifier.width(Spacing.spacing2))
                Text(
                    text = if (isSaving) "Saving…" else "Save idea",
                    style = AppTheme.typography.button,
                    color = if (isSaving) AppTheme.colors.textDisabled else AppTheme.colors.onPrimary,
                )
            }
        }
    }
}

@Preview
@Composable
fun AddActivityScreenPreview() {
    AppTheme {
        AddActivityContent(
            state = AddActivityState(
                title = "Sunset picnic at Eidsvold",
                description = "Wine, cheese, that blanket we never use.",
                selectedStatus = ActivityUiModel.ActivityStatus.Planned,
                selectedCategories = setOf(
                    ActivityUiModel.ActivityTag.Romantic,
                    ActivityUiModel.ActivityTag.Chill,
                    ActivityUiModel.ActivityTag.Nature,
                ),
                location = "Eidsvold park",
                date = "Fri · 6pm",
            ),
            onBackClick = {},
            onTitleChanged = {},
            onDescriptionChanged = {},
            onStatusSelected = {},
            onCategoryToggled = {},
            onLocationChanged = {},
            onDateClick = {},
            onSaveClick = {},
            onCancelClick = {},
        )
    }
}

@Preview
@Composable
fun AddActivityScreenEmptyPreview() {
    AppTheme {
        AddActivityContent(
            state = AddActivityState(),
            onBackClick = {},
            onTitleChanged = {},
            onDescriptionChanged = {},
            onStatusSelected = {},
            onCategoryToggled = {},
            onLocationChanged = {},
            onDateClick = {},
            onSaveClick = {},
            onCancelClick = {},
        )
    }
}
