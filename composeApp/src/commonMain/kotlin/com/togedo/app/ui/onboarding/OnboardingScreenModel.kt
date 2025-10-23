package com.togedo.app.ui.onboarding

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.navigator.Navigator
import com.togedo.app.ui.auth.LoginScreen
import kotlinx.coroutines.launch

class OnboardingScreenModel : StateScreenModel<OnboardingScreenModel.State>(State()) {

    data class State(
        val currentPage: Int = 0,
        val pages: List<OnboardingPageUiModel> = listOf(
            OnboardingPageUiModel(0), OnboardingPageUiModel(1),
            OnboardingPageUiModel(2)
        )
    )

    fun onPageChanged(page: Int) {
        screenModelScope.launch {
            mutableState.value = state.value.copy(currentPage = page)
        }
    }

    fun onSkip(navigator: Navigator) {
        navigator.push(LoginScreen())
    }

    fun onStart(navigator: Navigator) {
        navigator.push(LoginScreen())
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
