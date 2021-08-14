package org.ybk.fooddiaryapp.presentation.editdiary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.ybk.fooddiaryapp.domain.usecase.*
import org.ybk.fooddiaryapp.presentation.diary.DiaryViewModel

class EditDiaryViewModelFactory(
    private val getDiaryUseCase: GetDiaryUseCase,
    private val addDiaryUseCase: AddDiaryUseCase,
    private val uploadFoodImageUseCase: UploadFoodImageUseCase,
    private val deleteDiaryUseCase: DeleteDiaryUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EditDiaryViewModel(
            getDiaryUseCase,
            addDiaryUseCase,
            uploadFoodImageUseCase,
            deleteDiaryUseCase
        ) as T
    }
}