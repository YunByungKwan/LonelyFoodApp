package org.ybk.fooddiaryapp.presentation.di.profile

import dagger.Subcomponent
import org.ybk.fooddiaryapp.presentation.di.core.FragmentScope
import org.ybk.fooddiaryapp.presentation.profile.ProfileFragment

@FragmentScope
@Subcomponent(modules = [ProfileModule::class])
interface ProfileComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): ProfileComponent
    }

    fun inject(fragment: ProfileFragment): ProfileFragment
}