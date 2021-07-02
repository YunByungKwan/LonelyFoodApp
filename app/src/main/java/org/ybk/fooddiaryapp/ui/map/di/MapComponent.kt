package org.ybk.fooddiaryapp.ui.map.di

import dagger.Subcomponent
import org.ybk.fooddiaryapp.di.FragmentScope
import org.ybk.fooddiaryapp.ui.map.MapFragment

@FragmentScope
@Subcomponent(modules = [MapModule::class])
interface MapComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): MapComponent
    }

    fun inject(fragment: MapFragment): MapFragment
}