package org.ybk.fooddiaryapp.presentation.di.map

import dagger.Subcomponent
import org.ybk.fooddiaryapp.presentation.di.core.FragmentScope
import org.ybk.fooddiaryapp.presentation.map.MapFragment

@FragmentScope
@Subcomponent(modules = [MapModule::class])
interface MapComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): MapComponent
    }

    fun inject(fragment: MapFragment): MapFragment
}