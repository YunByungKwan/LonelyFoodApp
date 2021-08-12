package org.ybk.fooddiaryapp.presentation.di.share

import dagger.Subcomponent
import org.ybk.fooddiaryapp.presentation.di.core.FragmentScope
import org.ybk.fooddiaryapp.presentation.share.ShareFragment

@FragmentScope
@Subcomponent(modules = [ShareModule::class])
interface ShareComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): ShareComponent
    }

    fun inject(fragment: ShareFragment): ShareFragment
}