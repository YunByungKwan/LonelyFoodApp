package org.ybk.fooddiaryapp.presentation.share

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.ybk.fooddiaryapp.domain.usecase.GetOpenDiaryListUseCase

class ShareViewModelFactory(
    private val getOpenDiaryListUseCase: GetOpenDiaryListUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ShareViewModel(getOpenDiaryListUseCase) as T
    }
}