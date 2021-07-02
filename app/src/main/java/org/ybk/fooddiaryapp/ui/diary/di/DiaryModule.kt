package org.ybk.fooddiaryapp.ui.diary.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import org.ybk.fooddiaryapp.base.BaseViewModel
import org.ybk.fooddiaryapp.di.FragmentScope
import org.ybk.fooddiaryapp.di.ViewModelKey
import org.ybk.fooddiaryapp.ui.diary.DiaryViewModel

@Module
abstract class DiaryModule {
    @Binds
    @IntoMap
    @FragmentScope
    @ViewModelKey(DiaryViewModel::class)
    internal abstract fun bindViewModel(viewmodel: DiaryViewModel): ViewModel
}