package org.ybk.fooddiaryapp.ui.editdiary.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import org.ybk.fooddiaryapp.di.ActivityScope
import org.ybk.fooddiaryapp.di.ViewModelKey
import org.ybk.fooddiaryapp.ui.editdiary.EditDiaryViewModel

@Module
abstract class EditDiaryModule {
    @Binds
    @IntoMap
    @ActivityScope
    @ViewModelKey(EditDiaryViewModel::class)
    internal abstract fun bindViewModel(viewmodel: EditDiaryViewModel): ViewModel
}