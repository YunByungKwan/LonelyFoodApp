package org.ybk.fooddiaryapp.presentation.dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.ybk.fooddiaryapp.domain.usecase.DeleteDiaryUseCase
import org.ybk.fooddiaryapp.domain.usecase.RecommendRestaurantUseCase
import org.ybk.fooddiaryapp.domain.usecase.UpdateDiaryToOpenUseCase

class DotDialogViewModelFactory(
    private val recommendRestaurantUseCase: RecommendRestaurantUseCase,
    private val updateDiaryToOpenUseCase: UpdateDiaryToOpenUseCase,
    private val deleteDiaryUseCase: DeleteDiaryUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DotDialogViewModel(
            recommendRestaurantUseCase,
            updateDiaryToOpenUseCase,
            deleteDiaryUseCase
        ) as T
    }
}