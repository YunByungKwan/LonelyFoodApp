package org.ybk.fooddiaryapp.ui.dialog.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import org.ybk.fooddiaryapp.di.FragmentScope
import org.ybk.fooddiaryapp.di.ViewModelKey
import org.ybk.fooddiaryapp.ui.dialog.dot.DotDialogViewModel

@Module
abstract class DotDialogModule {
    @Binds
    @IntoMap
    @FragmentScope
    @ViewModelKey(DotDialogViewModel::class)
    abstract fun bindViewModel(viewmodel: DotDialogViewModel): ViewModel
}