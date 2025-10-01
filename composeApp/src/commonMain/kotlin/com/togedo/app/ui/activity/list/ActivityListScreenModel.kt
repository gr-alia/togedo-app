package com.togedo.app.ui.activity.list

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.StateScreenModel

class ActivityListScreenModel: StateScreenModel<> {

    sealed class State {
        object Loading : State()
        data class Result(val item: String) : State()
    }
}