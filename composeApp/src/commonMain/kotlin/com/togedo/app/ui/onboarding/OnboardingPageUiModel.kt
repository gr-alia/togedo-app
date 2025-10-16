package com.togedo.app.ui.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.togedo.app.designsystem.AppTheme
import com.togedo.app.ui.activity.list.ActivityUiModel
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import togedo_app.composeapp.generated.resources.Res
import togedo_app.composeapp.generated.resources.ic_dark_mode
import togedo_app.composeapp.generated.resources.onboarding1
import togedo_app.composeapp.generated.resources.onboarding2
import togedo_app.composeapp.generated.resources.onboarding3
import togedo_app.composeapp.generated.resources.onboarding_screen1_description_line1
import togedo_app.composeapp.generated.resources.onboarding_screen1_title
import togedo_app.composeapp.generated.resources.onboarding_screen2_description_line1
import togedo_app.composeapp.generated.resources.onboarding_screen2_title
import togedo_app.composeapp.generated.resources.onboarding_screen3_description_line1
import togedo_app.composeapp.generated.resources.onboarding_screen3_title

data class OnboardingPageUiModel(
    val page: Int
){
    val title
        @Composable get() = when (page) {
            0 -> Res.string.onboarding_screen1_title
            1 -> Res.string.onboarding_screen2_title
            else -> Res.string.onboarding_screen3_title
        }

    val description
        @Composable get() = when (page) {
            0 -> Res.string.onboarding_screen1_description_line1
            1 -> Res.string.onboarding_screen2_description_line1
            else -> Res.string.onboarding_screen3_description_line1
        }

    val image
        @Composable get() = when (page) {
            0 -> Res.drawable.onboarding1
            1 -> Res.drawable.onboarding3
            else -> Res.drawable.onboarding2
        }

    val background
        @Composable get() = when (page) {
            0 -> AppTheme.colors.primary
            1 -> AppTheme.colors.secondary
            else -> AppTheme.colors.tertiary
        }
}