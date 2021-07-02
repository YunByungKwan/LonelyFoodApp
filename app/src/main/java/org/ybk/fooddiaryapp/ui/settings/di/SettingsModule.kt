package org.ybk.fooddiaryapp.ui.settings.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import org.ybk.fooddiaryapp.di.FragmentScope
import org.ybk.fooddiaryapp.di.ViewModelKey
import org.ybk.fooddiaryapp.ui.settings.SettingsViewModel

@Module
abstract class SettingsModule {
    @Binds
    @IntoMap
    @FragmentScope
    @ViewModelKey(SettingsViewModel::class)
    internal abstract fun bindViewModel(viewmodel: SettingsViewModel): ViewModel
}