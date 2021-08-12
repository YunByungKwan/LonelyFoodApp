package org.ybk.fooddiaryapp.presentation.editdiary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.ybk.fooddiaryapp.domain.usecase.GetDiaryListUseCase
import org.ybk.fooddiaryapp.presentation.diary.DiaryViewModel

class EditDiaryViewModelFactory(): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EditDiaryViewModel() as T
    }
}