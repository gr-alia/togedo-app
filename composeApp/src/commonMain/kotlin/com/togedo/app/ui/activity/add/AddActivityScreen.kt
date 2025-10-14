package com.togedo.app.ui.activity.add

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.togedo.app.designsystem.AppTheme
import com.togedo.app.designsystem.BorderRadius
import com.togedo.app.designsystem.Spacing
import com.togedo.app.designsystem.components.*
import com.togedo.app.designsystem.components.textfield.TextField
import com.togedo.app.ui.activity.list.ActivityUiModel
import compose.icons.FeatherIcons
import compose.icons.feathericons.*
import androidx.compose.foundation.text.KeyboardOptions

class AddActivityScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = rememberScreenModel { AddActivityScreenModel() }
        val state by screenModel.state.collectAsState()

        LaunchedEffect(state.showSuccessMessage) {
            if (state.showSuccessMessage) {
                screenModel.clearSuccessMessage()
            }
        }

        Scaffold(
            topBar = {
                TopBar(
                    onBackClick = { navigator.pop() }
                )
            },
            snackbarHost = {
                if (state.titleError) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Spacing.spacing4)
                    ) {
                        SnackbarMessage(
                            message = "Activity title is required",
                            onDismiss = { screenModel.onTitleChanged(state.title) }
                        )
                    }
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(AppTheme.colors.background)
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = Spacing.spacing4)
                    ) {
                        Spacer(modifier = Modifier.height(Spacing.spacing4))

                        TitleSection(
                            title = state.title,
                            onTitleChanged = screenModel::onTitleChanged,
                            isError = state.titleError
                        )

                        Spacer(modifier = Modifier.height(Spacing.spacing4))

                        DescriptionSection(
                            description = state.description,
                            onDescriptionChanged = screenModel::onDescriptionChanged
                        )

                        Spacer(modifier = Modifier.height(Spacing.spacing5))

                        StatusSection(
                            selectedStatus = state.selectedStatus,
                            onStatusSelected = screenModel::onStatusSelected
                        )

                        Spacer(modifier = Modifier.height(Spacing.spacing5))

                        CategorySection(
                            selectedCategories = state.selectedCategories,
                            onCategoryToggled = screenModel::onCategoryToggled
                        )

                        Spacer(modifier = Modifier.height(Spacing.spacing5))

                        LocationSection(
                            location = state.location,
                            onLocationChanged = screenModel::onLocationChanged
                        )

                        Spacer(modifier = Modifier.height(Spacing.spacing4))

                        DateSection(
                            date = state.date,
                            onDateClick = { screenModel.onShowDatePicker(true) }
                        )

                        Spacer(modifier = Modifier.height(Spacing.spacing4))

                        PhotoSection(
                            photoUri = state.photoUri,
                            onPhotoSelected = screenModel::onPhotoSelected
                        )

                        Spacer(modifier = Modifier.height(Spacing.spacing8))
                    }

                    ActionButtons(
                        isSaving = state.isSaving,
                        onSaveClick = {
                            screenModel.validateAndSave { navigator.pop() }
                        },
                        onCancelClick = { navigator.pop() }
                    )
                }
            }
        }
    }
}

@Composable
private fun TopBar(onBackClick: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = AppTheme.colors.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.spacing2, vertical = Spacing.spacing3),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                onClick = onBackClick,
                shape = RoundedCornerShape(BorderRadius.roundedSm),
                color = AppTheme.colors.transparent
            ) {
                Box(
                    modifier = Modifier.padding(Spacing.spacing2)
                ) {
                    Icon(
                        imageVector = FeatherIcons.ArrowLeft,
                        contentDescription = "Back",
                        tint = AppTheme.colors.text
                    )
                }
            }

            Spacer(modifier = Modifier.width(Spacing.spacing2))

            Text(
                text = "Add New Activity",
                style = AppTheme.typography.h2,
                color = AppTheme.colors.text
            )
        }
    }
}

