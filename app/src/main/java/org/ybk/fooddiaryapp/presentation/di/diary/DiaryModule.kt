package org.ybk.fooddiaryapp.presentation.di.diary

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import org.ybk.fooddiaryapp.presentation.di.core.FragmentScope
import org.ybk.fooddiaryapp.domain.usecase.DeleteDiaryUseCase
import org.ybk.fooddiaryapp.domain.usecase.GetDiaryListUseCase
import org.ybk.fooddiaryapp.presentation.dialog.DotDialogViewModelFactory
import org.ybk.fooddiaryapp.presentation.diary.DiaryViewModel
import org.ybk.fooddiaryapp.presentation.diary.DiaryViewModelFactory

@Module
class DiaryModule {
//    @Binds
//    @IntoMap
//    @FragmentScope
//    @ViewModelKey(DiaryViewModel::class)
//    internal abstract fun bindViewModel(viewmodel: DiaryViewModel): ViewModel

    @DiaryScope
    @Provides
    fun provideDiaryViewModelFactory(
        getDiaryListUseCase: GetDiaryListUseCase
    ): DiaryViewModelFactory {
        return DiaryViewModelFactory(getDiaryListUseCase)
    }
}