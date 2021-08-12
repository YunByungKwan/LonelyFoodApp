package org.ybk.fooddiaryapp.presentation.di.settings

import dagger.Subcomponent
import org.ybk.fooddiaryapp.presentation.di.core.FragmentScope
import org.ybk.fooddiaryapp.presentation.settings.SettingsFragment

@FragmentScope
@Subcomponent(modules = [SettingsModule::class])
interface SettingsComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): SettingsComponent
    }

    fun inject(fragment: SettingsFragment): SettingsFragment
}