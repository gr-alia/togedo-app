package com.togedo.app.ui.activity

import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
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
import compose.icons.feathericons.ArrowLeft
import compose.icons.feathericons.Calendar
import compose.icons.feathericons.CheckCircle
import compose.icons.feathericons.ChevronRight
import compose.icons.feathericons.Edit2
import compose.icons.feathericons.Heart
import compose.icons.feathericons.MapPin
import compose.icons.feathericons.Send
import compose.icons.feathericons.Sun
import org.jetbrains.compose.ui.tooling.preview.Preview

class ActivityDetailsScreen(private val activityId: String) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        ActivityDetailsContent(onBackClick = { navigator.pop() })
    }
}

@Composable
private fun ActivityDetailsContent(onBackClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.background),
    ) {
        HeroArea(onBackClick = onBackClick)

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Spacing.spacing5)
                .padding(top = Spacing.spacing5),
        ) {
            Text(
                text = "Sunset picnic\nat Eidsvold",
                style = AppTheme.typography.display2,
                color = AppTheme.colors.text,
            )

            Spacer(modifier = Modifier.height(Spacing.spacing4))

            AttributionRow()

            Spacer(modifier = Modifier.height(Spacing.spacing4))

            Text(
                text = "Wine, cheese, that blanket we never use. Let's watch the sky do its thing and not check our phones for one whole hour.",
                style = AppTheme.typography.body1,
                color = AppTheme.colors.text,
            )

            Spacer(modifier = Modifier.height(Spacing.spacing5))

            DetailRowsCard()

            Spacer(modifier = Modifier.height(Spacing.spacing4))

            TagsRow()

            Spacer(modifier = Modifier.height(Spacing.spacing5))
        }

        BottomActions()
    }
}

@Composable
private fun HeroArea(onBackClick: () -> Unit) {
    val secondary = AppTheme.colors.secondary
    val primary = AppTheme.colors.primary
    val tertiary = AppTheme.colors.tertiary
    val onStatusPlanned = AppTheme.colors.onFeatureSpecificStatusPlanned

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp),
    ) {
        // Gradient background
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(secondary, primary),
                    ),
                ),
        )

        // Sun circle
        Box(
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.TopEnd)
                .offset(x = (-36).dp, y = 50.dp)
                .clip(CircleShape)
                .background(tertiary.copy(alpha = 0.85f)),
        )

        // Wave overlay at bottom
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .align(Alignment.BottomStart)
                .offset(x = (-10).dp, y = 20.dp)
                .clip(RoundedCornerShape(topStartPercent = 100, topEndPercent = 100))
                .background(Color.White.copy(alpha = 0.12f)),
        )

        // Top controls
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.spacing5)
                .padding(top = 12.dp)
                .align(Alignment.TopStart),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            GlassButton(onClick = onBackClick) {
                Icon(
                    imageVector = FeatherIcons.ArrowLeft,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp),
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(Spacing.spacing2)) {
                GlassButton(onClick = {}) {
                    Icon(
                        imageVector = FeatherIcons.Heart,
                        contentDescription = "Favourite",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp),
                    )
                }
                GlassButton(onClick = {}) {
                    Icon(
                        imageVector = FeatherIcons.Send,
                        contentDescription = "Share",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp),
                    )
                }
            }
        }

        // Status chip overlay
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = Spacing.spacing5, bottom = Spacing.spacing5),
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(BorderRadius.roundedFull))
                    .background(Color.White.copy(alpha = 0.92f))
                    .padding(horizontal = 12.dp, vertical = 6.dp),
            ) {
                Text(
                    text = "● Planned · Friday, Apr 24",
                    style = AppTheme.typography.label2.copy(fontWeight = FontWeight.Bold),
                    color = onStatusPlanned,
                )
            }
        }
    }
}

@Composable
private fun GlassButton(
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    Surface(
        onClick = onClick,
        shape = CircleShape,
        color = Color.White.copy(alpha = 0.22f),
        modifier = Modifier.size(40.dp),
    ) {
        Box(contentAlignment = Alignment.Center) {
            content()
        }
    }
}

@Composable
private fun AttributionRow() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        // Overlapping avatars
        Box {
            Avatar(
                initial = "E",
                bgColor = AppTheme.colors.primary,
                textColor = AppTheme.colors.onPrimary
            )
            Box(modifier = Modifier.offset(x = 22.dp)) {
                Avatar(
                    initial = "J",
                    bgColor = AppTheme.colors.tertiary,
                    textColor = AppTheme.colors.onTertiary
                )
            }
        }

        Spacer(modifier = Modifier.width(Spacing.spacing8))

        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("You") }
                append(" added this for ")
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("Jack") }
                append(" · 2 days ago")
            },
            style = AppTheme.typography.body2,
            color = AppTheme.colors.textSecondary,
        )
    }
}

