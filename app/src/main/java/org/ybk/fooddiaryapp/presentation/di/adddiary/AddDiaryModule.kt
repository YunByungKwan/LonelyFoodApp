package org.ybk.fooddiaryapp.presentation.di.adddiary

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import org.ybk.fooddiaryapp.domain.usecase.AddDiaryUseCase
import org.ybk.fooddiaryapp.domain.usecase.UploadFoodImageUseCase
import org.ybk.fooddiaryapp.presentation.adddiary.AddDiaryViewModel
import org.ybk.fooddiaryapp.presentation.adddiary.AddDiaryViewModelFactory
import org.ybk.fooddiaryapp.presentation.di.core.ActivityScope

@Module
class AddDiaryModule {
//    @Binds
//    @IntoMap
//    @ActivityScope
//    @ViewModelKey(AddDiaryViewModel::class)
//    abstract fun bindViewModel(viewmodel: AddDiaryViewModel): ViewModel

    @AddDiaryScope
    @Provides
    fun provideAddDiaryViewModelFactory(
        addDiaryUseCase: AddDiaryUseCase,
        uploadFoodImageUseCase: UploadFoodImageUseCase
    ): AddDiaryViewModelFactory {
        return AddDiaryViewModelFactory(addDiaryUseCase, uploadFoodImageUseCase)
    }
}