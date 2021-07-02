package org.ybk.fooddiaryapp.ui.adddiary.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import org.ybk.fooddiaryapp.di.ActivityScope
import org.ybk.fooddiaryapp.di.ViewModelKey
import org.ybk.fooddiaryapp.ui.adddiary.AddDiaryViewModel

@Module
abstract class AddDiaryModule {
    @Binds
    @IntoMap
    @ActivityScope
    @ViewModelKey(AddDiaryViewModel::class)
    abstract fun bindViewModel(viewmodel: AddDiaryViewModel): ViewModel
}