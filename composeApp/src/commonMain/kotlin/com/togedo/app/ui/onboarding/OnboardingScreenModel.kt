package com.togedo.app.ui.onboarding

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch

class OnboardingScreenModel : StateScreenModel<OnboardingScreenModel.State>(State()) {

    data class State(
        val currentPage: Int = 0,
    )

    fun onPageChanged(page: Int) {
        screenModelScope.launch {
            mutableState.value = state.value.copy(currentPage = page)
        }
    }

    fun onSkip() {
        // TODO: Navigate to main flow
    }

    fun onStart() {
        // TODO: Navigate to main flow
    }

    fun onNextPage() {
        screenModelScope.launch {
            val nextPage = (state.value.currentPage + 1).coerceAtMost(2)
            mutableState.value = state.value.copy(currentPage = nextPage)
        }
    }

    fun onPreviousPage() {
        screenModelScope.launch {
            val previousPage = (state.value.currentPage - 1).coerceAtLeast(0)
            mutableState.value = state.value.copy(currentPage = previousPage)
        }
    }
}