@Composable
private fun Avatar(
    initial: String,
    bgColor: Color,
    textColor: Color,
    size: androidx.compose.ui.unit.Dp = 32.dp,
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
private fun DetailRowsCard() {
    val primary = AppTheme.colors.primary
    val primaryContainer = AppTheme.colors.primaryContainer

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(BorderRadius.roundedLg))
            .background(AppTheme.colors.surface)
            .padding(horizontal = Spacing.spacing4),
    ) {
        DetailRow(
            icon = {
                Icon(
                    imageVector = FeatherIcons.MapPin,
                    tint = primary,
                    modifier = Modifier.size(18.dp)
                )
            },
            label = "Eidsvold park, west entrance",
            sub = "Tap to open in Maps",
            divider = false,
        )
        DetailRow(
            icon = {
                Icon(
                    imageVector = FeatherIcons.Calendar,
                    tint = primary,
                    modifier = Modifier.size(18.dp)
                )
            },
            label = "Friday · Apr 24",
            sub = "6:00 – 8:30 PM · Added to calendar",
            divider = true,
        )
        DetailRow(
            icon = {
                Icon(
                    imageVector = FeatherIcons.Sun,
                    tint = primary,
                    modifier = Modifier.size(18.dp)
                )
            },
            label = "Sunset at 7:48 PM",
            sub = "Local forecast: 18° clear",
            divider = true,
        )
    }
}

@Composable
private fun DetailRow(
    icon: @Composable () -> Unit,
    label: String,
    sub: String,
    divider: Boolean,
) {
    if (divider) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(AppTheme.colors.outlineVariant),
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.spacing3),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.spacing3),
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(BorderRadius.roundedMd))
                .background(AppTheme.colors.primaryContainer),
            contentAlignment = Alignment.Center,
        ) {
            icon()
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                style = AppTheme.typography.h4,
                color = AppTheme.colors.text,
            )
            Text(
                text = sub,
                style = AppTheme.typography.body3,
                color = AppTheme.colors.textSecondary,
                modifier = Modifier.padding(top = 2.dp),
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
private fun TagsRow() {
    Row(horizontalArrangement = Arrangement.spacedBy(Spacing.spacing2)) {
        listOf("Romantic", "Chill", "Outdoors").forEach { tag ->
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(BorderRadius.roundedFull))
                    .background(AppTheme.colors.secondaryContainer)
                    .padding(horizontal = 12.dp, vertical = 6.dp),
            ) {
                Text(
                    text = tag,
                    style = AppTheme.typography.label2,
                    color = AppTheme.colors.secondary,
                )
            }
        }
    }
}

@Composable
private fun BottomActions() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppTheme.colors.background)
            .padding(horizontal = Spacing.spacing5, vertical = Spacing.spacing3),
        horizontalArrangement = Arrangement.spacedBy(Spacing.spacing2),
    ) {
        Button(
            modifier = Modifier.weight(1f),
            variant = ButtonVariant.SecondaryOutlined,
            onClick = {},
        ) {
            Row {
                Icon(
                    imageVector = FeatherIcons.Edit2,
                    contentDescription = null,
                    tint = AppTheme.colors.secondary,
                    modifier = Modifier.size(16.dp),
                )
                Spacer(modifier = Modifier.width(Spacing.spacing1))
                Text(
                    text = "Edit",
                    style = AppTheme.typography.button,
                    color = AppTheme.colors.secondary,
                )
            }
        }

        Button(
            modifier = Modifier.weight(2f),
            variant = ButtonVariant.Primary,
            onClick = {},
        ) {
            Row {
                Icon(
                    imageVector = FeatherIcons.CheckCircle,
                    contentDescription = null,
                    tint = AppTheme.colors.onPrimary,
                    modifier = Modifier.size(18.dp),
                )
                Spacer(modifier = Modifier.width(Spacing.spacing2))
                Text(
                    text = "Mark as done",
                    style = AppTheme.typography.button,
                    color = AppTheme.colors.onPrimary,
                )
            }
        }
    }
}

@Preview
@Composable
fun ActivityDetailsScreenPreview() {
    AppTheme {
        ActivityDetailsContent(onBackClick = {})
    }
}
