package org.ybk.fooddiaryapp.presentation.adddiary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.ybk.fooddiaryapp.domain.usecase.AddDiaryUseCase
import org.ybk.fooddiaryapp.domain.usecase.UploadFoodImageUseCase
import javax.inject.Inject

class AddDiaryViewModelFactory @Inject constructor(
    private val addDiaryUseCase: AddDiaryUseCase,
    private val uploadFoodImageUseCase: UploadFoodImageUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddDiaryViewModel(addDiaryUseCase, uploadFoodImageUseCase) as T
    }
}