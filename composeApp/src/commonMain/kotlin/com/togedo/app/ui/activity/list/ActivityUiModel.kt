package com.togedo.app.ui.activity.list

import androidx.compose.runtime.Composable
import com.togedo.app.designsystem.AppTheme

data class ActivityUiModel(
    val id: String,
    val title: String,
    val description: String,
    val tags: List<ActivityTag>,
    val status: ActivityStatus,
) {


    enum class ActivityTag {
        Fun, Sport, NoSex, Art, Romantic, Pleasure, Sez, Fancy, Chill, Socializing, Nature
    }

    enum class ActivityStatus {
        Idea, Planned, Canceled, Done;

        val statusColor
            @Composable get() = when (this) {
                ActivityUiModel.ActivityStatus.Idea -> AppTheme.colors.onFeatureSpecificStatusPlanning
                ActivityUiModel.ActivityStatus.Planned -> AppTheme.colors.onFeatureSpecificStatusPlanned
                ActivityUiModel.ActivityStatus.Canceled -> AppTheme.colors.onFeatureSpecificStatusCanceled
                ActivityUiModel.ActivityStatus.Done -> AppTheme.colors.onFeatureSpecificStatusDone
            }

        val statusBackgroundColor
            @Composable get() = when (this) {
                ActivityUiModel.ActivityStatus.Idea -> AppTheme.colors.featureSpecificStatusPlanning
                ActivityUiModel.ActivityStatus.Planned -> AppTheme.colors.featureSpecificStatusPlanned
                ActivityUiModel.ActivityStatus.Canceled -> AppTheme.colors.featureSpecificStatusCanceled
                ActivityUiModel.ActivityStatus.Done -> AppTheme.colors.featureSpecificStatusDone
            }
    }
}

