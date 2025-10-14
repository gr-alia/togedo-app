package com.togedo.app.ui.profile

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProfileScreenModel : StateScreenModel<ProfileState>(ProfileState()) {

    init {
        loadProfile()
    }

    private fun loadProfile() {
        screenModelScope.launch {
            mutableState.value = state.value.copy(isLoading = true, error = null)

            try {
                delay(1000)

                val badges = listOf(
                    Badge(
                        id = "1",
                        title = "Dreamer",
                        level = 3,
                        type = BadgeType.Dreamer
                    ),
                    Badge(
                        id = "2",
                        title = "Explorer",
                        level = 2,
                        type = BadgeType.Explorer
                    ),
                    Badge(
                        id = "3",
                        title = "Achiever",
                        level = 1,
                        type = BadgeType.Achiever
                    )
                )

                val recentActivities = listOf(
                    RecentActivity(
                        id = "1",
                        title = "Sunset Picnic at the Beach",
                        date = "Completed 2 days ago",
                        type = RecentActivityType.Completed
                    ),
                    RecentActivity(
                        id = "2",
                        title = "Learn to Cook Pasta Together",
                        date = "Added 5 days ago",
                        type = RecentActivityType.Added
                    ),
                    RecentActivity(
                        id = "3",
                        title = "Visit Art Museum Downtown",
                        date = "Planned for next week",
                        type = RecentActivityType.Planned
                    )
                )

                mutableState.value = state.value.copy(
                    badges = badges,
                    recentActivities = recentActivities,
                    isLoading = false
                )
            } catch (e: Exception) {
                mutableState.value = state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load profile"
                )
            }
        }
    }

    fun refreshProfile() {
        loadProfile()
    }
}
