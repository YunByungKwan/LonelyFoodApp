package org.ybk.fooddiaryapp.ui.settings.di

import dagger.Subcomponent
import org.ybk.fooddiaryapp.di.FragmentScope
import org.ybk.fooddiaryapp.ui.settings.SettingsFragment

@FragmentScope
@Subcomponent(modules = [SettingsModule::class])
interface SettingsComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): SettingsComponent
    }

    fun inject(fragment: SettingsFragment): SettingsFragment
}