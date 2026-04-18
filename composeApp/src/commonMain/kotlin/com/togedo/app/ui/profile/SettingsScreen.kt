package com.togedo.app.ui.profile

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
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.vector.ImageVector
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
import compose.icons.feathericons.ArrowLeft
import compose.icons.feathericons.Bell
import compose.icons.feathericons.ChevronRight
import compose.icons.feathericons.Globe
import compose.icons.feathericons.Heart
import compose.icons.feathericons.LogOut
import compose.icons.feathericons.Moon
import compose.icons.feathericons.Shield
import compose.icons.feathericons.Users
import compose.icons.feathericons.Zap
import org.jetbrains.compose.ui.tooling.preview.Preview

class SettingsScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        SettingsContent(onBackClick = { navigator.pop() })
    }
}

@Composable
private fun SettingsContent(onBackClick: () -> Unit) {
    var suggestionsEnabled by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Spacing.spacing5),
    ) {
        Spacer(modifier = Modifier.height(Spacing.spacing14))

        SettingsTopBar(onBackClick = onBackClick)

        Spacer(modifier = Modifier.height(Spacing.spacing5))

        AccountCard()

        Spacer(modifier = Modifier.height(Spacing.spacing5))

        SettingsGroup(title = "YOUR SPACE WITH JACK") {
            SettingsRow(
                icon = FeatherIcons.Heart,
                iconColor = AppTheme.colors.primary,
                title = "Partner & pairing",
            )
            SettingsDivider()
            SettingsRow(
                icon = FeatherIcons.Bell,
                iconColor = AppTheme.colors.secondary,
                title = "Notifications",
            )
            SettingsDivider()
            SettingsRow(
                icon = FeatherIcons.Users,
                iconColor = AppTheme.colors.tertiary,
                title = "Shared lists",
            )
        }

        Spacer(modifier = Modifier.height(Spacing.spacing4))

        SettingsGroup(title = "PREFERENCES") {
            SettingsRow(
                icon = FeatherIcons.Moon,
                iconColor = AppTheme.colors.secondary,
                title = "Appearance",
                value = "System",
            )
            SettingsDivider()
            SettingsToggleRow(
                icon = FeatherIcons.Zap,
                iconColor = AppTheme.colors.primary,
                title = "Suggestions",
                checked = suggestionsEnabled,
                onCheckedChange = { suggestionsEnabled = it },
            )
            SettingsDivider()
            SettingsRow(
                icon = FeatherIcons.Globe,
                iconColor = AppTheme.colors.textSecondary,
                title = "Language",
                value = "English",
            )
        }

        Spacer(modifier = Modifier.height(Spacing.spacing4))

        SettingsGroup(title = "ACCOUNT") {
            SettingsRow(
                icon = FeatherIcons.Shield,
                iconColor = AppTheme.colors.success,
                title = "Privacy & security",
            )
            SettingsDivider()
            SettingsRow(
                icon = FeatherIcons.LogOut,
                iconColor = AppTheme.colors.error,
                title = "Sign out",
                danger = true,
                showChevron = false,
            )
        }

        Spacer(modifier = Modifier.height(Spacing.spacing8))

        Text(
            text = "togedo · v1.4.0 · made with ♥",
            style = AppTheme.typography.body3,
            color = AppTheme.colors.textDisabled,
            modifier = Modifier.fillMaxWidth().padding(bottom = Spacing.spacing5),
        )
    }
}

@Composable
private fun SettingsTopBar(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.spacing3),
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
                    modifier = Modifier.size(18.dp),
                )
            }
        }

        Text(
            text = "Settings",
            style = AppTheme.typography.h2,
            color = AppTheme.colors.text,
        )
    }
}

@Composable
private fun AccountCard() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(BorderRadius.roundedLg))
            .background(AppTheme.colors.surface)
            .padding(Spacing.spacing4),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(AppTheme.colors.primary),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "E",
                style = AppTheme.typography.h3.copy(fontWeight = FontWeight.ExtraBold),
                color = AppTheme.colors.onPrimary,
            )
        }

        Spacer(modifier = Modifier.width(Spacing.spacing3))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Emma",
                style = AppTheme.typography.h3,
                color = AppTheme.colors.text,
            )
            Text(
                text = "emma@email.com",
                style = AppTheme.typography.body3,
                color = AppTheme.colors.textSecondary,
            )
        }

        Icon(
            imageVector = FeatherIcons.ChevronRight,
            contentDescription = null,
            tint = AppTheme.colors.textDisabled,
            modifier = Modifier.size(18.dp),
        )
    }
}

@Composable
private fun SettingsGroup(
    title: String,
    content: @Composable () -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = AppTheme.typography.label2,
            color = AppTheme.colors.textSecondary,
            modifier = Modifier.padding(start = Spacing.spacing2, bottom = Spacing.spacing2),
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(BorderRadius.roundedLg))
                .background(AppTheme.colors.surface)
                .padding(horizontal = Spacing.spacing4),
        ) {
            content()
        }
    }
}

@Composable
private fun SettingsDivider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(AppTheme.colors.outlineVariant),
    )
}

@Composable
private fun SettingsRow(
    icon: ImageVector,
    iconColor: Color,
    title: String,
    value: String? = null,
    danger: Boolean = false,
    showChevron: Boolean = true,
    onClick: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = Spacing.spacing3),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.spacing3),
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(BorderRadius.roundedMd))
                .background(iconColor.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(16.dp),
            )
        }

        Text(
            text = title,
            style = AppTheme.typography.body1,
            color = if (danger) AppTheme.colors.error else AppTheme.colors.text,
            modifier = Modifier.weight(1f),
        )

        if (value != null) {
            Text(
                text = value,
                style = AppTheme.typography.body2,
                color = AppTheme.colors.textSecondary,
            )
        }

        if (showChevron) {
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
private fun SettingsToggleRow(
    icon: ImageVector,
    iconColor: Color,
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(vertical = Spacing.spacing3),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.spacing3),
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(BorderRadius.roundedMd))
                .background(iconColor.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(16.dp),
            )
        }

        Text(
            text = title,
            style = AppTheme.typography.body1,
            color = AppTheme.colors.text,
            modifier = Modifier.weight(1f),
        )

        MiniToggle(checked = checked, onCheckedChange = onCheckedChange)
    }
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
fun SettingsScreenPreview() {
    AppTheme {
        SettingsContent(onBackClick = {})
    }
}
