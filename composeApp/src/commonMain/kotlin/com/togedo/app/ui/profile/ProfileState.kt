package com.togedo.app.ui.profile

data class ProfileState(
    val user: UserProfile = UserProfile(),
    val statistics: ProfileStatistics = ProfileStatistics(),
    val partner: Partner? = Partner(),
    val badges: List<Badge> = emptyList(),
    val recentActivities: List<RecentActivity> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

data class UserProfile(
    val name: String = "Emma",
    val email: String = "emma@email.com",
    val avatarUrl: String? = null
)

data class ProfileStatistics(
    val sharedListsCount: Int = 12,
    val activitiesCount: Int = 47,
    val completedCount: Int = 23
)

data class Partner(
    val name: String = "Jack",
    val avatarUrl: String? = null
)

data class Badge(
    val id: String,
    val title: String,
    val level: Int,
    val type: BadgeType
)

enum class BadgeType {
    Dreamer,
    Explorer,
    Achiever
}

data class RecentActivity(
    val id: String,
    val title: String,
    val date: String,
    val type: RecentActivityType
)

enum class RecentActivityType {
    Completed,
    Added,
    Planned
}
