package org.ybk.fooddiaryapp.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.ybk.fooddiaryapp.domain.usecase.*

class SearchViewModelFactory(
    private val getPlaceListUseCase: GetPlaceListUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchViewModel(getPlaceListUseCase) as T
    }
}