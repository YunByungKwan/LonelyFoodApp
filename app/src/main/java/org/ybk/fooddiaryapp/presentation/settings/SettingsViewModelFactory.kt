package org.ybk.fooddiaryapp.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.ybk.fooddiaryapp.domain.usecase.*
import org.ybk.fooddiaryapp.presentation.profile.ProfileViewModel

class SettingsViewModelFactory(
    private val withDrawFirebaseUseCase: WithDrawFirebaseUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SettingsViewModel(withDrawFirebaseUseCase) as T
    }
}