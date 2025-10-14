package com.togedo.app.ui.activity.add

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.togedo.app.ui.activity.list.ActivityUiModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AddActivityScreenModel : StateScreenModel<AddActivityState>(AddActivityState()) {

    fun onTitleChanged(title: String) {
        mutableState.value = state.value.copy(
            title = title,
            titleError = false
        )
    }

    fun onDescriptionChanged(description: String) {
        mutableState.value = state.value.copy(description = description)
    }

    fun onCategoryToggled(tag: ActivityUiModel.ActivityTag) {
        val currentCategories = state.value.selectedCategories
        val newCategories = if (currentCategories.contains(tag)) {
            currentCategories - tag
        } else {
            currentCategories + tag
        }
        mutableState.value = state.value.copy(selectedCategories = newCategories)
    }

    fun onStatusSelected(status: ActivityUiModel.ActivityStatus) {
        mutableState.value = state.value.copy(selectedStatus = status)
    }

    fun onLocationChanged(location: String) {
        mutableState.value = state.value.copy(location = location)
    }

    fun onDateChanged(date: String) {
        mutableState.value = state.value.copy(date = date, showDatePicker = false)
    }

    fun onShowDatePicker(show: Boolean) {
        mutableState.value = state.value.copy(showDatePicker = show)
    }

    fun onPhotoSelected(uri: String?) {
        mutableState.value = state.value.copy(photoUri = uri)
    }

    fun validateAndSave(onSuccess: () -> Unit) {
        if (state.value.title.isBlank()) {
            mutableState.value = state.value.copy(titleError = true)
            return
        }

        screenModelScope.launch {
            mutableState.value = state.value.copy(isSaving = true)

            try {
                delay(1000)

                mutableState.value = state.value.copy(
                    isSaving = false,
                    showSuccessMessage = true
                )

                delay(500)
                onSuccess()
            } catch (e: Exception) {
                mutableState.value = state.value.copy(isSaving = false)
            }
        }
    }

    fun clearSuccessMessage() {
        mutableState.value = state.value.copy(showSuccessMessage = false)
    }
}
