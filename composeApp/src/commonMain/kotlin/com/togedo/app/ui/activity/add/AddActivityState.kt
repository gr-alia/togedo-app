package com.togedo.app.ui.activity.add

import com.togedo.app.ui.activity.list.ActivityUiModel

data class AddActivityState(
    val title: String = "",
    val description: String = "",
    val selectedCategories: Set<ActivityUiModel.ActivityTag> = emptySet(),
    val selectedStatus: ActivityUiModel.ActivityStatus = ActivityUiModel.ActivityStatus.Idea,
    val location: String = "",
    val date: String = "",
    val photoUri: String? = null,
    val titleError: Boolean = false,
    val showDatePicker: Boolean = false,
    val isSaving: Boolean = false,
    val showSuccessMessage: Boolean = false
)

enum class CategoryOption(val tag: ActivityUiModel.ActivityTag, val displayName: String) {
    TRAVEL(ActivityUiModel.ActivityTag.Nature, "Travel"),
    FOOD(ActivityUiModel.ActivityTag.Pleasure, "Food"),
    OUTDOORS(ActivityUiModel.ActivityTag.Nature, "Outdoors"),
    CULTURE(ActivityUiModel.ActivityTag.Art, "Culture"),
    SPORT(ActivityUiModel.ActivityTag.Sport, "Sport"),
    ROMANTIC(ActivityUiModel.ActivityTag.Romantic, "Romantic"),
    FUN(ActivityUiModel.ActivityTag.Fun, "Fun"),
    CHILL(ActivityUiModel.ActivityTag.Chill, "Chill")
}
