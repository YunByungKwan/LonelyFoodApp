package org.ybk.fooddiaryapp.presentation.di.dialog

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import org.ybk.fooddiaryapp.presentation.di.core.FragmentScope
import org.ybk.fooddiaryapp.domain.usecase.AddDiaryUseCase
import org.ybk.fooddiaryapp.domain.usecase.DeleteDiaryUseCase
import org.ybk.fooddiaryapp.domain.usecase.RecommendRestaurantUseCase
import org.ybk.fooddiaryapp.domain.usecase.UpdateDiaryToOpenUseCase
import org.ybk.fooddiaryapp.presentation.adddiary.AddDiaryViewModelFactory
import org.ybk.fooddiaryapp.presentation.dialog.DotDialogViewModel
import org.ybk.fooddiaryapp.presentation.dialog.DotDialogViewModelFactory

@Module
class DotDialogModule {
//    @Binds
//    @IntoMap
//    @FragmentScope
//    @ViewModelKey(DotDialogViewModel::class)
//    abstract fun bindViewModel(viewmodel: DotDialogViewModel): ViewModel

    @DotDialogScope
    @Provides
    fun provideDotDialogViewModelFactory(
        recommendRestaurantUseCase: RecommendRestaurantUseCase,
        updateDiaryToOpenUseCase: UpdateDiaryToOpenUseCase,
        deleteDiaryUseCase: DeleteDiaryUseCase
    ): DotDialogViewModelFactory {
        return DotDialogViewModelFactory(
            recommendRestaurantUseCase,
            updateDiaryToOpenUseCase,
            deleteDiaryUseCase
        )
    }
}