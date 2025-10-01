package com.togedo.app.domain.model.activity

data class Activity(
    val id: String,
    val title: String,
    val description: String,
    val tag: ActivityTag,
    val status: ActivityStatus,
    // todo add planedDate
){
    enum class ActivityTag {
        Fun, Sport, NoSex, Art, Romantic, Pleasure, Sez, Fancy, Chill, Socializing, Nature
    }

    enum class ActivityStatus {
        Idea, Planned, Canceled, Done
    }
}
