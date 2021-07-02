package org.ybk.fooddiaryapp.ui.profile.di

import dagger.Subcomponent
import org.ybk.fooddiaryapp.di.FragmentScope
import org.ybk.fooddiaryapp.ui.profile.ProfileFragment

@FragmentScope
@Subcomponent(modules = [ProfileModule::class])
interface ProfileComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): ProfileComponent
    }

    fun inject(fragment: ProfileFragment): ProfileFragment
}