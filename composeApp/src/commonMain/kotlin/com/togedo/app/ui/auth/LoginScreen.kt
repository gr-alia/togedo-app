package com.togedo.app.ui.auth

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.togedo.app.designsystem.AppTheme
import com.togedo.app.designsystem.Spacing
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import com.togedo.app.designsystem.BorderRadius
import com.togedo.app.designsystem.components.Button
import com.togedo.app.designsystem.components.ButtonVariant
import com.togedo.app.designsystem.components.Icon
import com.togedo.app.designsystem.components.Surface
import com.togedo.app.designsystem.components.Text
import com.togedo.app.designsystem.components.textfield.TextField
import compose.icons.FeatherIcons
import compose.icons.feathericons.Mail
import org.jetbrains.compose.ui.tooling.preview.Preview

class LoginScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        LoginContent(
            onRegisterClick = { navigator.push(RegisterScreen()) },
            onEmailLoginClick = {},
            onGoogleLoginClick = {},
        )
    }
}

@Composable
private fun LoginContent(
    onRegisterClick: () -> Unit = {},
    onEmailLoginClick: (String) -> Unit = {},
    onGoogleLoginClick: () -> Unit = {},
) {
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.background)
            .padding(horizontal = Spacing.spacing6),
    ) {
        Spacer(modifier = Modifier.height(Spacing.spacing14))

        LogoMark()

        Spacer(modifier = Modifier.height(Spacing.spacing14))

        Text(
            text = "Welcome\nback.",
            style = AppTheme.typography.display1,
            color = AppTheme.colors.text,
        )

        Spacer(modifier = Modifier.height(Spacing.spacing3))

        Text(
            text = "We'll send a magic code to your inbox — no password to remember.",
            style = AppTheme.typography.body1,
            color = AppTheme.colors.textSecondary,
        )

        Spacer(modifier = Modifier.height(Spacing.spacing10))

        Text(
            text = "Email",
            style = AppTheme.typography.label1,
            color = AppTheme.colors.textSecondary,
        )

        Spacer(modifier = Modifier.height(Spacing.spacing2))

        TextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = "your@email.com",
                    style = AppTheme.typography.body1,
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = FeatherIcons.Mail,
                    contentDescription = "Email",
                    modifier = Modifier.size(Spacing.spacing5),
                    tint = AppTheme.colors.primary,
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        )

        Spacer(modifier = Modifier.height(Spacing.spacing5))

        Button(
            text = "Send me a magic code",
            onClick = { onEmailLoginClick(email) },
            modifier = Modifier.fillMaxWidth(),
            variant = ButtonVariant.Primary,
        )

        Spacer(modifier = Modifier.height(Spacing.spacing8))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp)
                    .background(AppTheme.colors.outline),
            )
            Text(
                text = "  or  ",
                style = AppTheme.typography.label2,
                color = AppTheme.colors.textDisabled,
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp)
                    .background(AppTheme.colors.outline),
            )
        }

        Spacer(modifier = Modifier.height(Spacing.spacing5))

        Surface(
            onClick = onGoogleLoginClick,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.5.dp,
                    color = AppTheme.colors.outline,
                    shape = RoundedCornerShape(BorderRadius.roundedXl),
                ),
            shape = RoundedCornerShape(BorderRadius.roundedXl),
            color = AppTheme.colors.surface,
        ) {
            Row(
                modifier = Modifier.padding(
                    horizontal = Spacing.spacing4,
                    vertical = Spacing.spacing3,
                ),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "G",
                    style = AppTheme.typography.body1.copy(
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    ),
                    color = AppTheme.colors.text,
                )
                Spacer(modifier = Modifier.width(Spacing.spacing2))
                Text(
                    text = "Continue with Google",
                    style = AppTheme.typography.button,
                    color = AppTheme.colors.text,
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Spacing.spacing5),
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = buildAnnotatedString {
                    append("New here? ")
                    withStyle(SpanStyle(color = AppTheme.colors.primary)) {
                        append("Create an account")
                    }
                },
                style = AppTheme.typography.body2,
                color = AppTheme.colors.textSecondary,
                modifier = Modifier.clickable(onClick = onRegisterClick),
            )
        }
    }
}

@Composable
private fun LogoMark() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.spacing2),
    ) {
        Box {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(AppTheme.colors.primary),
            )
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .offset(x = 18.dp)
                    .clip(CircleShape)
                    .background(AppTheme.colors.secondary),
            )
        }

        Spacer(modifier = Modifier.width(18.dp))

        Text(
            text = "togedo",
            style = AppTheme.typography.h4,
            color = AppTheme.colors.text,
        )
    }
}

@Preview
@Composable
fun LoginContentPreview() {
    AppTheme {
        LoginContent()
    }
}
