package com.togedo.app.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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
import com.togedo.app.designsystem.components.textfield.TextField
import compose.icons.FeatherIcons
import compose.icons.feathericons.ArrowLeft
import compose.icons.feathericons.ArrowRight
import compose.icons.feathericons.Camera
import compose.icons.feathericons.Check
import compose.icons.feathericons.Eye
import compose.icons.feathericons.EyeOff
import compose.icons.feathericons.Lock
import compose.icons.feathericons.Mail
import compose.icons.feathericons.User
import org.jetbrains.compose.ui.tooling.preview.Preview

class RegisterScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        RegisterContent(
            onBackClick = { navigator.pop() },
            onContinueClick = {},
        )
    }
}

@Composable
private fun RegisterContent(
    onBackClick: () -> Unit = {},
    onContinueClick: () -> Unit = {},
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var termsAccepted by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Spacing.spacing4),
    ) {
        Spacer(modifier = Modifier.height(Spacing.spacing14))

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

            Surface(
                shape = CircleShape,
                color = AppTheme.colors.surface,
            ) {
                Text(
                    text = "Step 1 of 2",
                    style = AppTheme.typography.label2,
                    color = AppTheme.colors.textSecondary,
                    modifier = Modifier.padding(
                        horizontal = Spacing.spacing3,
                        vertical = Spacing.spacing2,
                    ),
                )
            }
        }

        Spacer(modifier = Modifier.height(Spacing.spacing5))

        Text(
            text = "Let's make\nit yours.",
            style = AppTheme.typography.display1,
            color = AppTheme.colors.text,
        )

        Spacer(modifier = Modifier.height(Spacing.spacing2))

        Text(
            text = "Just a name to get started. You can invite your partner next.",
            style = AppTheme.typography.body1,
            color = AppTheme.colors.textSecondary,
        )

        Spacer(modifier = Modifier.height(Spacing.spacing6))

        StepProgressBar()

        Spacer(modifier = Modifier.height(Spacing.spacing7))

        AvatarChooser(initial = name.firstOrNull()?.uppercaseChar()?.toString() ?: "")

        Spacer(modifier = Modifier.height(Spacing.spacing7))

        Column(verticalArrangement = Arrangement.spacedBy(Spacing.spacing4)) {
            TextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text("Your name", style = AppTheme.typography.body1)
                },
                leadingIcon = {
                    Icon(
                        imageVector = FeatherIcons.User,
                        contentDescription = null,
                        tint = AppTheme.colors.primary,
                        modifier = Modifier.size(18.dp),
                    )
                },
                singleLine = true,
            )

            TextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text("Email", style = AppTheme.typography.body1)
                },
                leadingIcon = {
                    Icon(
                        imageVector = FeatherIcons.Mail,
                        contentDescription = null,
                        tint = AppTheme.colors.textDisabled,
                        modifier = Modifier.size(18.dp),
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            )

            TextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text("Password", style = AppTheme.typography.body1)
                },
                leadingIcon = {
                    Icon(
                        imageVector = FeatherIcons.Lock,
                        contentDescription = null,
                        tint = AppTheme.colors.textDisabled,
                        modifier = Modifier.size(18.dp),
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = if (passwordVisible) FeatherIcons.EyeOff else FeatherIcons.Eye,
                        contentDescription = if (passwordVisible) "Hide password" else "Show password",
                        tint = AppTheme.colors.primary,
                        modifier = Modifier
                            .size(18.dp)
                            .clickable { passwordVisible = !passwordVisible },
                    )
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                singleLine = true,
            )
        }

        Spacer(modifier = Modifier.height(Spacing.spacing6))

        TermsRow(
            accepted = termsAccepted,
            onToggle = { termsAccepted = !termsAccepted },
        )

        Spacer(modifier = Modifier.height(Spacing.spacing6))

        Button(
            onClick = onContinueClick,
            modifier = Modifier.fillMaxWidth(),
            variant = ButtonVariant.Primary,
        ) {
            Text(
                text = "Continue",
                style = AppTheme.typography.button,
                color = AppTheme.colors.onPrimary,
            )
            Spacer(modifier = Modifier.width(Spacing.spacing2))
            Icon(
                imageVector = FeatherIcons.ArrowRight,
                contentDescription = null,
                tint = AppTheme.colors.onPrimary,
                modifier = Modifier.size(18.dp),
            )
        }

        Spacer(modifier = Modifier.height(Spacing.spacing8))
    }
}

@Composable
private fun StepProgressBar() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Spacing.spacing2),
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(AppTheme.colors.primary),
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(AppTheme.colors.outline),
        )
    }
}

@Composable
private fun AvatarChooser(initial: String) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Box {
            Box(
                modifier = Modifier
                    .size(104.dp)
                    .clip(CircleShape)
                    .background(AppTheme.colors.primaryContainer),
                contentAlignment = Alignment.Center,
            ) {
                if (initial.isNotEmpty()) {
                    Text(
                        text = initial,
                        style = AppTheme.typography.display1,
                        color = AppTheme.colors.primary,
                    )
                } else {
                    Icon(
                        imageVector = FeatherIcons.User,
                        contentDescription = null,
                        tint = AppTheme.colors.primary,
                        modifier = Modifier.size(48.dp),
                    )
                }
            }

            Box(
                modifier = Modifier
                    .size(36.dp)
                    .align(Alignment.BottomEnd)
                    .offset(x = 4.dp, y = 4.dp)
                    .clip(CircleShape)
                    .background(AppTheme.colors.primary)
                    .border(
                        width = 3.dp,
                        color = AppTheme.colors.background,
                        shape = CircleShape,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = FeatherIcons.Camera,
                    contentDescription = "Change photo",
                    tint = AppTheme.colors.onPrimary,
                    modifier = Modifier.size(16.dp),
                )
            }
        }
    }
}

@Composable
private fun TermsRow(
    accepted: Boolean,
    onToggle: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(Spacing.spacing2),
        modifier = Modifier.clickable(onClick = onToggle),
    ) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(if (accepted) AppTheme.colors.secondary else AppTheme.colors.outline),
            contentAlignment = Alignment.Center,
        ) {
            if (accepted) {
                Icon(
                    imageVector = FeatherIcons.Check,
                    contentDescription = null,
                    tint = AppTheme.colors.onSecondary,
                    modifier = Modifier.size(14.dp),
                )
            }
        }

        Text(
            text = buildAnnotatedString {
                append("I agree to the ")
                withStyle(SpanStyle(color = AppTheme.colors.primary, fontWeight = FontWeight.SemiBold)) {
                    append("Terms")
                }
                append(" and ")
                withStyle(SpanStyle(color = AppTheme.colors.primary, fontWeight = FontWeight.SemiBold)) {
                    append("Privacy Policy")
                }
                append(".")
            },
            style = AppTheme.typography.body2,
            color = AppTheme.colors.textSecondary,
        )
    }
}

@Preview
@Composable
fun RegisterContentPreview() {
    AppTheme {
        RegisterContent()
    }
}
