package com.togedo.app.ui.activity.list

data class ActivityUiModel(
    val id: String,
    val title: String,
    val description: String,
    val tags: List<ActivityTag>,
    val status: ActivityStatus,
){
    enum class ActivityTag {
        Fun, Sport, NoSex, Art, Romantic, Pleasure, Sez, Fancy, Chill, Socializing, Nature
    }

    enum class ActivityStatus {
        Idea, Planned, Canceled, Done
    }
}

