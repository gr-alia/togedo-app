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

    fun filterByStatus(filter: ActivityUiModel.ActivityStatus?) {
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

    private fun getSampleActivities(): List<ActivityUiModel> {
        return listOf(
            ActivityUiModel(
                id = "1",
                title = "Sunset picnic at Eidsvold",
                description = "Wine, cheese, that blanket we never use.",
                tags = listOf(ActivityUiModel.ActivityTag.Romantic, ActivityUiModel.ActivityTag.Chill, ActivityUiModel.ActivityTag.Nature),
                status = ActivityUiModel.ActivityStatus.Planned,
                owner = ActivityUiModel.ActivityOwner.User,
                date = "Fri",
            ),
            ActivityUiModel(
                id = "2",
                title = "Japanese cooking class",
                description = "The one near Central Market with handmade udon.",
                tags = listOf(ActivityUiModel.ActivityTag.Fun, ActivityUiModel.ActivityTag.Fancy),
                status = ActivityUiModel.ActivityStatus.Idea,
                owner = ActivityUiModel.ActivityOwner.Partner,
            ),
            ActivityUiModel(
                id = "3",
                title = "Morning run along the river",
                description = "5k, then pastries. Always pastries.",
                tags = listOf(ActivityUiModel.ActivityTag.Sport),
                status = ActivityUiModel.ActivityStatus.Done,
                owner = ActivityUiModel.ActivityOwner.User,
                date = "Sun",
            ),
            ActivityUiModel(
                id = "4",
                title = "Pottery studio date",
                description = "Request pottery like in the movie. Maybe not.",
                tags = listOf(ActivityUiModel.ActivityTag.Art, ActivityUiModel.ActivityTag.Fun),
                status = ActivityUiModel.ActivityStatus.Planned,
                owner = ActivityUiModel.ActivityOwner.Partner,
                date = "Sat",
            ),
            ActivityUiModel(
                id = "5",
                title = "Concert in the park",
                description = "Live music under the stars.",
                tags = listOf(ActivityUiModel.ActivityTag.Art, ActivityUiModel.ActivityTag.Socializing),
                status = ActivityUiModel.ActivityStatus.Canceled,
                owner = ActivityUiModel.ActivityOwner.User,
            ),
        )
    }
}