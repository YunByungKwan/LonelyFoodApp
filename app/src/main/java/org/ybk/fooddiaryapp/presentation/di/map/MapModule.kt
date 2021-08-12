package org.ybk.fooddiaryapp.presentation.di.map

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import org.ybk.fooddiaryapp.domain.usecase.GetDiaryListUseCase
import org.ybk.fooddiaryapp.domain.usecase.SignInFirebaseUseCase
import org.ybk.fooddiaryapp.presentation.di.core.FragmentScope
import org.ybk.fooddiaryapp.presentation.login.LoginViewModelFactory
import org.ybk.fooddiaryapp.presentation.map.MapViewModel
import org.ybk.fooddiaryapp.presentation.map.MapViewModelFactory

@Module
class MapModule {
//    @Binds
//    @IntoMap
//    @FragmentScope
//    @ViewModelKey(MapViewModel::class)
//    internal abstract fun bindViewModel(viewmodel: MapViewModel): ViewModel

    @Provides
    fun provideMapViewModelFactory(
        getDiaryListUseCase: GetDiaryListUseCase
    ): MapViewModelFactory {
        return MapViewModelFactory(getDiaryListUseCase)
    }
}