@Composable
private fun SnackbarMessage(
    message: String,
    onDismiss: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(BorderRadius.roundedMd),
        color = AppTheme.colors.error
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.spacing4),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = FeatherIcons.AlertCircle,
                    contentDescription = null,
                    tint = AppTheme.colors.onError,
                    modifier = Modifier.size(20.dp)
                )

                Spacer(modifier = Modifier.width(Spacing.spacing2))

                Text(
                    text = message,
                    style = AppTheme.typography.body2,
                    color = AppTheme.colors.onError
                )
            }

            Surface(
                onClick = onDismiss,
                shape = RoundedCornerShape(BorderRadius.roundedFull),
                color = AppTheme.colors.transparent
            ) {
                Icon(
                    imageVector = FeatherIcons.X,
                    contentDescription = "Dismiss",
                    tint = AppTheme.colors.onError,
                    modifier = Modifier
                        .size(20.dp)
                        .padding(Spacing.spacing1)
                )
            }
        }
    }
}

@Composable
private fun TitleSection(
    title: String,
    onTitleChanged: (String) -> Unit,
    isError: Boolean
) {
    Column {
        Text(
            text = "Activity Title *",
            style = AppTheme.typography.label1,
            color = AppTheme.colors.text
        )

        Spacer(modifier = Modifier.height(Spacing.spacing2))

        TextField(
            value = title,
            onValueChange = onTitleChanged,
            placeholder = { Text("e.g., Sunset picnic at the beach", style = AppTheme.typography.body1) },
            singleLine = true,
            isError = isError,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun DescriptionSection(
    description: String,
    onDescriptionChanged: (String) -> Unit
) {
    Column {
        Text(
            text = "Description",
            style = AppTheme.typography.label1,
            color = AppTheme.colors.text
        )

        Spacer(modifier = Modifier.height(Spacing.spacing2))

        TextField(
            value = description,
            onValueChange = onDescriptionChanged,
            placeholder = {
                Text(
                    "Add more details about your activity...",
                    style = AppTheme.typography.body1
                )
            },
            minLines = 4,
            maxLines = 6,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun StatusSection(
    selectedStatus: ActivityUiModel.ActivityStatus,
    onStatusSelected: (ActivityUiModel.ActivityStatus) -> Unit
) {
    Column {
        Text(
            text = "Status",
            style = AppTheme.typography.label1,
            color = AppTheme.colors.text
        )

        Spacer(modifier = Modifier.height(Spacing.spacing2))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.spacing2)
        ) {
            listOf(
                ActivityUiModel.ActivityStatus.Idea to "To Do",
                ActivityUiModel.ActivityStatus.Planned to "Planned",
                ActivityUiModel.ActivityStatus.Done to "Done"
            ).forEach { (status, label) ->
                StatusChip(
                    label = label,
                    status = status,
                    isSelected = selectedStatus == status,
                    onClick = { onStatusSelected(status) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun StatusChip(
    label: String,
    status: ActivityUiModel.ActivityStatus,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected) {
        status.statusBackgroundColor
    } else {
        AppTheme.colors.surface
    }

    val textColor = if (isSelected) {
        status.statusColor
    } else {
        AppTheme.colors.textSecondary
    }

    Surface(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(BorderRadius.roundedMd),
        color = backgroundColor
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Spacing.spacing3),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = label,
                style = AppTheme.typography.label1,
                color = textColor
            )
        }
    }
}

@Composable
private fun CategorySection(
    selectedCategories: Set<ActivityUiModel.ActivityTag>,
    onCategoryToggled: (ActivityUiModel.ActivityTag) -> Unit
) {
    Column {
        Text(
            text = "Categories",
            style = AppTheme.typography.label1,
            color = AppTheme.colors.text
        )

        Spacer(modifier = Modifier.height(Spacing.spacing2))

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.spacing2),
            verticalArrangement = Arrangement.spacedBy(Spacing.spacing2)
        ) {
            CategoryOption.entries.forEach { category ->
                CategoryChip(
                    label = category.displayName,
                    isSelected = selectedCategories.contains(category.tag),
                    onClick = { onCategoryToggled(category.tag) }
                )
            }
        }
    }
}

@Composable
private fun CategoryChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Chip(
        onClick = onClick,
        selected = isSelected,
        colors = ChipDefaults.chipColors(
            selectedContainerColor = AppTheme.colors.secondary,
            selectedContentColor = AppTheme.colors.onSecondary
        )
    ) {
        Text(
            text = label,
            style = AppTheme.typography.label2
        )
    }
}

@Composable
private fun LocationSection(
    location: String,
    onLocationChanged: (String) -> Unit
) {
    Column {
        Text(
            text = "Location",
            style = AppTheme.typography.label1,
            color = AppTheme.colors.text
        )

        Spacer(modifier = Modifier.height(Spacing.spacing2))

        TextField(
            value = location,
            onValueChange = onLocationChanged,
            placeholder = { Text("Add a location (optional)", style = AppTheme.typography.body1) },
            leadingIcon = {
                Icon(
                    imageVector = FeatherIcons.MapPin,
                    contentDescription = null,
                    tint = AppTheme.colors.secondary,
                    modifier = Modifier.size(20.dp)
                )
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun DateSection(
    date: String,
    onDateClick: () -> Unit
) {
    Column {
        Text(
            text = "Date",
            style = AppTheme.typography.label1,
            color = AppTheme.colors.text
        )

        Spacer(modifier = Modifier.height(Spacing.spacing2))

        Surface(
            onClick = onDateClick,
            shape = RoundedCornerShape(BorderRadius.roundedXl),
            color = AppTheme.colors.surface,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.spacing4, vertical = Spacing.spacing4),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = FeatherIcons.Calendar,
                    contentDescription = null,
                    tint = AppTheme.colors.secondary,
                    modifier = Modifier.size(20.dp)
                )

                Spacer(modifier = Modifier.width(Spacing.spacing3))

                Text(
                    text = if (date.isEmpty()) "Pick a date (optional)" else date,
                    style = AppTheme.typography.body1,
                    color = if (date.isEmpty()) AppTheme.colors.textSecondary else AppTheme.colors.text
                )
            }
        }
    }
}

@Composable
private fun PhotoSection(
    photoUri: String?,
    onPhotoSelected: (String?) -> Unit
) {
    Column {
        Text(
            text = "Photo",
            style = AppTheme.typography.label1,
            color = AppTheme.colors.text
        )

        Spacer(modifier = Modifier.height(Spacing.spacing2))

        Surface(
            onClick = { },
            shape = RoundedCornerShape(BorderRadius.roundedMd),
            color = AppTheme.colors.surface,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Spacing.spacing4),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = FeatherIcons.Image,
                    contentDescription = null,
                    tint = AppTheme.colors.secondary,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(Spacing.spacing3))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = if (photoUri == null) "Add a photo" else "Photo attached",
                        style = AppTheme.typography.body1,
                        color = AppTheme.colors.text
                    )

                    if (photoUri == null) {
                        Text(
                            text = "Optional",
                            style = AppTheme.typography.body3,
                            color = AppTheme.colors.textSecondary
                        )
                    }
                }

                Icon(
                    imageVector = FeatherIcons.Upload,
                    contentDescription = null,
                    tint = AppTheme.colors.textSecondary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
private fun ActionButtons(
    isSaving: Boolean,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = AppTheme.colors.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.spacing4),
            horizontalArrangement = Arrangement.spacedBy(Spacing.spacing2)
        ) {
            Surface(
                onClick = onCancelClick,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(BorderRadius.roundedMd),
                color = AppTheme.colors.background
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Spacing.spacing4),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Cancel",
                        style = AppTheme.typography.button,
                        color = AppTheme.colors.text
                    )
                }
            }

            Surface(
                onClick = onSaveClick,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(BorderRadius.roundedMd),
                color = AppTheme.colors.primary,
                enabled = !isSaving
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Spacing.spacing4),
                    contentAlignment = Alignment.Center
                ) {
                    if (isSaving) {
                        Text(
                            text = "Saving...",
                            style = AppTheme.typography.button,
                            color = AppTheme.colors.onPrimary
                        )
                    } else {
                        Text(
                            text = "Save Activity",
                            style = AppTheme.typography.button,
                            color = AppTheme.colors.onPrimary
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FlowRow(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    content: @Composable () -> Unit
) {
    androidx.compose.foundation.layout.FlowRow(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement
    ) {
        content()
    }
}
