package org.ybk.fooddiaryapp.presentation.di.settings

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import org.ybk.fooddiaryapp.domain.usecase.GetPlaceListUseCase
import org.ybk.fooddiaryapp.domain.usecase.WithDrawFirebaseUseCase
import org.ybk.fooddiaryapp.presentation.di.core.FragmentScope
import org.ybk.fooddiaryapp.presentation.search.SearchViewModelFactory
import org.ybk.fooddiaryapp.presentation.settings.SettingsViewModel
import org.ybk.fooddiaryapp.presentation.settings.SettingsViewModelFactory

@Module
class SettingsModule {
//    @Binds
//    @IntoMap
//    @FragmentScope
//    @ViewModelKey(SettingsViewModel::class)
//    internal abstract fun bindViewModel(viewmodel: SettingsViewModel): ViewModel

    @Provides
    fun provideSettingsViewModelFactory(
        withDrawFirebaseUseCase: WithDrawFirebaseUseCase
    ): SettingsViewModelFactory {
        return SettingsViewModelFactory(withDrawFirebaseUseCase)
    }
}