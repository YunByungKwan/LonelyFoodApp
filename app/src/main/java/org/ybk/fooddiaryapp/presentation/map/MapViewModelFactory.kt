package org.ybk.fooddiaryapp.presentation.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.ybk.fooddiaryapp.domain.usecase.GetDiaryListUseCase

class MapViewModelFactory(
    private val getDiaryListUseCase: GetDiaryListUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MapViewModel(getDiaryListUseCase) as T
    }
}