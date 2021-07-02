package org.ybk.fooddiaryapp.ui.map.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import org.ybk.fooddiaryapp.di.FragmentScope
import org.ybk.fooddiaryapp.di.ViewModelKey
import org.ybk.fooddiaryapp.ui.map.MapViewModel

@Module
abstract class MapModule {
    @Binds
    @IntoMap
    @FragmentScope
    @ViewModelKey(MapViewModel::class)
    internal abstract fun bindViewModel(viewmodel: MapViewModel): ViewModel
}