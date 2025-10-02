package com.togedo.app.ui.activity.list

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ActivityListScreenModel : StateScreenModel<ActivityListState>(ActivityListState()) {

    init {
        loadActivities()
    }

    fun loadActivities() {
        screenModelScope.launch {
            mutableState.value = state.value.copy(isLoading = true, error = null)

            try {
                delay(3000)

                // todo from repo
                val activities = getSampleActivities()

                mutableState.value = state.value.copy(
                    activities = activities,
                    isLoading = false
                )
            } catch (e: Exception) {
                mutableState.value = state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error occurred"
                )
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        mutableState.value = state.value.copy(searchQuery = query)
    }

    fun filterByStatus(filter: ActivityFilter?) {
        mutableState.value = state.value.copy(selectedFilter = filter)
    }

    fun deleteActivity(id: String) {
        screenModelScope.launch {
            try {
                delay(3000)

                val updatedActivities = state.value.activities.filter { it.id != id }
                mutableState.value = state.value.copy(activities = updatedActivities)
            } catch (e: Exception) {
                mutableState.value = state.value.copy(
                    error = e.message ?: "Failed to delete activity"
                )
            }
        }
    }

    fun getFilteredActivities(): List<ActivityUiModel> {
        val currentState = state.value
        var filtered = currentState.activities

        if (currentState.searchQuery.isNotBlank()) {
            filtered = filtered.filter {
                it.title.contains(currentState.searchQuery, ignoreCase = true) ||
                        it.description.contains(currentState.searchQuery, ignoreCase = true)
            }
        }

        currentState.selectedFilter?.let { filter ->
            filtered = filtered.filter { activity ->
                when (filter) {
                    ActivityFilter.Idea -> activity.status == ActivityUiModel.ActivityStatus.Idea
                    ActivityFilter.Planned -> activity.status == ActivityUiModel.ActivityStatus.Planned
                    ActivityFilter.Canceled -> activity.status == ActivityUiModel.ActivityStatus.Canceled
                    ActivityFilter.Done -> activity.status == ActivityUiModel.ActivityStatus.Done
                }
            }
        }

        return filtered
    }

    private fun getSampleActivities(): List<ActivityUiModel> {
        return listOf(
            ActivityUiModel(
                id = "1",
                title = "Movie Night",
                description = "Watch a classic film with popcorn",
                tag = ActivityUiModel.ActivityTag.Fun,
                status = ActivityUiModel.ActivityStatus.Planned
            ),
            ActivityUiModel(
                id = "2",
                title = "Dinner at Italian Restaurant wearing fancy suits",
                description = "Romantic dinner at Mario's",
                tag = ActivityUiModel.ActivityTag.Romantic,
                status = ActivityUiModel.ActivityStatus.Idea
            ),
            ActivityUiModel(
                id = "3",
                title = "Hiking Trip",
                description = "Mountain trail adventure",
                tag = ActivityUiModel.ActivityTag.Nature,
                status = ActivityUiModel.ActivityStatus.Planned
            ),
            ActivityUiModel(
                id = "4",
                title = "Board Game Night",
                description = "Play Catan with friends",
                tag = ActivityUiModel.ActivityTag.Socializing,
                status = ActivityUiModel.ActivityStatus.Done
            ),
            ActivityUiModel(
                id = "5",
                title = "Concert",
                description = "Live music performance",
                tag = ActivityUiModel.ActivityTag.Art,
                status = ActivityUiModel.ActivityStatus.Canceled
            )
        )
    }
}