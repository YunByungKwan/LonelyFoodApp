package org.ybk.fooddiaryapp.presentation.di.editdiary

import dagger.Module
import dagger.Provides
import org.ybk.fooddiaryapp.domain.usecase.AddDiaryUseCase
import org.ybk.fooddiaryapp.domain.usecase.DeleteDiaryUseCase
import org.ybk.fooddiaryapp.domain.usecase.GetDiaryUseCase
import org.ybk.fooddiaryapp.domain.usecase.UploadFoodImageUseCase
import org.ybk.fooddiaryapp.presentation.editdiary.EditDiaryViewModelFactory

@Module
class EditDiaryModule {
    @EditDiaryScope
    @Provides
    fun provideEditDiaryViewModelFactory(
        getDiaryUseCase: GetDiaryUseCase,
        addDiaryUseCase: AddDiaryUseCase,
        uploadFoodImageUseCase: UploadFoodImageUseCase,
        deleteDiaryUseCase: DeleteDiaryUseCase
    ): EditDiaryViewModelFactory {
        return EditDiaryViewModelFactory(
            getDiaryUseCase,
            addDiaryUseCase,
            uploadFoodImageUseCase,
            deleteDiaryUseCase
        )
    }
}