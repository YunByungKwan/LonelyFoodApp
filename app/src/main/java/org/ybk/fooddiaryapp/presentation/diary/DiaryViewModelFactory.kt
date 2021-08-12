package org.ybk.fooddiaryapp.presentation.diary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.ybk.fooddiaryapp.domain.usecase.DeleteDiaryUseCase
import org.ybk.fooddiaryapp.domain.usecase.GetDiaryListUseCase

class DiaryViewModelFactory(
    private val getDiaryListUseCase: GetDiaryListUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DiaryViewModel(getDiaryListUseCase) as T
    }
}