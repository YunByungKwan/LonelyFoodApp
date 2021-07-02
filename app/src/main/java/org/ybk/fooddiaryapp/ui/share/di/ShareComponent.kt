package org.ybk.fooddiaryapp.ui.share.di

import dagger.Subcomponent
import org.ybk.fooddiaryapp.di.FragmentScope
import org.ybk.fooddiaryapp.ui.share.ShareFragment

@FragmentScope
@Subcomponent(modules = [ShareModule::class])
interface ShareComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): ShareComponent
    }

    fun inject(fragment: ShareFragment): ShareFragment
}