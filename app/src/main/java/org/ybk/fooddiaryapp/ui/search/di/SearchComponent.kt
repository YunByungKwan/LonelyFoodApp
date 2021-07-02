package org.ybk.fooddiaryapp.ui.search.di

import dagger.Subcomponent
import org.ybk.fooddiaryapp.di.ActivityScope
import org.ybk.fooddiaryapp.ui.search.SearchActivity

@ActivityScope
@Subcomponent(modules = [SearchModule::class])
interface SearchComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): SearchComponent
    }

    fun inject(activity: SearchActivity): SearchActivity
}