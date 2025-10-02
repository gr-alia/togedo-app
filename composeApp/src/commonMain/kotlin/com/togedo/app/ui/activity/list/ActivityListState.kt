package com.togedo.app.ui.activity.list

data class ActivityListState(
    val activities: List<ActivityUiModel> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedFilter: ActivityFilter? = null
)

enum class ActivityFilter {
    Idea,
    Planned,
    Canceled,
    Done
}
