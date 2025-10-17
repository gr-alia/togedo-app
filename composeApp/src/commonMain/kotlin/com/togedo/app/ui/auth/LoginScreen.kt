package com.togedo.app.ui.auth

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.togedo.app.designsystem.AppTheme
import com.togedo.app.designsystem.Spacing
import com.togedo.app.designsystem.components.Button
import com.togedo.app.designsystem.components.ButtonVariant
import com.togedo.app.designsystem.components.Icon
import com.togedo.app.designsystem.components.Text
import com.togedo.app.designsystem.components.textfield.TextField
import compose.icons.FeatherIcons
import compose.icons.feathericons.Mail
import compose.icons.feathericons.Smile
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
            .padding(horizontal = Spacing.spacing6)
            .padding(top = Spacing.spacing16),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(AppTheme.colors.brandEarthYellow.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = FeatherIcons.Smile,
                contentDescription = "Welcome",
                modifier = Modifier.size(56.dp),
                tint = AppTheme.colors.brandEarthYellow,
            )
        }

        Spacer(modifier = Modifier.height(Spacing.spacing8))

        Text(
            text = buildAnnotatedString {
                append("Hey there! ")
                withStyle(SpanStyle(color = AppTheme.colors.brandVerdigris)) {
                    append("👋")
                }
            },
            style = AppTheme.typography.h1,
            color = AppTheme.colors.text,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(Spacing.spacing3))

        Text(
            text = "We're excited to see you! Drop your email below and we'll send you a magic code ✨",
            style = AppTheme.typography.body2,
            color = AppTheme.colors.textSecondary,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(Spacing.spacing10))

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
                    tint = AppTheme.colors.brandVerdigris,
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        )

        Spacer(modifier = Modifier.height(Spacing.spacing6))

        Button(
            text = "Send me the magic code! 🪄",
            onClick = { onEmailLoginClick(email) },
            modifier = Modifier.fillMaxWidth(),
            variant = ButtonVariant.Primary,
        )

        Spacer(modifier = Modifier.height(Spacing.spacing8))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp)
                    .background(AppTheme.colors.outline),
            )
            Text(
                text = "  or try this  ",
                style = AppTheme.typography.label2,
                color = AppTheme.colors.textSecondary,
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp)
                    .background(AppTheme.colors.outline),
            )
        }

        Spacer(modifier = Modifier.height(Spacing.spacing8))

        Button(
            text = "Continue with Google",
            onClick = onGoogleLoginClick,
            modifier = Modifier.fillMaxWidth(),
            variant = ButtonVariant.SecondaryOutlined,
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
